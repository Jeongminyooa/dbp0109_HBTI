package controller.group;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;
import model.Group;
import model.service.UserManager;

public class ListGroupController implements Controller{

	// private static final int countPerPage = 100;	// �� ȭ�鿡 ����� ����� ��

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
	
    	// �α��� ���� Ȯ�� �߰�
    	UserManager manager = UserManager.getInstance();
		List<Group> groupList = manager.findGroupList(1, 1);
		
		request.setAttribute("groupList", groupList);	
		
    	return "group/list.jsp";
    }
}
