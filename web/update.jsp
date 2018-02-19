<%-- 
    Document   : index
    Created on : 19/02/2018, 11:37:41
    Author     : Mateus
--%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="model.Usuario"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Usuario usuario = null;
    int ctrl = -1;

    String NAMEINPUT = servlet.ServletPOST.NAMEINPUT;
    String EMAILINPUT = servlet.ServletPOST.EMAILINPUT;
    String TYPEINPUT = servlet.ServletPOST.TYPEINPUT;
    String IDINPUT = servlet.ServletPOST.IDINPUT;

    String idStr = (String) request.getParameter("id");
    if (idStr != null) {
        System.out.println("idStr != null");
        Long id = null;
        try {
            id = Long.parseLong(idStr.trim());
        } catch (Exception e) {
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", new String("/HelloWeb/") + "?erro=Parametro invalido");
        }
        if (id != null) {
            UsuarioDao uDao = new UsuarioDao();
            usuario = uDao.findById(id);
            /*if (usuario == null) {
                ctrl = 1;
                //System.out.println("usuario==null" + (usuario == null));
            } else {
                System.out.println("ACHOU USUARIO");
            }*/
        } else {
        }
    } else {
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", new String("/HelloWeb/") + "?erro=Parametro invalido");
    }
%>

<html>
    <head>
        <jsp:include page="header.jspf"/>
        <title>JSP CRUD - Exemplo: Update</title>
    </head>
    <body>
        <!-- Fixed navbar -->
        <jsp:include page="cabecalho.jspf"/>

        <!-- content -->
        <div class="container">
            <% String rs = (String) request.getParameter("rs");

                if (ctrl == 0 || (rs != null && rs.equals("0"))) {%>
            <div class="alert alert-success">
                <strong>Success!</strong> User updated.
            </div>
            <%} else if (ctrl == 1 || (rs != null && rs.equals("1"))) {%>
            <div class="alert alert-danger">
                <strong>Success!</strong> User NOT exists.
            </div>
            <%} else if (ctrl == 2 || (rs != null && rs.equals("2"))) {%>
            <div class="alert alert-danger">
                <strong>Success!</strong> User NOT updated.
            </div>
            <% }%>
            <!-- Main component for a primary marketing message or call to action -->
            <div class="jumbotron">
                <h1>Update</h1>
                <form class="form-horizontal" action="ServletPOST" method="post">
                    <fieldset>
                        <!-- change col-sm-N to reflect how you would like your column spacing (http://getbootstrap.com/css/#forms-control-sizes) -->

                        <!-- Form Name -->
                        <legend>New user</legend>

                        <!-- Text input http://getbootstrap.com/css/#forms -->
                        <div class="form-group">
                            <label for="nameinput" class="control-label col-sm-2">Nome</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="<%=NAMEINPUT%>" name="<%=NAMEINPUT%>" placeholder="John Smith" value="<%=usuario.getNome()%>"required="">
                                <p class="help-block">Insert your nome</p>
                            </div>
                        </div>
                        <!-- Text input http://getbootstrap.com/css/#forms -->
                        <div class="form-group">
                            <label for="emailinput" class="control-label col-sm-2">Email</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="<%=EMAILINPUT%>" name="<%=EMAILINPUT%>" placeholder="ex@example.com" value="<%=usuario.getEmail()%>" required="">
                                <p class="help-block">Insert your email</p>
                            </div>
                        </div>
                        <!-- Button http://getbootstrap.com/css/#buttons -->
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="singlebutton"></label>
                            <div class="text-right col-sm-10">
                                <input type="submit" class="btn btn-primary" aria-label="" value="Enviar"></input>

                            </div>
                        </div>
                        <input type="hidden" name="from" id="from" value="${pageContext.request.requestURI}?id=${param.id}">
                        <input type="hidden" name="<%=TYPEINPUT%>" id="<%=TYPEINPUT%>" value="update">
                        <input type="hidden" name="<%=IDINPUT%>" id="<%=IDINPUT%>" value="<%=usuario.getId()%>">
                    </fieldset>
                </form>
            </div><!--.jumbtrom-->
        </div> <!-- /container -->
        <!--footer-->
        <jsp:include page="footer.jspf"/>
    </body>
</html>
