/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mhack
 */
public class VideoController extends BaseController {
   
    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        dispatch(req, res, getResourcePath("video.jsp"));
    }


    @Override
    public String getServletInfo() {
        return "Message Controller";
    }
}
