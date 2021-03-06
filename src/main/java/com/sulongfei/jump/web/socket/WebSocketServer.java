package com.sulongfei.jump.web.socket;

import com.sulongfei.jump.config.WebSocketEncoder;
import com.sulongfei.jump.context.SpringContext;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/12/26 11:43
 * @Version 1.0
 */
@Slf4j
@ServerEndpoint(value = "/{remoteClubId}/{saleId}/{saleType}/socket/{userId}", encoders = WebSocketEncoder.class)
@Component
public class WebSocketServer {
    private static int onlineCount = 0;
    private static final CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private SecurityUserMapper userMapper = SpringContext.getBean(SecurityUserMapper.class);
    private Session session;
    private Long remoteClubId;
    private Long userId;

    @OnOpen
    public void onOpen(
            @PathParam(value = "remoteClubId") Long remoteClubId,
            @PathParam(value = "saleId") Long saleId,
            @PathParam(value = "saleType") Integer saleType,
            @PathParam(value = "userId") Long userId,
            Session session) {
        this.session = session;
        this.remoteClubId = remoteClubId;
        this.userId = userId;
        webSocketSet.add(this);
        addOnlineCount();
        SecurityUser user = userMapper.selectByPrimaryKey(this.userId);
        user.setLastOperationTime(new Timestamp(new Date().getTime()));
        user.setLastOperationClub(this.remoteClubId);
        userMapper.updateByPrimaryKey(user);
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有一链接关闭！当前在线人数" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("来自客户端的消息：" + message);
        SecurityUser user = userMapper.selectByPrimaryKey(this.userId);
        user.setConfirmPush(false);
        userMapper.updateByPrimaryKey(user);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("webSocket发生错误");
    }

    public void sendMessage(Object obj) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(obj);
    }

    public static void sendInfo(Long userId, Object obj) {
        for (WebSocketServer item : webSocketSet) {
            try {
                if (userId == null) {
                    item.sendMessage(obj);
                } else if (item.userId.equals(userId)) {
                    item.sendMessage(obj);
                    break;
                }
            } catch (IOException | EncodeException e) {
                continue;
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
