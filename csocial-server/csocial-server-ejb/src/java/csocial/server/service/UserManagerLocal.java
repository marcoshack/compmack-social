/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.User;
import javax.ejb.Local;

/**
 *
 * @author mhack
 */
@Local
public interface UserManagerLocal {

    public User getUser(long id);
    
}
