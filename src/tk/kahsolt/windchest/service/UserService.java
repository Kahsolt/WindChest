package tk.kahsolt.windchest.service;

import javax.annotation.Resource;
import java.util.List;

import org.springframework.stereotype.Service;
import tk.kahsolt.windchest.DAO.UserDAO;
import tk.kahsolt.windchest.entity.UserEntity;

/**
 * Created by kahsolt on 17-6-7.
 */

@Service
public class UserService {

    @Resource
    private UserDAO userDao;
    public UserDAO getUserDao() { return userDao; }
    public void setUserDao(UserDAO userDao) { this.userDao = userDao; }

    public boolean signin(UserEntity user) {
        List res=userDao.findByUsername(user.getUsername());
        if(res.size()>0) {
            return false;
        } else {
            userDao.save(user);
            return true;
        }
    }
    public UserEntity login(UserEntity user) {
        List res=userDao.findByUsername(user.getUsername());
        if(res.size()>0) {
            UserEntity loginuser=(UserEntity)res.get(0);
            if((loginuser.getUsername().equals(user.getUsername()) && loginuser.getPassword().equals(user.getPassword()))) {
                return loginuser;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public UserEntity updateinfo(UserEntity user) {
        userDao.merge(user);
        return (UserEntity)userDao.findByUsername(user.getUsername()).get(0);
    }
    public boolean leaveout(UserEntity user) {
        List res=userDao.findByUsername(user.getUsername());
        if(res.size()>0) {
            userDao.delete(user);
            return true;
        } else {
            return false;
        }
    }

}