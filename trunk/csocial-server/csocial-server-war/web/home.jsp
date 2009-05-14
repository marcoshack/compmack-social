<%-- 
    Document   : home
    Created on : May 13, 2009, 9:33:04 PM
    Author     : mhack
--%>

<jsp:include page="include/header.jsp" />
<body>
    <jsp:include page="include/menu.jsp" />

    <p>Voce tem <%= request.getAttribute("friend_count")%> amigos</p>
    <br>

</body>
<jsp:include page="include/footer.jsp" />
