/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.UsuarioDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;

/**
 *
 * @author Mateus
 */
public class ServletPOST extends HttpServlet {

    private static final long serialVersionUID = -542154924971485L;
    public static final String NAMEINPUT = "nameinput";
    public static final String EMAILINPUT = "emailinput";
    public static final String TYPEINPUT = "typeinput";
    public static final String IDINPUT = "idinput";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String t = request.getParameter(this.TYPEINPUT);
        if (t == null) {
            response.sendRedirect(request.getParameter("from") + "?rs=2&erro=TYPE==NULL");
        } else if (t.equals("insert")) {
            String userStr = request.getParameter(NAMEINPUT);
            String emailStr = request.getParameter(EMAILINPUT);

            //System.out.println("user: " + userStr);
            //System.out.println("email: " + emailStr);
            UsuarioDao uDao = new UsuarioDao();
            Usuario user = new Usuario(userStr, emailStr);
            uDao.insert(user);

            response.sendRedirect(request.getParameter("from") + "?rs=1");
        } else if (t.equals("update")) {
            System.out.println("CHEGOU NO UPDATE");
            String idStr = request.getParameter(IDINPUT);
            String userStr = request.getParameter(NAMEINPUT);
            String emailStr = request.getParameter(EMAILINPUT);

            Usuario user = new Usuario(Long.parseLong(idStr), userStr, emailStr);

            UsuarioDao uDao = new UsuarioDao();
            uDao.update(user);

            System.out.println("REDIRECT->> " + request.getParameter("from") + "&rs=0");

            response.sendRedirect(request.getParameter("from") + "&rs=0");
        } else if (t.equals("delete")) {
            System.out.println("CHEGOU NO DELETE");
            String idStr = request.getParameter(IDINPUT);

            UsuarioDao uDao = new UsuarioDao();

            uDao.delete(Long.parseLong(idStr));
            response.sendRedirect(request.getParameter("from") + "?rs=1");
        }
    }
}
