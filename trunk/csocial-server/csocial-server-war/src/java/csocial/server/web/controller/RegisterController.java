/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.web.controller;

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
    private static final String warning_param = "warning";

    @EJB
    private UserManager userManager;

    private String username;
    private String realname;
    private String password;
    private String email;
    private String warning_message;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("warning", "");

        if (req.getParameter("button_create") != null) {
            username = req.getParameter("username");
            realname = req.getParameter("realname");
            password = req.getParameter("password");
            email = req.getParameter("email");
            req.setAttribute("username", username);
            req.setAttribute("realname", realname);
            req.setAttribute("password", password);
            req.setAttribute("email", email);

            processAccountCreation(req, res);

        } else {
            req.setAttribute("username", "");
            req.setAttribute("realname", "");
            req.setAttribute("password", "");
            req.setAttribute("email", "");
            dispatch(req, res, getResourcePath("register.jsp"));
        }
    }

    @Override
    public String getServletInfo() {
        return "Register Controller";
    }

    private void processAccountCreation(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {

        if (!validateParameters(req)) {
            // Parametros invalidos, volta na tela de registro.
            req.setAttribute(warning_param, warning_message);
            dispatch(req, res, getResourcePath("register.jsp"));

        } else {
            // Parametros validos, realiza registro.

            // TODO [mhack] criar usuario com factory method no UserManager
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setRealName(realname);
            newUser.setEmail(email);
            // TODO [mhack] password criptografado
            newUser.setPassword(password);
            userManager.save(newUser);

            HttpSession session = req.getSession();
            session.setAttribute("user_id", newUser.getId());
            session.setAttribute("username", newUser.getUsername());

            dispatch(req, res, getResourcePath("?a=home"));
        }
    }

    private boolean validateParameters(HttpServletRequest req) {
        boolean isValid = true;

        if (username == null || username.length() < 5) {
            warning_message =
                    "Invalid username, it must be at least 5 characters long.";
            isValid = false;
        }

        if (realname == null || realname.length() < 5) {
            warning_message =
                    "Invalid real name, it must be at least 5 characters long.";
            isValid = false;
        }

        if (email == null || !Pattern.matches(".*@.*", email)) {
            warning_message =  "Invalid e-mail address.";
            isValid = false;
        }

        if (password == null || password.length() < 5) {
            warning_message =
                    "Invalid password, it must be at least 5 characters long.";
            isValid = false;
        }

        User newUser = userManager.findByUsername(username);
        if (newUser != null) {
            warning_message =  "Username already exists.";
            isValid = false;
        }

        return isValid;
    }
}
