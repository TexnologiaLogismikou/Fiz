<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registration</title>
    </head>

    <body>
        <div id="body-wrapper">
            <c:if test="${response == 'OK'}">
                User registered successfully
            </c:if>
            <c:if test="${response == 'CONFLICT'}">
                Username already in use
            </c:if>
            <form id="form" name="form" method="post" action="<c:url value="/register"/>">
                <table id="table">
                    <tr>
                        <td>Username: </td>
                        <td><input type="text" name="username" id="username" required/></td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td><input type="password" name="password" id="password" required/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" name="submit" value="Register">
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </body>
</html>
