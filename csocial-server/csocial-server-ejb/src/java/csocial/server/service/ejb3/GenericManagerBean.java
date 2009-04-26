package csocial.server.service.ejb3;

import csocial.server.service.GenericManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public abstract class GenericManagerBean<T, ID extends Serializable> 
        implements GenericManager<T, ID>
{
    private Class<T> entityBeanType;
    private EntityManager em;

    public GenericManagerBean() {
        this.entityBeanType = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PersistenceContext(unitName = "csocial-pu")
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        if (em == null) {
            throw new IllegalStateException("EntityManager has not been set");
        }
        return em;
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    public T findById(ID id) {
        return em.find(getEntityBeanType(), id);
    }

    public T save(T entity) {
        return em.merge(entity);
    }

    protected T getSingleResult(Query q) {
        T object;
        try {
            object = (T)q.getSingleResult();
        } catch (NoResultException e) {
            object = null;
        }
        return object;
    }
}
