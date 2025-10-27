<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Онлайн-табло аэропорта</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; text-align: center; margin-bottom: 30px; }
        .filters { background: #ecf0f1; padding: 20px; border-radius: 6px; margin-bottom: 20px; }
        .filters form { display: flex; gap: 15px; align-items: end; flex-wrap: wrap; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 5px; color: #34495e; }
        select, input, button { padding: 8px 12px; border: 1px solid #bdc3c7; border-radius: 4px; font-size: 14px; }
        button { background: #3498db; color: white; border: none; cursor: pointer; padding: 8px 20px; }
        button:hover { background: #2980b9; }
        .board-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .board-table th { background: #34495e; color: white; padding: 12px; text-align: left; }
        .board-table td { padding: 12px; border-bottom: 1px solid #ecf0f1; }
        .board-table tr:hover { background: #f8f9fa; }
        .status-scheduled { color: #27ae60; font-weight: bold; }
        .status-delayed { color: #e74c3c; font-weight: bold; }
        .status-cancelled { color: #95a5a6; font-weight: bold; }
        .status-departed { color: #3498db; font-weight: bold; }
        .current-selection { background: #fff3cd; padding: 10px; border-radius: 4px; margin-bottom: 15px; border-left: 4px solid #ffc107; }
        .no-flights { text-align: center; padding: 40px; color: #7f8c8d; }
    </style>
</head>
<body>
<div class="container">
    <h1>✈️ Онлайн-табло аэропорта</h1>
    <!-- Текущие настройки -->
    <div class="current-selection">
        <strong>Показываем:</strong>
        <#if boardType == "departure">
            Вылеты из аэропорта
        <#else>
            Прилеты в аэропорт
        </#if>
        <strong>${currentAirportName!"Неизвестно"}</strong>
        на <strong>${selectedDate!"неизвестную дату"}</strong>
    </div>

    <!-- Форма фильтров -->
    <div class="filters">
        <form method="get" action="board">
            <div class="form-group">
                <label for="airport">Аэропорт:</label>
                <select id="airport" name="airport">
                    <#if airports??>
                        <#list airports as airport>
                            <option value="${airport.airportCode}"
                                    <#if currentAirport == airport.airportCode>selected</#if>>
                                ${airport.airportName} (${airport.airportCode}) - ${airport.city}
                            </option>
                        </#list>
                    <#else>
                        <option value="LED">Пулково (LED)</option>
                    </#if>
                </select>
            </div>

            <div class="form-group">
                <label for="type">Тип табло:</label>
                <select id="type" name="type">
                    <option value="departure" <#if boardType == "departure">selected</#if>>Вылет</option>
                    <option value="arrival" <#if boardType == "arrival">selected</#if>>Прилет</option>
                </select>
            </div>

            <div class="form-group">
                <label for="date">Дата:</label>
                <input type="date" id="date" name="date" value="${selectedDate!}">
            </div>

            <div class="form-group">
                <button type="submit">Показать</button>
            </div>
        </form>
    </div>

    <!-- Таблица рейсов -->
    <#if flights?? && flights?size gt 0>
        <table class="board-table">
            <thead>
            <tr>
                <#if boardType == "departure">
                    <th>Рейс</th>
                    <th>Вылет</th>
                    <th>Назначение</th>
                    <th>Аэропорт</th>
                    <th>Статус</th>
                <#else>
                    <th>Рейс</th>
                    <th>Прилет</th>
                    <th>Откуда</th>
                    <th>Аэропорт</th>
                    <th>Статус</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#list flights as flight>
                <tr>
                    <td><strong>${flight.flightNo}</strong></td>
                    <td>
                        <#if flight.time??>
                            ${flight.time?datetime("yyyy-MM-dd'T'HH:mm")?time}
                        <#else>
                            --:--
                        </#if>
                    </td>
                    <td>${flight.city}</td>
                    <td>${flight.airportName} (${flight.airportCode})</td>
                    <td class="
                            <#if flight.status == 'Scheduled'>status-scheduled
                            <#elseif flight.status == 'Delayed'>status-delayed
                            <#elseif flight.status == 'Cancelled'>status-cancelled
                            <#elseif flight.status == 'Departed'>status-departed
                            <#else>status-scheduled</#if>">
                        <#if flight.status == 'Scheduled'>По расписанию
                        <#elseif flight.status == 'Delayed'>Задерживается
                        <#elseif flight.status == 'Cancelled'>Отменен
                        <#elseif flight.status == 'Departed'>Вылетел
                        <#else>${flight.status}</#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <div class="no-flights">
            <h3>Рейсов не найдено</h3>
            <p>Попробуйте изменить параметры поиска</p>
        </div>
    </#if>

    <!-- Легенда статусов -->
    <div style="margin-top: 20px; padding: 15px; background: #d4edda; border-radius: 4px; text-align: center;">
        <small>
            <strong>Легенда:</strong>
            <span class="status-scheduled">По расписанию</span> |
            <span class="status-delayed">Задерживается</span> |
            <span class="status-cancelled">Отменен</span> |
            <span class="status-departed">Вылетел</span>
        </small>
    </div>
</div>
</body>
</html>