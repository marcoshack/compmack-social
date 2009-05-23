<%-- 
    Document   : login
    Created on : May 13, 2009, 8:15:19 PM
    Author     : mhack
--%>

<jsp:include page="include/header.jsp" />
<body>
    <jsp:include page="include/menu_offline.jsp" />
    <br>
    <div>
        <form name="form_login" action="login" method="POST">
            <table>
                <tr>
                <td style="text-align:right">username:
                <td><input type="text" name="username" value="" size="20" />
                <tr>
                <td style="text-align:right">password:
                <td><input type="password" name="password" value="" size="20" />
                <tr>
                <td>
                <td style="text-align:right">
                    <input type="submit" value="Login" name="button_login" />
            </table>
        </form>
        <br>
    </div>
</body>
<jsp:include page="include/footer.jsp" />
