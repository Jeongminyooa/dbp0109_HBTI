package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.User;
import model.service.PasswordMismatchException;
import model.service.UserHbtiException;
import model.service.UserManager;
import model.service.UserNotFoundException;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");
		
		try {
			// �𵨿� �α��� ó���� ����
			UserManager manager = UserManager.getInstance();
			manager.login(user_id, password);
			int hbti_id = manager.findHBTI(user_id);
			if(hbti_id == 0) {
				throw new UserHbtiException(user_id +"�� HBTI�� �������� �ʽ��ϴ�.");
			}
			manager.updateLoginDate(user_id);
	
			// ���ǿ� ����� ���̵� ����
			HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user_id);
            
            return "redirect:/group/main";			
		} catch (UserNotFoundException e) {
			/* UserNotFoundException�̳� PasswordMismatchException �߻� ��
			 * �ٽ� login form�� ����ڿ��� �����ϰ� ���� �޼����� ���
			 */
            request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
			
            return "/user/loginForm.jsp";			
		}	
		catch (PasswordMismatchException e) {
			/* UserNotFoundException�̳� PasswordMismatchException �߻� ��
			 * �ٽ� login form�� ����ڿ��� �����ϰ� ���� �޼����� ���
			 */
            request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
			
            return "/user/loginForm.jsp";			
		}	
		catch(UserHbtiException e) {
			HttpSession session = request.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user_id);
			return "redirect:/user/hbtiTest";		
		}
    }
}
