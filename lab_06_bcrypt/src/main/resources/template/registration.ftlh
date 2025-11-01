<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>User Registration</h1>

<#if errormessage??>
    <div style="color: red;">
        ${errormessage}
    </div>
</#if>

<form method="POST">
    <div>
        <label>Username:</label>
        <input type="text" name="username" value="${username!''}">
    </div>
    <div>
        <label>Password:</label>
        <input type="password" name="password">
    </div>
    <div>
        <label>First Name:</label>
        <input type="text" name="firstname" value="${firstname!''}">
    </div>
    <div>
        <label>Last Name:</label>
        <input type="text" name="lastname" value="${lastname!''}">
    </div>
    <div>
        <label>Phone:</label>
        <input type="text" name="phone" value="${phone!''}">
    </div>
    <button type="submit">Register</button>
</form>

<a href="login" class="login-btn">Login </a>
</body>
</html>