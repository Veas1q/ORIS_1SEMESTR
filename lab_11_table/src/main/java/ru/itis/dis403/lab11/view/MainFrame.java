package ru.itis.dis403.lab11.view;

import ru.itis.dis403.lab11.model.Flight;
import ru.itis.dis403.lab11.repository.AirportRepository;
import ru.itis.dis403.lab11.repository.FlightRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

// выпадающий список с кодами аэропортов
private JComboBox<String> airportComboBox;

        // выпадающий список: вылет / прилёт
        private JComboBox<String> typeComboBox;

        // кнопка "Показать"
        private JButton searchButton;

        private final AirportRepository airportRepository;

        private final FlightRepository flightRepository;

        // панель с таблицей рейсов
        private FlightTablePanel flightTablePanel;

        public MainFrame() {
            super("Табло аэропортов");

            this.airportRepository = new AirportRepository();
            this.flightRepository = new FlightRepository();

            // настройка окна и интерфейса
            init();

            // загрузка аэропортов в выпадающий список
            loadAirports();
        }

        // настройка окна
        private void init() {

            // при закрытии окна программа завершается
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            // получаем доступ к системе (экран пользователя)
            Toolkit toolkit = Toolkit.getDefaultToolkit();

            // получаем размер экрана (ширина и высота)
            Dimension dimension = toolkit.getScreenSize();

            // задаём положение и размер окна
            setBounds(
                    dimension.width / 2,
                    dimension.height / 2,
                    800,
                    600
            );

            // создаём верхнюю панель с кнопками и списками
            JPanel controlPanel = createControlPanel();

            // добавляем панель в верхнюю часть окна
            add(controlPanel, BorderLayout.NORTH);

            // создаём панель с таблицей рейсов
            flightTablePanel = new FlightTablePanel();

            // добавляем таблицу в центр окна
            add(flightTablePanel, BorderLayout.CENTER);
        }

        // создание верхней панели управления
        private JPanel createControlPanel() {

            // панель с горизонтальным расположением элементов
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

            // надпись "Аэропорт"
            panel.add(new JLabel("Аэропорт:"));

            // выпадающий список аэропортов
            airportComboBox = new JComboBox<>();
            panel.add(airportComboBox);

            // надпись "Тип табло"
            panel.add(new JLabel("Тип табло:"));

            // выпадающий список: вылет / прилёт
            typeComboBox = new JComboBox<>(new String[]{"Вылет", "Прилёт"});
            panel.add(typeComboBox);

            // кнопка "Показать"
            searchButton = new JButton("Показать");

            // при нажатии загружаем рейсы
            searchButton.addActionListener(e -> loadFlights());
            panel.add(searchButton);

            return panel;
        }

        // загрузка аэропортов из БД
        private void loadAirports() {

            // получаем список кодов аэропортов
            List<String> airports = airportRepository.findAll();

            // добавляем каждый код в выпадающий список
            for (String airport : airports) {
                airportComboBox.addItem(airport);
            }

            if (!airports.isEmpty()) {
                airportComboBox.setSelectedItem("SVO");
            }

        }

        // загрузка рейсов по выбранным параметрам
        private void loadFlights() {

            // выбранный аэропорт
            String airportCode = (String) airportComboBox.getSelectedItem();

            // тип табло: вылет или прилёт
            String type = typeComboBox.getSelectedIndex() == 0
                    ? "departure"
                    : "arrival";

            // если аэропорт выбран
            if (airportCode != null) {

                // получаем список рейсов из БД
                List<Flight> flights =
                        flightRepository.findAll(airportCode, type);

                // передаём рейсы в таблицу
                flightTablePanel.updateTable(flights);
            }
        }

        public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            });
        }
}