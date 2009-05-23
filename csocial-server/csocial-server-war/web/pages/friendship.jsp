<%-- 
    Document   : home
    Created on : May 13, 2009, 9:33:04 PM
    Author     : mhack
--%>

<%@ page import="csocial.server.entity.*" %>

<jsp:include page="include/header.jsp" />
<body>
    <jsp:include page="include/menu.jsp" />

    <br>
    <div>
        <form name="form_friend_search" action="?a=friends" method="POST">
            <table>
                <tr>
                <td><input type="text" name="search_friends" value="<%= request.getAttribute("search_friends")%>" size="30" />
                <input type="submit" value="Search Friends" name="button_search" />
            </table>
        </form>
        <br>

        <% if (session.getAttribute("search_result") == null) {
        %>
        <table>
            <%
     java.util.List<User> friends = (java.util.List<User>) session.getAttribute("friend_list");
     if (friends != null) {
         for (User u : friends) {
            %>
            <tr><td><%= u%>
            <%
                }
            } else {
            %>
            <tr><td>You have no friends. Try to search someone.
            <%     }
            %>
        </table>
        <% }%>

    </div>
    <br>

</body>
<jsp:include page="include/footer.jsp" />
