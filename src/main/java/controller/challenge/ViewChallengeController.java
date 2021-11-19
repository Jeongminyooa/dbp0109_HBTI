package controller.challenge;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.ChallengePost;
import model.service.GroupManager;

public class ViewChallengeController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		GroupManager groupManager = GroupManager.getInstance();
		
		if(request.getServletPath().equals("/challenge/myView")) {
			String user_id = UserSessionUtils.getLoginUserId(request.getSession());
			
			ChallengePost post = groupManager.findPost(user_id);
			
			request.setAttribute("postInfo", post);
			
			return "/challenge/view.jsp";
		}
		// �ٸ� ����� ç���� �������� �� ��
		String user_id = request.getParameter("writer_id");
		
		ChallengePost post = groupManager.findPost(user_id);
		request.setAttribute("postInfo", post);
		
		return "/challenge/view.jsp";
	}

}
