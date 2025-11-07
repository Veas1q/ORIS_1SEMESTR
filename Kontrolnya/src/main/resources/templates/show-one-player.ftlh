<!DOCTYPE html>
<html>
<head>
    <title>Редактировать игрока</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin: 15px 0; }
        label { display: inline-block; width: 120px; }
        input, select { padding: 8px; width: 300px; }
        button { padding: 10px 20px; margin: 5px; cursor: pointer; }
        .save { background: #28a745; color: white; border: none; }
        .cancel { background: #6c757d; color: white; border: none; }
    </style>
</head>
<body>
<h1>✏️ Редактировать игрока: ${player.nickname!''}</h1>
<a href="index">🏠 На главную</a> |
<a href="show">📊 Все игроки</a>

<#if player??>
    <form action="update" method="POST">
        <!-- Скрытое поле со старым ником -->
        <input type="hidden" name="oldNickname" value="${player.nickname}">

        <div class="form-group">
            <label for="nickname">Никнейм:</label>
            <input type="text" id="nickname" name="nickname" value="${player.nickname!''}" required>
        </div>

        <div class="form-group">
            <label for="name">Полное имя:</label>
            <input type="text" id="name" name="name" value="${player.name!''}" required>
        </div>

        <div class="form-group">
            <label for="game">Игра:</label>
            <select id="game" name="game" required>
                <option value="Dota 2" <#if player.game == 'Dota 2'>selected</#if>>Dota 2</option>
                <option value="CS2" <#if player.game == 'CS2'>selected</#if>>CS2</option>
                <option value="Valorant" <#if player.game == 'Valorant'>selected</#if>>Valorant</option>
                <option value="League of Legends" <#if player.game == 'League of Legends'>selected</#if>>League of Legends</option>
            </select>
        </div>

        <div class="form-group">
            <label for="team">Команда:</label>
            <input type="text" id="team" name="team" value="${player.team!''}">
        </div>

        <div class="form-group">
            <label for="earnings">Заработок ($):</label>
            <input type="number" id="earnings" name="earnings" step="0.01" value="${player.earnings!0}">
        </div>

        <div class="form-group">
            <label for="country">Страна:</label>
            <input type="text" id="country" name="country" value="${player.country!''}" maxlength="3">
        </div>

        <div class="form-group">
            <label>
                <input type="checkbox" name="isActive" <#if player.active>checked</#if>> Активный игрок
            </label>
        </div>

        <button type="submit" class="save">💾 Сохранить изменения</button>
        <a href="show"><button type="button" class="cancel">❌ Отмена</button></a>
    </form>
<#else>
    <p>Игрок не найден</p>
</#if>
</body>
</html>