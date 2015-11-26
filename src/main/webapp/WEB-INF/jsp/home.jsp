<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>FIZ</title>
</head>
<body>
<div id="body-wrapper">
    <h1>Welcome on new AutoDeploy</h1>
    <c:choose>
        <c:when test="${empty user}">
            <h2>You are not logged in</h2>
        </c:when>
        <c:otherwise>
            <h2>Logged in as: ${user}, <a href="<c:url value="/login?logout"/>">Logout</a></h2>
        </c:otherwise>
    </c:choose>
    <table>
        <tr>
            <td>
                <a href="<c:url value="/register"/>">Register Page</a>
            </td>
            <td>
                <a href="<c:url value="/login"/>">Login Page</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="<c:url value="/publicchat"/>">Chat</a>
            </td>
            <td>
                <a href="<c:url value="/images"/>">Images</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>

