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
    <table>
        <tr>
            <td>
                <a href="<c:url value="/register"/>">Register Page</a>
            </td>
            <td>
                <a href="<c:url value="/login"/>">Login Page</a>
            </td>
            <td>
                <a href="/chat.html">Chat</a>
            </td>
            <td>
                <a href="/images.html">Images</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>

