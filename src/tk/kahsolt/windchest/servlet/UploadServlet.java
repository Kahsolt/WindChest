package tk.kahsolt.windchest.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.DAO.WindDAO;
import tk.kahsolt.windchest.entity.WindEntity;
import tk.kahsolt.windchest.websocket.WindChest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by kahsolt on 17-6-9.
 */
@Repository
public class UploadServlet extends HttpServlet {

    private static final String PATHBASE = "/uploads";
    private static final String PATHTMP = PATHBASE + "/tmp";

    @SuppressWarnings({ "static-access", "unchecked" })
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");

        System.out.print("Got a file");

        Map<String,String> map = new HashMap<String,String>();
        String path=PATHBASE;
        try {
            //1.1 创建上传文件的工厂类
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*1024*50);
            factory.setRepository(new File(this.getServletContext().getRealPath(PATHTMP)));
            //1.2 获取上传文件的核心类
            ServletFileUpload fileload = new ServletFileUpload(factory);
            fileload.setHeaderEncoding("UTF-8");
            fileload.setFileSizeMax(1024*1024*100);
            fileload.setSizeMax(1024*1024*200);
            if(!fileload.isMultipartContent(request)){
                throw new RuntimeException("错误：不是文件上传类型！");
            }
            //1.3 解析文件数据
            List<FileItem> list = fileload.parseRequest(request);
            for(FileItem item :list){
                if(item.isFormField()){ //普通字段
                    String name = item.getFieldName();
                    String val = item.getString("UTF-8");
                    map.put(name, val);
                } else {
                    String originalname = item.getName();
                    map.put("file",originalname);
                    String uuidname  = originalname + "_" + UUID.randomUUID().toString();
                    path += ("/" + uuidname);
                    map.put("path", path);
                    System.out.print("UploadFile saved to path: " + path);

                    InputStream is = item.getInputStream();
                    OutputStream os = new FileOutputStream(this.getServletContext().getRealPath(path));
                    byte [] b = new byte[1024];
                    int len = 0;
                    while((len=is.read(b))!=-1){
                        os.write(b, 0, len);
                    }
                    is.close();
                    os.close();
                    item.delete();
                    System.out.println("UploadServlet: 文件上传成功！");

                    /*
                    WindEntity wind = new WindEntity();
                    wind.setUid(1);
                    wind.setChest("NormalChest");
                    wind.setText(originalname);
                    wind.setPath(path);
                    getWindDao().save(wind);

                    WindChest.broadcast(wind);
                    */
                }
            }
        }catch(FileUploadBase.FileSizeLimitExceededException e){
            response.getWriter().write("<script>alert('文件大小不能超过100M!');</script>");
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

}