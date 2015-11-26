<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="../styles/login.css">
</head>
<body onload='document.loginForm.username.focus();'>

<h1>Spring Security Login Form (Database Authentication)</h1>

<div id="login-box">

    <h2>Login with Username and Password</h2>

    <c:if test="${not empty error}">
        <div class="error">Username or Password are incorrect</div>
    </c:if>
    <c:if test="${not empty logout}">
        <div class="logout">You have been logged out</div>
    </c:if>

    <form name='loginForm'
          action="<c:url value='/login' />" method='POST'>

        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='username'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password' /></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"
                                       value="submit" /></td>
            </tr>
        </table>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>
</div>

</body>
</html>