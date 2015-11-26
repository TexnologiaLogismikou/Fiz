<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String username = SecurityContextHolder.getContext().getAuthentication().getName(); %>
<html>
<head>
    <title>Fiz Chat</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/sockjs/1.0.0/sockjs.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="<c:url value="/resources/scripts/chat.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/chat.css"/>">
</head>
<body onload="connect()"  onclose="disconnect()">
<noscript>
    <h1>
        Seems your browser doesn't support Javascript!
        Websocket relies on Javascript being enabled.
        Please enable Javascript and reload this page!
    </h1>
</noscript>
<div id="body-wrapper">
    <textarea id="text-area"></textarea><br/>
    <input id="message" type="text" onkeypress="enterFunction(event)"/><button id="submit" onclick="sendMessage(<%=username%>)">SEND</button>
</div>
</body>
</html>
