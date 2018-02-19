<%-- 
    Document   : delete
    Created on : 19/02/2018, 11:37:41
    Author     : Mateus
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="model.Usuario"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    boolean ctrl = false;

    String NAMEINPUT = servlet.ServletPOST.NAMEINPUT;
    String EMAILINPUT = servlet.ServletPOST.EMAILINPUT;
    String TYPEINPUT = servlet.ServletPOST.TYPEINPUT;
    String IDINPUT = servlet.ServletPOST.IDINPUT;

    UsuarioDao uDao = new UsuarioDao();
    List<Usuario> usuarios = uDao.getAll();

%>

<html>
    <head>
        <jsp:include page="header.jspf"/>
        <title>JSP CRUD - Exemplo: Listar</title>
    </head>
    <body>
        <!-- Fixed navbar -->
        <jsp:include page="cabecalho.jspf"/>

        <!-- content -->
        <div class="container">
            <% String str = request.getParameter("rs");
                if (ctrl || (str != null && str.equals("1"))) {%>
            <div class="alert alert-success">
                <strong>Success!</strong> User deleted.
            </div>
            <%}
                if (str != null && str.equals("2")) {%>
            <div class="alert alert-danger">
                <strong>Success!</strong> User NOT deleted.
            </div>
            <% }%>

            <!-- Main component for a primary marketing message or call to action -->
            <div class="jumbotron">
                <h1>Listar</h1>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Ação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Usuario in : usuarios) {%>
                        <tr>
                            <td><%=in.getId()%></td>
                            <td><%=in.getNome()%></td>
                            <td><%=in.getEmail()%></td>
                            <td>
                                <form action="ServletPOST" method="post">
                                    <input type="button" onclick="location.href = 'update.jsp?id=<%=in.getId()%>';" class="btn btn-danger" aria-label="" value="Editar"</input>
                                    <input type="submit" class="btn btn-danger" aria-label="" value="Deletar"></input>
                                    <input type="hidden" name="<%=IDINPUT%>" id="<%=IDINPUT%>" value="<%=in.getId()%>">
                                    <input type="hidden" name="<%=TYPEINPUT%>" id="<%=TYPEINPUT%>" value="delete">
                                    <input type="hidden" name="from" id="from" value="${pageContext.request.requestURI}">
                                </form></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div><!--.jumbtrom-->
        </div> <!-- /container -->
        <!--footer-->
        <jsp:include page="footer.jspf"/>
    </body>
</html>
