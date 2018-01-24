package socket1;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/chat")
public class ChatAn {
    private static final ArrayList<ChatAn> connections = new ArrayList<>();
    private Session session;
    private String userName;//  web页面的连接的用户名是固定的（约定叫做web，也可以在web端自定义） 根据这个把java-client的消息转发到web-client

    public ChatAn() {
    }

    @OnOpen
    public void connect(Session session) {
        this.session = session;
        connections.add(this);
    }

    @OnMessage
    public void receiveMessage(String message) throws IOException {
        ChatAn chatAn = new ChatAn();
        System.out.println(message);
        if (message.startsWith("webinit")) {
            //web-client建立连接的时候自动发的第一天消息 用来命名连接userName  message格式为 webinit-web
            this.userName = message.split("-")[1];
            broadcast(this, "hello");
        } else if (message.startsWith("javaclient")) {
            //接收来自java-client的消息 每个连接无需命名username connectionID
            for (int i = 0; i < connections.size(); i++) {
                System.out.println("当前的name:" + connections.get(i).userName);
                if (connections.get(i).userName.equals("web")) {//
                    chatAn = connections.get(i);
                    break;
                }
            }
            broadcast(chatAn, message.substring("javaclient".length(), message.length()));
        }
    }

    /**
     * 把java-client的消息转广播至web-client
     *
     * @param chatAn  web连接
     * @param message
     */
    public void broadcast(ChatAn chatAn, String message) throws IOException {
        System.out.println("要发出去的消息：" + message);
        chatAn.session.getBasicRemote().sendText("from java client : " + message);
    }
}
