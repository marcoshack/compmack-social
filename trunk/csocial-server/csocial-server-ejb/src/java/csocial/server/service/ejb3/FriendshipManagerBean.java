package csocial.server.service.ejb3;

import csocial.server.entity.Friendship;
import csocial.server.entity.FriendshipRequest;
import csocial.server.entity.FriendshipRequestStatus;
import csocial.server.entity.User;
import csocial.server.service.FriendshipManager;
import csocial.server.service.FriendshipRequestAlreadyExistException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public class FriendshipManagerBean extends GenericManagerBean<Friendship, Long> 
        implements FriendshipManager
{
    
    public Friendship findFriendship(User owner, User friend) {
        Query q = getEntityManager().createQuery("FROM Friendship f WHERE "
                + "f.owner = :owner AND f.friend = :friend");
        q.setParameter("owner", owner);
        q.setParameter("friend", friend);
        return getSingleResult(q);
    }

    public List<User> getFriendList(User user) {
        Query q = getEntityManager().createQuery("SELECT f.friend FROM "
                + "Friendship f WHERE f.owner = :user");
        q.setParameter("user", user);
        return q.getResultList();
    }

    public void addFriendshipRequest(FriendshipRequest request) 
            throws FriendshipRequestAlreadyExistException
    {
        if (findFriendship(request.getFromUser(), request.getToUser()) != null)
        {
            throw new FriendshipRequestAlreadyExistException();
        }
        getEntityManager().persist(request);
    }

    public void acceptFriendshipRequest(FriendshipRequest request) {
        Date now = GregorianCalendar.getInstance().getTime();
        Friendship f1 = new Friendship();
        f1.setOwner(request.getFromUser());
        f1.setFriend(request.getToUser());
        f1.setStartDate(now);
        getEntityManager().persist(f1);

        Friendship f2 = new Friendship();
        f2.setOwner(request.getToUser());
        f2.setFriend(request.getFromUser());
        f2.setStartDate(now);
        getEntityManager().persist(f2);

        request.setStatus(FriendshipRequestStatus.ACCEPTED);
        getEntityManager().merge(request);
    }

    public void rejectFriendshipRequest(FriendshipRequest request) {
        request.setStatus(FriendshipRequestStatus.REJECTED);
        getEntityManager().merge(request);
    }

    public void ackFriendshipRequest(FriendshipRequest request) {
        request.setStatus(FriendshipRequestStatus.ACK);
        getEntityManager().merge(request);
    }

    public List<FriendshipRequest> getPendingFriendshipRequestList(User user) {
        Query q = getEntityManager().createQuery("FROM FriendshipRequest fq "
                + "WHERE fq.toUser = :user");
        q.setParameter("user", user);
        return q.getResultList();
    }

    public List<FriendshipRequest> getSentFriendshipRequestList(User user) {
        Query q = getEntityManager().createQuery("FROM FriendshipRequest fq "
                + "WHERE fq.fromUser = :user");
        q.setParameter("user", user);
        return q.getResultList();
    }
}
