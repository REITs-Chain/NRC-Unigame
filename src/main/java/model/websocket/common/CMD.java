package model.websocket.common;

/**
 * Created by 一线生 on 2015/11/22.
 * 说明：
 */
public class CMD {
    /**
     * 创建用户
     */
    public static final String USER_NAME_CREATE = "/USER_NAME_CREATE/";

    public static boolean isCMD(String message) {
        return message.startsWith(USER_NAME_CREATE);
    }
}
