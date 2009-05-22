<%-- 
    Document   : home
    Created on : May 13, 2009, 9:33:04 PM
    Author     : mhack
--%>

<jsp:include page="include/header.jsp" />
<body>
    <jsp:include page="include/menu_offline.jsp" />
    <br>
    <div>
        <form name="form_login" action="?a=register" method="POST">
            <table>
                <tr>
                <td style="text-align:right">username:
                <td><input type="text" name="username" value="<%= request.getAttribute("username") %>" size="20" />
                <tr>
                <td style="text-align:right">e-mail:
                <td><input type="text" name="email" value="<%= request.getAttribute("email") %>" size="20" />
                <tr>
                <td style="text-align:right">real name:
                <td><input type="text" name="realname" value="<%= request.getAttribute("realname") %>" size="20" />
                <tr>
                <td style="text-align:right">password:
                <td><input type="password" name="password" value="<%= request.getAttribute("password") %>" size="20" />
                <tr>
                <td>
                <td style="text-align:right">
                <input type="submit" value="Create" name="button_create" />
            </table>
        </form>
        <br>
    </div>
    <div>
        <p id="warning" class="warning"><%= request.getAttribute("warning")%></p>
    </div>
</body>
<jsp:include page="include/footer.jsp" />
