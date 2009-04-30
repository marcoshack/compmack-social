package csocial.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
@Entity
@Table(name = "friendship_requests")
public class FriendshipRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private User fromUser;
    private User toUser;
    private FriendshipRequestStatus status;
    private String message;
    private Date requestDate;

    public FriendshipRequest() {
        setStatus(FriendshipRequestStatus.PENDING);
        setRequestDate(GregorianCalendar.getInstance().getTime());
    }

    public FriendshipRequest(User from, User to, String requestMessage) {
        this();
        setFromUser(from);
        setToUser(to);
        setMessage(requestMessage);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FriendshipRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipRequestStatus status) {
        this.status = status;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @ManyToOne
    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendshipRequest)) {
            return false;
        }
        FriendshipRequest other = (FriendshipRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FriendshipRequest[id = ");
        sb.append(id);
        sb.append(", from = ").append(fromUser);
        sb.append(", to = ").append(toUser);
        sb.append(", date = ").append(requestDate);
        sb.append(", message = ").append(message);
        return sb.toString();
    }
}
