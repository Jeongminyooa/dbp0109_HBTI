package controller.user;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
    public static final String USER_SESSION_KEY = "user_id";

    /* ���� �α����� ������� ID�� ���� */
    public static String getLoginUserId(HttpSession session) {
        String user_id = (String)session.getAttribute(USER_SESSION_KEY);
        return user_id;
    }

    /* �α����� ���������� �˻� */
    public static boolean hasLogined(HttpSession session) {
        if (getLoginUserId(session) != null) {
            return true;
        }
        return false;
    }

    /* ���� �α����� ������� ID�� userId���� �˻� */
    public static boolean isLoginUser(String user_id, HttpSession session) {
        String loginUser = getLoginUserId(session);
        if (loginUser == null) {
            return false;
        }
        return loginUser.equals(user_id);
    }
}
