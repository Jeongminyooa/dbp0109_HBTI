package controller.todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.Todo;
import model.service.TodoManager;


public class ViewTodoController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {			
    	
    	String user_id = UserSessionUtils.getLoginUserId(request.getSession());
    	
		TodoManager manager = TodoManager.getInstance();
		
		if(request.getServletPath().equals("/todo/view")) {
			List<Todo> todoList = manager.findTodoList(user_id);				
		
			request.setAttribute("todoList", todoList);

			return "/todo/main.jsp";	
		}
		
		else if(request.getServletPath().equals("/todo/date")){
			
		String searchDate = request.getParameter("todo_date");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		Date date = (Date) sdf.parse(searchDate);
		
		System.out.println(date);
		
		List<Todo> todo = manager.findDateTodoList(date, user_id);
		request.setAttribute("todoList", todo);
		request.setAttribute("date", date);
		
		return "/todo/dateResult.jsp";
		}
		
		else {
			String date = request.getParameter("date");
			
			SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			Date data = recvSimpleFormat.parse(date);

			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			
			String d = sdf.format(data);
			
			Date todo_date = (Date) sdf.parse(d);
		
			List<Todo> todo = manager.findDateTodoList(todo_date , user_id);
			request.setAttribute("todoList", todo);
			request.setAttribute("date", todo_date);
			
			return "/todo/dateResult.jsp";
			
		}
		
    }
}