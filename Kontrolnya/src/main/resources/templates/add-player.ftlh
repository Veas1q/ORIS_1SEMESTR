<!DOCTYPE html>
<html>
<head>
    <title>Добавить игрока</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin: 15px 0; }
        label { display: inline-block; width: 120px; }
        input, select { padding: 8px; width: 300px; }
        button { padding: 10px 20px; background: #0066cc; color: white; border: none; cursor: pointer; }
        button:hover { background: #004499; }
    </style>
</head>
<body>
<h1>➕ Добавить нового киберспортсмена</h1>
<a href="index">🏠 На главную</a> |
<a href="show">📊 Все игроки</a>

<form action="add" method="POST">
    <div class="form-group">
        <label for="nickname">Никнейм:</label>
        <input type="text" id="nickname" name="nickname" required>
    </div>

    <div class="form-group">
        <label for="name">Полное имя:</label>
        <input type="text" id="name" name="name" required>
    </div>

    <div class="form-group">
        <label for="game">Игра:</label>
        <select id="game" name="game" required>
            <option value="">Выберите игру</option>
            <option value="Dota 2">Dota 2</option>
            <option value="CS2">CS2</option>
        </select>
    </div>

    <div class="form-group">
        <label for="team">Команда:</label>
        <input type="text" id="team" name="team">
    </div>

    <div class="form-group">
        <label for="earnings">Заработок ($):</label>
        <input type="number" id="earnings" name="earnings" step="0.01" value="0">
    </div>

    <div class="form-group">
        <label for="country">Страна (код):</label>
        <input type="text" id="country" name="country" maxlength="3" placeholder="UA, RU, US...">
    </div>

    <div class="form-group">
        <label>
            <input type="checkbox" name="isActive" checked> Активный игрок
        </label>
    </div>

    <button type="submit">💾 Сохранить игрока</button>
</form>
</body>
</html>