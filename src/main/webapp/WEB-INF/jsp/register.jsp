<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registration</title>
    </head>

    <body>
        <script type="text/javascript" src="scripts/register.js"> </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <div id="body-wrapper">
            <%--<form id="form" name="form" method="post" action="">--%>
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
                            <button type="button" onclick="testAlrt()">Submit</button>
                        </td>
                    </tr>
                </table>
            <%--</form>--%>
        </div>
    </body>
</html>
