package tk.kahsolt.windchest.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import tk.kahsolt.windchest.websocket.WindChest;

import java.io.*;

/**
 * Created by kahsolt on 17-6-12.
 */
public class UploadAction extends ActionSupport {
    private final String PATHBASE = "/uploads";
    private File file;// 对应文件域的file，封装文件内容
    private String fileContentType;// 封装文件类型
    private String fileFileName;// 封装文件名
    private String savePath;// 保存路径
    private String title;// 文件标题

    public String execute() throws Exception {
        if(file==null) {
            System.out.println("No file found!!");
            return ERROR;
        }
        String path= ServletActionContext.getServletContext().getRealPath(PATHBASE);
        String savepath = path + "/" + getFileFileName();
        System.out.println(savepath);

        BufferedInputStream bis=null;
        BufferedOutputStream bos=null;
        try {
            bis=new BufferedInputStream(new FileInputStream(getFile()));
            bos=new BufferedOutputStream(new FileOutputStream(savepath));
            byte[] buf=new byte[(int) getFile().length()];
            int len=0;
            while((len=bis.read(buf))!=-1) {
                bos.write(buf,0, len);
            }
            WindChest.broadcast("A new File");
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bis!=null) bis.close();
            if(bos!=null) bos.close();
        }
        return ERROR;
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public String getFileFileName() {
        return fileFileName;
    }
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    public String getFileContentType() {        return fileContentType;    }
    public void setFileContentType(String fileContentType) {        this.fileContentType = fileContentType;}
    public String getSavePath() {        return savePath;}
    public void setSavePath(String savePath) {        this.savePath = savePath;}
    public String getTitle() {        return title;}
    public void setTitle(String title) {        this.title = title;}
}
