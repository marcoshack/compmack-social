/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.controller;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhack
 */
public class RegisterController extends BaseController {

    @EJB
    private UserManager userManager;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("warning", "");

        if (req.getParameter("button_create") != null) {
            processAccountCreation(req, res);

        } else {
            req.setAttribute("username", "");
            req.setAttribute("realname", "");
            req.setAttribute("password", "");
            req.setAttribute("email", "");
            dispatch(req, res, "/register.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Register Controller";
    }

    private void processAccountCreation(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String realname = req.getParameter("realname");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        req.setAttribute("username", username);
        req.setAttribute("realname", realname);
        req.setAttribute("password", password);
        req.setAttribute("email", email);

        if (username == null || username.length() < 5) {
            req.setAttribute("warning", "Invalid username, it must be at least 5 characters long.");
            dispatch(req, res, "/register.jsp");
        }

        if (realname == null || realname.length() < 5) {
            req.setAttribute("warning", "Invalid real name, it must be at least 5 characters long.");
            dispatch(req, res, "/register.jsp");
        }

        if (email == null || !Pattern.matches(".*@.*", email)) {
            req.setAttribute("warning", "Invalid e-mail address.");
            dispatch(req, res, "/register.jsp");
        }

        if (password == null || password.length() < 5) {
            req.setAttribute("warning", "Invalid password, it must be at least 5 characters long.");
            dispatch(req, res, "/register.jsp");
        }

        User newUser = userManager.findByUsername(username);
        if (newUser != null) {
            req.setAttribute("warning", "Username already exists.");
            dispatch(req, res, "/register.jsp");
        }

        // TODO [mhack] criar usuario com factory method no UserManager
        newUser = new User();
        newUser.setUsername(username);
        newUser.setRealName(realname);
        newUser.setEmail(email);
        // TODO [mhack] password criptografado
        newUser.setPassword(password);
        userManager.save(newUser);

        HttpSession session = req.getSession();
        session.setAttribute("user_id", newUser.getId());
        session.setAttribute("username", newUser.getUsername());

        dispatch(req, res, "/?a=home");
    }
}
