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

        <%
        java.util.List<User> friends = (java.util.List<User>) request.getAttribute("friend_list");
        java.util.List<User> search = (java.util.List<User>) request.getAttribute("search_result");
        if (search == null || search.size() == 0) {
        %>
        <table>
            <%
            if (friends != null) {
                for (User u : friends) {
            %>
            <tr>
              <td>(<a href="<%= session.getAttribute("app_path") %>/web/?a=friends&viewFriendID=<%= u.getId() %>">View</a>)</td>
              <td><%= u.getRealName() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr><td>You have no friends. Try to search someone.
            <% } %>
        </table>
        <% } else { %>
        <table>
            <%
            for (User u : search) {
            %>
               <tr>
            <% if (friends.contains(u)) { %>
                  <td>(<a href="<%= session.getAttribute("app_path") %>/web/?a=friends&viewFriendID=<%= u.getId() %>">View</a>)</td>
            <% } else { %>
                  <td>(<a href="<%= session.getAttribute("app_path") %>/web/?a=friends&requestFriendID=<%= u.getId() %>">Add</a>)</td>
            <% } %>
                  <td><%= u.getRealName() %></td>
               </tr>
           <% } %>
        </table>
        <% } %>

    </div>
    <br>

</body>
<jsp:include page="include/footer.jsp" />
