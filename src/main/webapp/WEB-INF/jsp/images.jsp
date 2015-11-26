<%-- 
    Document   : images
    Created on : Nov 26, 2015, 10:19:51 AM
    Author     : KuroiTenshi
--%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>             

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>images</title>
        <script type="text/javascript"  src="<c:url value="/resources/scripts/ImgScript.js"/>"></script>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>         
    </head>
    <body>
        <%  
            String username;
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.equals("anonymousUser")){
                username = "";
            }
        %>
         <div id="body-wrapper">
             <div id="answer-wrapper">
                <c:if test="${response=='all good'}">
                    <input type="text" name="answer" value="The upload was correct" readonly="readonly" disabled="disabled" />
                </c:if>
                <c:if test="${response=='user not found'}">
                    <input type="text" name="answer" value="user not found" readonly="readonly" disabled="disabled" />
                </c:if>
                <c:if test="${response=='file was empty'}">
                    <input type="text" name="answer" value="file was empty" readonly="readonly" disabled="disabled" />
                </c:if>
                <c:if test="${response=='error with the file'}">
                    <input type="text" name="answer" value="error with the file" readonly="readonly" disabled="disabled" />
                </c:if>
             </div>
             <form method="POST" enctype="multipart/form-data" action="<c:url value="/images"/>">File to upload:
                <input type="file" name="file"><br /> 
                <input type="submit" value="Upload"> Press here to upload the file!
                <input type="hidden" name="username" value="<%= username %>">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <br /><br />
            
            <input type="text" name="imgID" id="imgID" required >
            <button type="button" onclick="downloadImageWithPost()">download With Post</button><br/>
            <img id="ItemPreview" height="900" width="900" src="" alt="" />
         </div>
    </body>
</html>