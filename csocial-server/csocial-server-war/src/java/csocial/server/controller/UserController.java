/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.controller;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mhack
 */
public class UserController extends HttpServlet {

    @EJB
    private UserManager userManagerBean;
    private Logger log = Logger.getLogger(UserController.class.getName());

    /** 
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO Codigo de teste, substituir pela logica do controller.

        List<User> userList = new ArrayList<User>();

        String strId = request.getParameter("id");
        if (strId != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            userList.add(userManagerBean.findById(id));
        }

        String username = request.getParameter("username");
        if (username != null) {
            userList.add(userManagerBean.findByUsername(username));
        }

        String pattern = request.getParameter("pattern");
        if (pattern != null) {
            userList.addAll(userManagerBean.find(pattern));
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            for (User u : userList) {
                out.println(u);
                out.print("<br/>");
            }
        } finally {
            out.close();
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "User controller";
    }
}
