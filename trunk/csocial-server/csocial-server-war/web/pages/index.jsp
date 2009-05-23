<%-- 
    Document   : index
    Created on : May 22, 2009, 4:25:06 PM
    Author     : mhack
--%>

<%
// Redireciona para aplicacao web protegendo o conteudo da pagina
        String contextName = session.getServletContext().getServletContextName();
        String contextPath = session.getServletContext().getContextPath();
        response.sendRedirect(contextName + contextPath + "/web/");
%>
