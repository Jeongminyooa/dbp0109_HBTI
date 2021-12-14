package controller.todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Todo;
import model.service.TodoManager;
import controller.Controller;
import controller.user.UserSessionUtils;

public class DeleteTodoController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String user_id =  UserSessionUtils.getLoginUserId(request.getSession());

		TodoManager manager = TodoManager.getInstance();
    	
		 if (request.getServletPath().equals("/todo/delete")) {
			
			int deleteId = Integer.parseInt(request.getParameter("todo_id"));
			manager.delete(deleteId);
		
			int selectId = Integer.parseInt(request.getParameter("select_id"));
			Todo selectTodo = manager.findTodo(selectId, user_id);
			request.setAttribute("selectTodo", selectTodo);


			List<Todo> todoList = manager.findNotSelectTodoList(selectId, user_id);
			request.setAttribute("todoList", todoList);	
			
			request.setAttribute("select_id", selectId);
			
			
			return "/todo/modifyForm.jsp"; 
			
		 }
		 
		 else {
		
				int deleteId = Integer.parseInt(request.getParameter("todo_id"));
				manager.delete(deleteId);
			
				String date = request.getParameter("todo_date");
				int selectId = Integer.parseInt(request.getParameter("select_id"));
				
	     		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
	     		Date todo_date = (Date) sdf.parse(date);
	    
	     		
				Todo selectTodo = manager.findTodo(todo_date, selectId, user_id);
	     		request.setAttribute("selectTodo", selectTodo);
	     		
	 			List<Todo> todoList = manager.findNotSelectTodoList(todo_date, selectId, user_id);
	 			request.setAttribute("todoList", todoList);	
	 			request.setAttribute("date", todo_date);	
	 			
	 			request.setAttribute("select_id", selectId);
		
				return "/todo/modifyDateForm.jsp";  
			 }
		 }
			
			
	}
