<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>AccessDenied page</title>
</head>
<body>
<c:choose>
    <c:when test="${empty user}">
        <h2>You do not have permission to access this page!</h2>
    </c:when>
    <c:otherwise>
        <h2>Dear ${user}, <br/>
            You do not have permission to access this page!</h2>
    </c:otherwise>
</c:choose>
<a href="<c:url value="/logout" />">Logout</a>
</body>
</html>