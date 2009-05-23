/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.entity.collection;

import csocial.server.entity.Message;
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
@XmlRootElement(name = "message_list")
@XmlSeeAlso(MessageList.class)
@XmlAccessorType(XmlAccessType.NONE)
public class MessageList extends ArrayList<Message> {
    public MessageList() {
        super();
    }

    public MessageList(List<Message> messageList) {
        super(messageList);
    }

    @XmlElementRef
    public List<Message> getUserList() {
        return this;
    }
}
