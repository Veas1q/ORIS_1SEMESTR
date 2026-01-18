<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Тест аутентификации</title>
    <meta name="description" content="Тест аутентификации">

    <style>
        label {
            color: red;
        }

        .div_style {
            border: 1px solid #9fb5de;
        }

        div {
            margin: 3px;
        }
    </style>
</head>
<body>
<h1>Тест AJAX</h1>

<form action="/testajax" >
    <div>
        <select id="city" name="city" onchange="change_city()">
            <option  value="0" selected>-</option>
            <#list cities as city>
                <option value="${city.id}">${city.name}</option>
            </#list>
        </select>
    </div>
    <div><input type="submit" value="Отправить"></div>
</form>

<div id="comment">

</div>

<script>
    function change_city() {
        let city = document.getElementById("city");
        selectedOption = city.options[city.selectedIndex];

        fetch('/ajax/comment?city=' + selectedOption.value).then(response => response.text()).then(txt => {let comment = document.getElementById("comment")});
        comment.innerHTML = txt;
    }
</script>
</body>
</html>