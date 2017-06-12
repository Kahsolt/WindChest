package tk.kahsolt.windchest.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.entity.ChestEntity;
import tk.kahsolt.windchest.entity.UserEntity;
import tk.kahsolt.windchest.service.UserService;

import javax.annotation.Resource;
import javax.xml.ws.Action;
import java.util.Map;

/**
 * Created by kahsolt on 17-6-10.
 */

@Repository
public class ChestAction extends ActionSupport {

    @Resource
    private UserEntity user;
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    @Resource
    private ChestEntity chest;
    public ChestEntity getChest() { return chest; }
    public void setChest(ChestEntity chest) { this.chest = chest; }

    @Resource
    private UserService userService;
    public UserService getUserService() { return userService; }
    public void setUserService(UserService userService) { this.userService = userService; }

    @Override
    public String execute() {
        return ERROR;
    }

    @Override
    public void validate() {

    }

    @Action
    public String enter() {
        Map session = ActionContext.getContext().getSession();
        session.put("chest", chest.getName());
        return SUCCESS;
    }

    @Action
    public String leave() {
        Map session = ActionContext.getContext().getSession();
        session.put("chest", null);
        return SUCCESS;
    }


}
