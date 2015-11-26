<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Details</title>
    <script type="text/javascript"  src="<c:url value="/resources/scripts/FrScript.js"/>"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body>
    <table border="1" style="width:25%">
        <thead>
        <tr>
            <th>Profile of the user:</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${username}</td>
        </tr>
        </tbody>
    </table>

    <table>
        <tr>
            <td><button type="button" onclick="createFriendship(<%=loggedUsername%>,${username})">Add ${username} as friend</button></td>
        </tr>
    </table>

</body>
</html>
