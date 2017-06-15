package tk.kahsolt.windchest.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.entity.WindEntity;

import java.util.List;

/**
 * Created by kahsolt on 17-6-10.
 */

@Repository
public class WindDAO extends HibernateDaoSupport{

    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

    // property constants
    public static final String TEXT = "text";
    public static final String TYPE = "type";
    public static final String PATH = "path";

    public static WindDAO getFromApplicationContext(ApplicationContext ctx) {
        return (WindDAO) ctx.getBean("windDao");
    }

    public void save(WindEntity transientInstance) {
        log.debug("saving Wind instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(WindEntity persistentInstance) {
        log.debug("deleting Wind instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public WindEntity findById(java.lang.Integer id) {
        log.debug("getting User instance with id: " + id);
        try {
            WindEntity instance = (WindEntity) getHibernateTemplate().get("tk.kahsolt.windchest.entity.WindEntity", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("getById failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding User instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from WindEntity as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByText(Object text) {
        return findByProperty(TEXT, text);
    }
    public List findByPath(Object path) { return findByProperty(PATH, path); }

}
