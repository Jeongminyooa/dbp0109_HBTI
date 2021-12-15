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


public class ModifyTodoController implements Controller {
	

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
 
    	String user_id = UserSessionUtils.getLoginUserId(request.getSession());
    	
    	TodoManager manager = TodoManager.getInstance();
			
    	 if (request.getServletPath().equals("/todo/modify")) {
				int todo_id = Integer.parseInt(request.getParameter("select_id"));
				String content = request.getParameter("content");
				manager.updateTodo(todo_id, content);
				
				return "redirect:/todo/view";	
    	 }
    	 
    	 else if (request.getServletPath().equals("/todo/date/modify")) {
				int todo_id = Integer.parseInt(request.getParameter("select_id"));
				String content = request.getParameter("content");
				manager.updateTodo(todo_id, content);
				
				Date todo_date = manager.findDateByTodoID(todo_id);
	 			List<Todo> todoList = manager.findDateTodoList(todo_date, user_id);
	 			request.setAttribute("todoList", todoList);		
	 			request.setAttribute("date", todo_date);
	 			
				return "/todo/dateResult.jsp";
    	 }
				
 	
    	 else if (request.getServletPath().equals("/todo/modifyForm")){
    	
    		int todo_id = Integer.parseInt(request.getParameter("todo_id"));
    	
    		Todo selectTodo = manager.findTodo(todo_id, user_id);
    		request.setAttribute("selectTodo", selectTodo);
    		
    		int select_id = todo_id;
    		request.setAttribute("select_id", select_id);
    		
			List<Todo> todoList = manager.findNotSelectTodoList(todo_id, user_id);
			request.setAttribute("todoList", todoList);	
   
			return "/todo/modifyForm.jsp";  
    	 }
    	 
    	 else {
    		int todo_id = Integer.parseInt(request.getParameter("todo_id"));
    	    	
     		String date = request.getParameter("todo_date");
     		
     		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
     		Date todo_date = (Date) sdf.parse(date);
    
     		Todo selectTodo = manager.findTodo(todo_date, todo_id, user_id);
     		request.setAttribute("selectTodo", selectTodo);
     		
     		int select_id = todo_id;
    		request.setAttribute("select_id", select_id);
     		
 			List<Todo> todoList = manager.findNotSelectTodoList(todo_date, todo_id, user_id);
 			request.setAttribute("todoList", todoList);
 			request.setAttribute("date", todo_date);	
    
 			return "/todo/modifyDateForm.jsp";  
    		 
    	 }
    	
    	}
    }