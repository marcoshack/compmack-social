<%-- 
    Document   : header
    Created on : May 13, 2009, 9:38:42 PM
    Author     : mhack
--%>
<div id="menu" style="background-color:#eeeeee;">
<table id="table_menu" style="width:100%;">
    <tr>
    <td><a class="logo" href="<%= session.getAttribute("app_path") %>">CSocial</a></td>
    <td align="left">
        <a href="?a=home">Home</a>
        | <a href="?a=friends">Friends</a>
        | <a href="?a=messages">Messages</a>
        | <a href="?a=photos">Photos</a>
        | <a href="?a=videos">Videos</a>
    </td>
    <td align="right">
        <label style="font-weight:bold"><%= session.getAttribute("username")%></label>
        | <a href="?a=profile">Profile</a>
        | <a href="?a=logout">Logout</a>
    </td>
</table>
</div>