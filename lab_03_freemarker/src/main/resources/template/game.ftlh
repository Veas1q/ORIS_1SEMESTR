<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Крестики-нолики</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
            text-align: center;
            color: white;
        }

        h1 {
            font-size: 2.5em;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }

        .game-board {
            margin: 20px auto;
            border: 3px solid #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        table {
            border-collapse: collapse;
            background: rgba(255, 255, 255, 0.9);
            width: 100%;
        }

        td {
            width: 80px;
            height: 80px;
            border: 2px solid #667eea;
            text-align: center;
            vertical-align: middle;
        }

        .game-info {
            margin: 20px 0;
            padding: 15px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 10px;
        }

        .error {
            color: #ff6b6b;
            background: rgba(255, 255, 255, 0.9);
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
            font-weight: bold;
        }

        button, .btn {
            background: #fc7a7a;
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 25px;
            font-size: 1em;
            cursor: pointer;
            margin: 5px;
            text-decoration: none;
            display: inline-block;
        }

        select {
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            margin: 0 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>🎮 Крестики-Нолики</h1>

    <div class="game-board">
        <table>
            <#list table as r>
                <tr>
                    <td><img src="/lab_03_freemarker_war/static/img/${r.f}" height="64" width="64"></td>
                    <td><img src="/lab_03_freemarker_war/static/img/${r.s}" height="64" width="64"></td>
                    <td><img src="/lab_03_freemarker_war/static/img/${r.t}" height="64" width="64"></td>
                </tr>
            </#list>
        </table>
    </div>

    <div class="game-info">
        <#if winner??>
        <#if winner == "DRAW">
            <h2>🤝 Ничья!</h2>
        <#else>
            <h2>🎉 Победил: ${winner}</h2>
        </#if>
            <a href="/lab_03_freemarker_war/game" class="btn">🔄 Новая игра</a>
        <#else>
        <#if error??>
            <div class="error">${error}</div>
        </#if>

            <h3>Сейчас ходит:
                <#if currentPlayer == "X">Игрок (X)<#else>Бот (O)</#if>
            </h3>

        <#if currentPlayer == "X">
            <form method='post' action='/lab_03_freemarker_war/game'>
                <input type="hidden" name="uid" value="${uid}">
                <div style="margin: 15px 0;">
                    <label>Строка:</label>
                    <select name='row'>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                    <label>Столбец:</label>
                    <select name='col'>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </div>
                <button type="submit">Сделать ход</button>
            </form>
        <#else>
            <p>🤖 Бот думает...</p>
            <form method='post' action='/lab_03_freemarker_war/game'>
                <input type="hidden" name="uid" value="${uid}">
                <input type="hidden" name="row" value="1">
                <input type="hidden" name="col" value="1">
                <input type="submit" value="Продолжить" style="display: none;" id="autoSubmit">
            </form>
            <script>
                setTimeout(() => {
                    document.getElementById('autoSubmit').click();
                }, 1500);
            </script>
        </#if>
        </#if>
    </div>
</div>
</body>
</html>