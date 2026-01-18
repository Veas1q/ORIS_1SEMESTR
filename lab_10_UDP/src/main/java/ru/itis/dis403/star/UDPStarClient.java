package ru.itis.dis403.star;

// UDPClient.java
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import java.io.*;

public class UDPStarClient {

    // адрес и порт сервера
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 50000;

    // размер буфера для UDP-пакетов
    private static final int BUFFER_SIZE = 4096;

    // флаг для остановки потока приёма
    // volatile — чтобы изменение было видно всем потокам
    private static volatile boolean running = true;

    public static void main(String[] args) {

        // UDP-сокет клиента
        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress serverAddress =
                    InetAddress.getByName(SERVER_ADDRESS);

            Scanner scanner = new Scanner(System.in);

            System.out.println("UDP клиент запущен");

            // ===============================
            // ПОТОК ПРИЁМА СООБЩЕНИЙ ОТ СЕРВЕРА
            // ===============================
            Thread receiveThread = new Thread(() -> {

                // общий буфер под входящие UDP-пакеты
                byte[] receiveData = new byte[BUFFER_SIZE];

                while (running) {
                    try {
                        DatagramPacket receivePacket =
                                new DatagramPacket(receiveData, receiveData.length);

                        // блокируется, пока не придёт пакет
                        socket.receive(receivePacket);

                        // копируем ТОЛЬКО реальные байты пакета
                        byte[] packetData =
                                new byte[receivePacket.getLength()];
                        System.arraycopy(
                                receivePacket.getData(),
                                0,
                                packetData,
                                0,
                                receivePacket.getLength()
                        );

                        // поток для чтения байт по порядку
                        DataInputStream dis = new DataInputStream(
                                new ByteArrayInputStream(packetData)
                        );

                        // защита от пустых пакетов
                        if (packetData.length > 0) {

                            // первый байт — тип сообщения
                            byte typeMsg = dis.readByte();

                            // выбор логики по типу пакета
                            switch (typeMsg) {

                                // ===== СПИСОК КЛИЕНТОВ =====
                                case 1 -> {
                                    // читаем длину JSON
                                    int size = dis.readInt();

                                    // читаем JSON байты
                                    byte[] msg = new byte[size];
                                    dis.read(msg);

                                    // байты -> строка
                                    String list =
                                            new String(msg, StandardCharsets.UTF_8);

                                    System.out.println(list);
                                    System.out.print("\n> ");
                                }

                                // ===== СООБЩЕНИЕ ОТ КЛИЕНТА =====
                                case 2 -> {
                                    int senderId = dis.readInt();

                                    int size = dis.readInt();

                                    byte[] msg = new byte[size];
                                    dis.read(msg);

                                    String message =
                                            new String(msg, StandardCharsets.UTF_8);

                                    System.out.println("\n> От клиента: " + senderId);
                                    System.out.println("Текст: " + message);
                                    System.out.println("> ");
                                }
                            }
                        }

                    } catch (IOException e) {
                        // ошибка приёма — завершаем поток
                        break;
                    }
                }
            });

            // daemon — чтобы при завершении main потока работа jvm тоже завершилась
            receiveThread.setDaemon(true);
            receiveThread.start();

            // ===============================
            // ОСНОВНОЙ ЦИКЛ ВВОДА ПОЛЬЗОВАТЕЛЯ
            // ===============================
            while (true) {
                System.out.print("> ");

                // читаем команду пользователя
                String command = scanner.nextLine().trim();

                // игнорируем пустой ввод
                if (command.isEmpty()) {
                    continue;
                }

                // выход из клиента
                if (command.equalsIgnoreCase("exit")) {
                    running = false;
                    System.out.println("Завершение работы клиента...");
                    break;
                }

                try {
                    // выбор действия по команде пользователя
                    switch (command.toLowerCase()) {

                        // ===== РЕГИСТРАЦИЯ =====
                        case "hello" -> {
                            System.out.println("введите имя:");
                            String name = scanner.nextLine();

                            byte[] data =
                                    name.getBytes(StandardCharsets.UTF_8);

                            // формируем UDP-пакет вручную
                            ByteArrayOutputStream bos =
                                    new ByteArrayOutputStream();

                            bos.write(0);                       // TYPE = 0
                            bos.write(writeInt(data.length));  // длина имени
                            bos.write(data);                   // имя

                            DatagramPacket sendPacket =
                                    new DatagramPacket(
                                            bos.toByteArray(),
                                            bos.size(),
                                            serverAddress,
                                            SERVER_PORT
                                    );

                            socket.send(sendPacket);
                            System.out.println("✓ Регистрация отправлена");
                        }

                        // ===== ЗАПРОС СПИСКА =====
                        case "list" -> {
                            // пакет состоит только из TYPE
                            DatagramPacket sendPacket =
                                    new DatagramPacket(
                                            new byte[]{1},
                                            1,
                                            serverAddress,
                                            SERVER_PORT
                                    );

                            socket.send(sendPacket);
                            System.out.println("✓ Запрос списка отправлен");
                        }

                        // ===== СООБЩЕНИЕ ДРУГОМУ КЛИЕНТУ =====
                        case "message" -> {
                            System.out.println("введите id получателя:");
                            int recipientId =
                                    Integer.parseInt(scanner.nextLine());

                            System.out.println("введите сообщение:");
                            String msg = scanner.nextLine();

                            byte[] data =
                                    msg.getBytes(StandardCharsets.UTF_8);

                            ByteArrayOutputStream bos =
                                    new ByteArrayOutputStream();

                            bos.write(2);                           // TYPE = 2
                            bos.write(writeInt(recipientId));       // id получателя
                            bos.write(writeInt(data.length));       // длина сообщения
                            bos.write(data);                        // текст

                            DatagramPacket sendPacket =
                                    new DatagramPacket(
                                            bos.toByteArray(),
                                            bos.size(),
                                            serverAddress,
                                            SERVER_PORT
                                    );

                            socket.send(sendPacket);
                            System.out.println(
                                    "✓ Сообщение отправлено клиенту #" + recipientId
                            );
                        }
                    }

                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Введите корректный числовой ID");
                }
            }

            scanner.close();

        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }

    // сериализация int -> 4 байта (big-endian)
    private static byte[] writeInt(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }
}
