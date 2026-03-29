package ru.itis.dis403.lab11.view;

import ru.itis.dis403.lab11.model.Flight;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;

import java.text.SimpleDateFormat;

import java.util.List;

// панель с таблицей рейсов
public class FlightTablePanel extends JPanel {

    // визуальная таблица
    private JTable table;

    // данные таблицы
    private DefaultTableModel tableModel;

    // формат даты для отображения в таблице
    private SimpleDateFormat dateFormat;

    public FlightTablePanel() {

        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        initComponents();
    }

    // создание и настройка таблицы
    private void initComponents() {

        // используем BorderLayout
        setLayout(new BorderLayout());

        // названия столбцов таблицы
        String[] columnsNames = {
                "Номер рейса",
                "Время отправления",
                "Время прибытия",
                "Статус",
                "Откуда",
                "Куда"
        };

        // модель таблицы:FEWFWEF
        // columnsNames — заголовкFFFпппппппп
        // 0 — изначально нет строкаааgrgr
        tableModel = new DefaultTableModel(columnsNames, 0) {

            // запрещаем редактирование ячеек
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // создаём таблицу и связываем её с моделью
        table = new JTable(tableModel);

        // таблица заполняет всю доступную высоту
        table.setFillsViewportHeight(true);

        // можно выбрать только одну строку
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // запрещаем менять порядок столбцов
        table.getTableHeader().setReorderingAllowed(false);

        // задаём ширину столбцов
        setColumnSize();

        // добавляем прокрутку для таблицы
        JScrollPane jScrollPane = new JScrollPane(table);

        // добавляем таблицу в центр панели
        add(jScrollPane, BorderLayout.CENTER);
    }

    // обновление данных таблицы
    public void updateTable(List<Flight> flightList) {

        // очищаем таблицу от старых данных
        tableModel.setRowCount(0);

        // проходим по списку рейсов
        for (Flight flight : flightList) {

            // одна строка таблицы (6 столбцов)
            Object[] row = new Object[6];

            // заполняем ячейки строки
            row[0] = flight.getFlightId(); // номер рейса
            row[1] = dateFormat.format(flight.getScheduledDeparture()); // время вылета
            row[2] = dateFormat.format(flight.getScheduledArrival());   // время прилёта
            row[3] = translateStatus(flight.getStatus());               // статус
            row[4] = flight.getDepartureAirport();                      // откуда
            row[5] = flight.getArrivalAirport();                        // куда

            // добавляем строку в таблицу
            tableModel.addRow(row);
        }
    }

    // настройка ширины столбцов
    private void setColumnSize() {

        // получаем модель столбцов таблицы
        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);
    }

    // перевод статусов рейса на русский язык
    private String translateStatus(String status) {

        return switch (status) {
            case "Cancelled" -> "Отменен";
            case "Scheduled" -> "По расписанию";
            case "Departed" -> "Вылетел";
            case "Arrived" -> "Прибыл";
            default -> status;
        };
    }
}
