package tk.kahsolt.windchest.DAO;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.entity.UserEntity;

import javax.jws.soap.SOAPBinding;

/**
 * Created by kahsolt on 17-6-7.
 */

@Repository
public class UserDAO extends HibernateDaoSupport {
    
    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
    
    // property constants
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
        return (UserDAO) ctx.getBean("userDao");
    }

    public void save(UserEntity transientInstance) {
        log.debug("saving User instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(UserEntity persistentInstance) {
        log.debug("deleting User instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public UserEntity update(UserEntity persistentInstance) {
        log.debug("updating UserEntity instance");
        try {
            getHibernateTemplate().update(persistentInstance);
            UserEntity result = (UserEntity) findByUsername(persistentInstance.getUsername()).get(0);
            log.debug("update successful");
            return result;
        } catch (RuntimeException re) {
            log.error("update failed", re);
            throw re;
        }
    }

    public UserEntity findById(java.lang.Integer id) {
        log.debug("getting User instance with id: " + id);
        try {
            UserEntity instance = (UserEntity) getHibernateTemplate().get("tk.kahsolt.windchest.entity.UserEntity", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("getById failed", re);
            throw re;
        }
    }

    public List findByExample(UserEntity instance) {
        log.debug("finding User instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding User instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from UserEntity as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByUsername(Object username) {
        return findByProperty(USERNAME, username);
    }

    public List findByPassword(Object password) {
        return findByProperty(PASSWORD, password);
    }

    public List findAll() {
        log.debug("finding all UserEntity instances");
        try {
            String queryString = "from UserEntity";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public UserEntity merge(UserEntity detachedInstance) {
        log.debug("merging UserEntity instance");
        try {
            UserEntity result = (UserEntity) getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(UserEntity instance) {
        log.debug("attaching dirty UserEntity instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(UserEntity instance) {
        log.debug("attaching clean UserEntity instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }


}