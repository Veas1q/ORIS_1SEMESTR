<!DOCTYPE html>
<html>
<head>
    <title>Найти игрока</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .search-form { margin: 20px 0; }
        input[type="text"] { padding: 10px; width: 300px; }
        button { padding: 10px 20px; background: #0066cc; color: white; border: none; cursor: pointer; }
        .error { color: red; margin: 10px 0; }
    </style>
</head>
<body>
<h1>🔍 Найти игрока для редактирования</h1>
<a href="index">🏠 На главную</a> |
<a href="show">📊 Все игроки</a>

<div class="search-form">
    <form action="showone" method="GET">
        <label for="nickname">Введите никнейм игрока:</label><br>
        <input type="text" id="nickname" name="nickname" placeholder="Например: s1mple" required>
        <button type="submit">🔍 Найти</button>
    </form>
</div>

<#if error??>
    <div class="error">❌ ${error}</div>
</#if>

<p>Или выберите из списка: <a href="show">все игроки</a></p>
</body>
</html>