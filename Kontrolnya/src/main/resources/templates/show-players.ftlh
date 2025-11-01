<!DOCTYPE html>
<html>
<head>
    <title>Все киберспортсмены</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:hover { background-color: #f5f5f5; }
        .active { color: green; }
        .inactive { color: red; }
        .actions a { margin-right: 10px; text-decoration: none; }
    </style>
</head>
<body>
<h1>📊 Все киберспортсмены</h1>
<a href="index">🏠 На главную</a> |
<a href="add">➕ Добавить игрока</a>

<#if players?? && players?size gt 0>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Никнейм</th>
            <th>Имя</th>
            <th>Игра</th>
            <th>Команда</th>
            <th>Заработок ($)</th>
            <th>Страна</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list players as player>
            <tr>
                <td>${player.id}</td>
                <td><strong>${player.nickname}</strong></td>
                <td>${player.name}</td>
                <td>${player.game}</td>
                <td>${player.team!''}</td>
                <td>${player.earnings?string["0.##"]}</td>
                <td>${player.country!''}</td>
                <td>
                    <#if player.active>
                        <span class="active">✅ Активен</span>
                    <#else>
                        <span class="inactive">❌ Не активен</span>
                    </#if>
                </td>
                <td class="actions">
                    <a href="showone?nickname=${player.nickname}">✏️ Редактировать</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
<#else>
    <p>Нет данных о игроках</p>
</#if>

<p>Всего игроков: <strong>${players?size!0}</strong></p>
</body>
</html>