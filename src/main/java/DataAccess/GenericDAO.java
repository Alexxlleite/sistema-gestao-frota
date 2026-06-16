package DataAccess;

import java.util.List;
import javax.persistence.EntityManager;

public class GenericDAO<T> {

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T getById(Object id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }

    public void persist(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception erro) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw erro;
        } finally {
            em.close();
        }
    }

    public T merge(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            T salvo = em.merge(entity);
            em.getTransaction().commit();
            return salvo;
        } catch (Exception erro) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw erro;
        } finally {
            em.close();
        }
    }

    public void removeById(Object id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            T entity = em.find(entityClass, id);

            if (entity != null) {
                em.remove(entity);
            }

            em.getTransaction().commit();
        } catch (Exception erro) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw erro;
        } finally {
            em.close();
        }
    }
}