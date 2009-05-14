package csocial.server.auth;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhack
 */
public class AuthFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    @EJB
    private UserManager userManager;

    public AuthFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Obtem user_id da sessao, se houver
        HttpSession session = ((HttpServletRequest) req).getSession(true);
        Long userID = (Long) session.getAttribute("user_id");

        User user = null;
        // Usuario nao esta autenticado e submeteu informacoes de login
        if (userID == null && username != null && password != null) {
            user = authenticate(username, password);
            if (user != null) {
                session.setAttribute("user_id", user.getId());
                session.setAttribute("username", user.getUsername());
            }
        } else if (userID != null) {
            user = userManager.findById(userID);
        }

        String action = req.getParameter("a");
        if (user != null || action != null && action.equals("register")) {
            // Autenticou ou estah se registrando
            processFilterChain(req, res, chain);

        } else {
            // Nao autenticou, retorna a tela de login
            dispatch(req, res, "/login.jsp");
        }
    }

    private void dispatch(ServletRequest req, ServletResponse res,
            String page) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(true);
        ServletContext context = session.getServletContext();
        context.getRequestDispatcher(page).forward(req, res);
    }

    private void processFilterChain(ServletRequest req, ServletResponse res,
            FilterChain chain) {
        try {
            chain.doFilter(req, res);

        } catch (Throwable t) {
            log("Error processing AuthFilter Chain", t);
        }
    }

    /**
     * Destroy method for this filter 
     */
    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    private User authenticate(String username, String password) {
        User user = userManager.findByUsername(username);

        // TODO [mhack] usar senha criptografada
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    public void log(String msg, Throwable t) {
        filterConfig.getServletContext().log(msg, t);
    }
}
