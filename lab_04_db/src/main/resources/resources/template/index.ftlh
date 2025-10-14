<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Тест Freemarker</title>
    <meta name="description" content="Тест Freemarker">
    <style>
        label {
            color: red;
        }
        .div_style {
            border: 1px solid green;
        }
        div {
            margin: 3px;
        }
    </style>
</head>
<body>
<h1>Тест Freemarker</h1>
<form method='post' action='/lab3/test'>
    <div>
         <label>Фамилия:</label>
        <input type='text' name='surname' value=${surname}></input>
    </div>
    <div>
        <label>Имя:</label>
        <input type='text' name='name' value=${name}></input>
    </div>
    <div>
        <input type='submit' value='отправить'>
    </div>
    <div>
        <label>Роль:</label>:
        <select name='role'>
            <option value="1">Студент</option>
            <option value="2">Преподователь</option>
            <option value="3">Вольнопосещающий</option>
        </select>
        <input type='submit' value='отправить'>
    </div>
    <div class="div_style">
        <label>Принимаю условия соглашения</label>
        <input type="checkbox" name="commit">
    </div>
    <fieldset>
        <label>Хорошее</label>
        <div>
            <input type="radio" name="mood" value="good" checked>
        </div>
        <label>Плохое</label>
        <div>
            <input type="radio" name="mood" value="bad" checked>
        </div>
        <label>Пойдет</label>
        <div>
            <input type="radio" name="mood" value="ok" checked>
        </div>
    </fieldset>
</form>
</body>
</html>