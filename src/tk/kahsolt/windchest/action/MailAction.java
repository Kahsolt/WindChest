package tk.kahsolt.windchest.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;
import tk.kahsolt.windchest.DAO.UserDAO;
import tk.kahsolt.windchest.DAO.WindDAO;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.util.Date;
import java.util.Properties;

/**
 * Created by kahsolt on 17-6-15.
 */
@Repository
public class MailAction extends ActionSupport {

    private static final String SITEBASEURL="http://windchest.kahsolt.cc";

    private static final String fromAccount = "kahsolt@qq.com";
    private static final String fromPassword = "egcjzgeuyhflbfhe";
    private static final String fromSMTPHost = "smtp.qq.com";

    @Resource
    private UserDAO userDao;
    public UserDAO getUserDao() { return userDao; }
    public void setUserDao(UserDAO userDao) { this.userDao = userDao; }

    @Resource
    private WindDAO windDao;
    public WindDAO getWindDao() { return windDao; }
    public void setWindDao(WindDAO windDao) { this.windDao = windDao; }
    private String uid;
    private String wid;

    @Override
    public String execute() {
        return ERROR;
    }


    @Action
    public String send() throws Exception {

        String toAccount="";
        String downloadLink="";
        String fileName="";

        if(getUid() ==null|| getWid() ==null) {
            System.out.println("MailAction: null uid/wid!");
            return ERROR;
        }
        toAccount = userDao.findById(Integer.parseInt(getUid())).getEmail();
        downloadLink = windDao.findById(Integer.parseInt(getWid())).getPath();
        fileName = windDao.findById(Integer.parseInt(getWid())).getText();
        if(toAccount.equals("")||downloadLink.equals("")) {
            System.out.println("MailAction: null mail/filepath-info record!");
            return ERROR;
        }
        downloadLink=SITEBASEURL+downloadLink;

        // 1. 创建参数配置, 用于连接邮件服务器
        Properties props = new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", fromSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        /* or SSL
        props.setProperty("mail.smtp.port", fromSMTPPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", fromSMTPPort);
        */

        // 2. 构造邮件
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAccount, "Windchest", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAccount, "Windchaser", "UTF-8"));
        message.setSubject("A Wind transfered from WindChest", "UTF-8");
        String msg="<p>这是转发自WindChest的文件链接，点击可以下载：</p>";
        msg+="<a href=\""+downloadLink+"\">"+fileName+"</a>";
        message.setContent(msg, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();

        // 3. 发送邮件
        Transport transport = session.getTransport();
        transport.connect(fromAccount, fromPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("mailOK","OK");
        return SUCCESS;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }
}
