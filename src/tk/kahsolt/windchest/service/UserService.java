package tk.kahsolt.windchest.service;

import javax.annotation.Resource;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;
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
    public UserEntity updateinfo(UserEntity user, UserEntity oldUser) {
        if(user.getNickname()!=null&&!user.getNickname().equals("")) {
            oldUser.setNickname(user.getNickname());
        }
        if(user.getPassword()!=null&&!user.getPassword().equals("")) {
            oldUser.setPassword(user.getPassword());
        }
        if(user.getAvatar()!=null&&!user.getAvatar().equals("")) {
            oldUser.setAvatar(user.getAvatar());
        }
        if(user.getEmail()!=null&&!user.getEmail().equals("")) {
            oldUser.setEmail(user.getEmail());
        }
        userDao.update(oldUser);
        return (UserEntity)userDao.findByUsername(oldUser.getUsername()).get(0);
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