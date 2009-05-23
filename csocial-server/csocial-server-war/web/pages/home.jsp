<%-- 
    Document   : home
    Created on : May 13, 2009, 9:33:04 PM
    Author     : mhack
--%>

<jsp:include page="include/header.jsp" />
<body>
    <jsp:include page="include/menu.jsp" />

    <p>You have <%= request.getAttribute("friend_count")%> friend(s).</p>
    <br>

</body>
<jsp:include page="include/footer.jsp" />
