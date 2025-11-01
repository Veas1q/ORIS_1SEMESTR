<!DOCTYPE html>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<h1>Добро пожаловать, ${user.username}!</h1>
<p>Вы успешно вошли в систему</p>


<form action="logout" method="POST">
    <button type="submit">Выйти</button>
</form>
</body>
</html>