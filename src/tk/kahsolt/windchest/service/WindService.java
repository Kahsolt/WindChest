package tk.kahsolt.windchest.service;

import org.springframework.stereotype.Service;
import tk.kahsolt.windchest.DAO.WindDAO;
import tk.kahsolt.windchest.entity.WindEntity;

import javax.annotation.Resource;

/**
 * Created by kahsolt on 17-6-12.
 */
@Service
public class WindService {

    @Resource
    private WindDAO windDao;
    public WindDAO getWindDao() { return windDao; }
    public void setWindDao(WindDAO windDao) { this.windDao = windDao; }

    public void format(WindEntity windEntity) {
        switch (windEntity.getType()) {
            case "text":
                break;
            case "link":
                break;
            case "image":
                break;
            case "audio":
                break;
            case "video":
                break;
            default:
                System.out.print("WindService: Unknown wind type!");
                break;
        }
    }

}
