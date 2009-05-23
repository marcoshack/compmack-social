/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.entity.collection;

import csocial.server.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author mhack
 */
@XmlRootElement(name = "user_list")
@XmlSeeAlso(User.class)
@XmlAccessorType(XmlAccessType.NONE)
public class UserList extends ArrayList<User> {
    public UserList() {
        super();
    }

    public UserList(List<User> userList) {
        super(userList);
    }

    @XmlElementRef
    public List<User> getUserList() {
        return this;
    }
}
