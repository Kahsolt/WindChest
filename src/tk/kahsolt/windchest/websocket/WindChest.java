package tk.kahsolt.windchest.websocket;

/**
 * Created by kahsolt on 17-6-9.
 */

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Repository;
import sun.plugin2.message.Message;
import sun.rmi.transport.ObjectTable;
import tk.kahsolt.windchest.entity.UserEntity;
import tk.kahsolt.windchest.entity.WindEntity;
import tk.kahsolt.windchest.utils.HTMLFilter;

@Repository
@ServerEndpoint(value = "/windchest")
public class WindChest{

    private static final Log log = LogFactory.getLog(WindChest.class);
    private static final Set<WindChest> connections = new CopyOnWriteArraySet<WindChest>();
    private static final String GUEST_PREFIX = "WindChaser #";
    private static final String GUEST_SUFFIX = "";

    private Session session;
    @Resource
    private UserEntity user;
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    private String chest;

    public WindChest() {
        // do nothing
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        connections.add(this);
        //user.setUsername(GUEST_PREFIX + Integer.parseInt(sess.getId()) + GUEST_SUFFIX);
        //String message = String.format("%s %s", user.getUsername(), " has joined.");
        log.info("Seesion ID: "+session.getId());
        System.out.println("Seesion ID: "+session.getId());

        //String message="欢迎~ Ctrl+Enter可以快速发送～";
        //broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        connections.remove(this);
        System.out.println("[ws] One leaves...");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("[ws] Recieved: "+message);
        String filteredMessage = String.format("%s", HTMLFilter.HTMLEscape(message));
        System.out.println("[ws] Sent: "+filteredMessage);
        if(filteredMessage!=null) {
            broadcast(filteredMessage);
        }
    }

    @OnMessage
    public void OnMessage(Session session, ByteBuffer byteBuffer) {
        System.out.println("[ws] Recieved: [Binaries]");
        if(byteBuffer!=null) {
            broadcast(byteBuffer);
        }
    }

    @OnMessage
    public void OnMessage(Session session, PongMessage pm) {
        System.out.println("[ws] Recieved: #PongMessage");
        try {
            session.close();
        } catch (IOException ioe) {
            // do nothing
        }
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("WebSocket Error: " + t.toString(), t);
    }

    public static void broadcast(Object msg) {
        for (WindChest client : connections) {
            try {
                synchronized (client) {
                    if(msg instanceof String) {
                        client.session.getBasicRemote().sendText((String)msg);
                    } else if(msg instanceof ByteBuffer) {
                        client.session.getBasicRemote().sendBinary((ByteBuffer)msg);
                    } else if(msg instanceof WindEntity) {  // Media
                        String src = ((WindEntity) msg).getPath();
                        String mark = "";
                        switch (((WindEntity) msg).getType()) {
                            case "image":
                                mark = String.format("<img src=\"../%s\" />", src);
                                client.session.getBasicRemote().sendText(mark);
                                break;
                            case "audio":
                                mark = String.format("<audio src=\"../%s\" />", src);
                                client.session.getBasicRemote().sendText(mark);
                                break;
                            case "video":
                                mark = String.format("<video src=\"../%s\" />", src);
                                client.session.getBasicRemote().sendText(mark);
                                break;
                            default:
                                log.error("Unknown WindEntity Type!");
                                break;
                        }

                    } else {
                        log.error("Unknown msg Type!");
                    }
                }
            } catch (IOException e) {
                log.debug("WebSocker Error: Failed to send message to client", e);
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException ioe) {
                    //ioe.printStackTrace();
                }
                //String message = String.format("* %s %s", client.nickname, "has been disconnected.");
                //broadcast(message);
                //log.info(String.format("* %s %s", client.getUser().getUsername(), "has disconnected"));
            }
        }
    }

    private int getOnlineCount() {
        return WindChest.connections.size();
    }

}