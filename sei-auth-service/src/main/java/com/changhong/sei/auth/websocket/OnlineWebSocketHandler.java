package com.changhong.sei.auth.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-06-22 15:45
 */
public class OnlineWebSocketHandler extends TextWebSocketHandler {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, WebSocketSession> sessionPools = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        System.out.println("获取到消息 >> " + message.getPayload());
        session.sendMessage(new TextMessage(String.format("收到用户：【%s】发来的【%s】",
                session.getAttributes().get("uid"), message.getPayload())));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws
            Exception {
        System.out.println("获取到拦截器中用户ID : " + session.getAttributes().get("uid"));
        String uid = session.getAttributes().get("uid").toString();
        //TODO: 重复链接没有进行处理
        sessionPools.put(uid, session);
        addOnlineCount();
        System.out.println(uid + "加入webSocket！当前人数为" + onlineNum);
        session.sendMessage(new TextMessage("欢迎连接到ws服务! 当前人数为：" + onlineNum));
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        System.out.println("断开连接!");
        String uid = session.getAttributes().get("uid").toString();
        sessionPools.remove(uid);
        subOnlineCount();
    }

    /**
     * 添加链接人数
     */
    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    /**
     * 移除链接人数
     */
    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
}
