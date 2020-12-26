package br.com.louvemos.api.base;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public abstract class BaseRepositoryHibernate<T> {

    @Autowired
    private EntityManager em;

    protected abstract Class<T> getEntityClass();

    //-- AUXILIARY LIST METHODS
    public List<T> loadByListId(List<Long> ids) {
        DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
        dc.add(Restrictions.in("id", ids));
        return findByCriteria(dc);
    }

    public List<T> listAll() {
        DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
        return findByCriteria(dc);
    }

    //-- CRITERIA METHODS
    public List<T> findByCriteria(DetachedCriteria dc, int firstResult, int maxResults) {
        try {
            dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            Criteria c = dc.getExecutableCriteria(getCurrentSession());
            c.setFirstResult(firstResult);
            if (maxResults > 0) {
                c.setMaxResults(maxResults);
            }

            return (List<T>) c.list();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<T> findByCriteria(DetachedCriteria dc) {
        try {
            return findByCriteria(dc, 0, 0);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public T findByOneCriteria(DetachedCriteria dc) {
        try {
            List<T> list = findByCriteria(dc, 0, 0);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
            return null;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    //-- BASIC SESSION METHODS
    public Session openSession() {
        return em.unwrap(Session.class).getSessionFactory().openSession();
    }

    public Session getCurrentSession() {
        return em.unwrap(Session.class);
    }

    public T loadByIdNewSession(Long id) {
        return (T) openSession().get(getEntityClass(), id);
    }

    public T loadById(Long id) {
        return (T) getCurrentSession().get(getEntityClass(), id);
    }

    public T save(T t) {
        getCurrentSession().save(t);
        return t;
    }

    public T update(T t) {
        getCurrentSession().update(t);
        return t;
    }

    public T saveOrUpdate(T t) {
        getCurrentSession().saveOrUpdate(t);
        return t;
    }

    public void delete(T t) {
        getCurrentSession().delete(getCurrentSession().contains(t) ? t : getCurrentSession().merge(t));
        this.flush();
    }

    public void evict(T t) {
        getCurrentSession().evict(t);
    }

    public void refresh(T t) {
        getCurrentSession().flush();
        getCurrentSession().refresh(t);
    }

    public T merge(T t) {
        return (T) getCurrentSession().merge(t);
    }

    public void flush() {
        getCurrentSession().flush();
    }

    public void clear() {
        getCurrentSession().clear();
    }

}
