package model.websocket.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

/**
 * Created by 一线生 on 2015/11/22.
 * 说明：
 */
public class Message {

    private static Logger LOG = LoggerFactory.getLogger(Message.class);

    /**
     * 给除自己外的所有用户发送消息
     * @param message
     * @param filterSession
     */
    public void send(String message, Session filterSession) {
    	
        //给所有用户发送消息
        Set<String> keys = UserPool.getUserPool().keySet();
        for(String key : keys) {
            Session session = (Session) UserPool.get(key);
            //排除自己
            if(session.equals(filterSession)) {
                continue;
            }
            try {
                //发送消息
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOG.error("给用户 [" + session.getId() + "] 发送消息失败", e);
            }
        }
    }
}
