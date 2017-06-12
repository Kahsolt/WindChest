package tk.kahsolt.windchest.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.catalina.User;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.entity.UserEntity;
import tk.kahsolt.windchest.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.util.Map;

/**
 * Created by kahsolt on 17-6-9.
 */
@Repository
public class UserAction extends ActionSupport{

    private UserEntity user;
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    @Resource
    private UserService userService;
    public UserService getUserService() { return userService; }
    public void setUserService(UserService userService) { this.userService = userService; }

    @Override
    public String execute() {
        return ERROR;
    }

    @Action
    public String login() {
        Map session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        UserEntity loginuser=userService.login(user);
        if (loginuser!=null) {
            session.put("user", loginuser);
            request.setAttribute("loginerror", false);
            System.out.println("Login OK!");
            return SUCCESS;
        } else {
            session.put("user", null);
            request.setAttribute("loginerror", true);
            System.out.println("Login Error!");
            return ERROR;
        }
    }

    @Action
    public String logout() {
        Map session = ActionContext.getContext().getSession();
        session.put("user", null);
        System.out.println("Logout OK!");
        return SUCCESS;
    }

    @Action
    public String signin() {
        Map session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        if(userService.signin(user)) {
            session.put("user", user);
            request.setAttribute("signinerror", false);
            System.out.println("Signin OK!");
            return SUCCESS;
        } else {
            request.setAttribute("signinerror", true);
            System.out.println("Signin Error!");
            return ERROR;
        }
    }

    @Action
    public String leaveout() {
        Map session = ActionContext.getContext().getSession();
        if(userService.leaveout(user)) {
            session.put("user", null);
            System.out.println("Leaveout OK!");
            return SUCCESS;
        } else {
            System.out.println("Leaveout Error!");
            return ERROR;
        }
    }

    @Action
    public String updateinfo() {
        Map session = ActionContext.getContext().getSession();
        UserEntity newUser=userService.updateinfo(user);
        if(newUser!=null) {
            session.put("user", newUser);
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
