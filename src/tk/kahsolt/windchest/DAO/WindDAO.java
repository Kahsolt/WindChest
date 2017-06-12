package tk.kahsolt.windchest.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.entity.WindEntity;

/**
 * Created by kahsolt on 17-6-10.
 */

@Repository
public class WindDAO extends HibernateDaoSupport{

    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

    // property constants
    public static final String TYPE = "text";
    public static final String PATH = "/path/";

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
}
