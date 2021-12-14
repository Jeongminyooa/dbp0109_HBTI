package controller.todo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.TodoManager;

public class CheckTodoController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int todo_id = Integer.parseInt(request.getParameter("todo_id"));
		
		TodoManager todoManager = TodoManager.getInstance();
		
		// üũ�� �� ���
		if(request.getServletPath().equals("/todo/doCheck")) {
			todoManager.updateIs_done(todo_id, 0);
		} else {
			todoManager.updateIs_done(todo_id, 1);
		}
		return "redirect:/todo/view";
	}

}