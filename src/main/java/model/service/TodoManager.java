package model.service;

import java.sql.SQLException;
import java.util.List;
import model.Todo;
import model.dao.mybatis.TodoDAO;
import model.service.exception.TodoNotFoundException;

public class TodoManager {
	
	
	private static TodoManager dbp_HBTI = new TodoManager();
	private TodoDAO todoDAO;


		private TodoManager() {
			try {
				todoDAO = new TodoDAO();
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		public static TodoManager getInstance() {
			return dbp_HBTI;
		}
		
		public int addTodo(Todo todo) throws SQLException {
			return todoDAO.addTodo(todo);
		}

		public int updateTodo(int todo_id, String content) throws SQLException {
			return todoDAO.updateTodo(content, todo_id);
		}	

		public int updateIs_done(int todo_id, int is_done) throws SQLException {
			return todoDAO.updateIs_done(todo_id, is_done);
		}
		public int delete(int todo_id) throws SQLException, TodoNotFoundException {
			return todoDAO.delete(todo_id);
		}
		
		public List<Todo> findDateTodoList(java.util.Date date, String user_id) throws SQLException {
			java.sql.Date date1 = new java.sql.Date(date.getTime());
			
			return todoDAO.findDateTodoList(date1, user_id);
		}
		
		public List<Todo> findTodoListByUserId(String user_id) throws SQLException {
			return todoDAO.findTodoListByUserId(user_id);
		}
		public Todo findTodo(java.util.Date date, int todo_id, String user_id) throws SQLException {
			java.sql.Date date1 = new java.sql.Date(date.getTime());
			return todoDAO.findTodo(date1, todo_id, user_id);
		}
		
		public Todo findTodo(int todo_id, String user_id) throws SQLException {
			return todoDAO.findTodo(todo_id, user_id);
		}

		public List<Todo> findNotSelectTodoList(java.util.Date date, int todo_id, String user_id) throws SQLException {
			java.sql.Date date1 = new java.sql.Date(date.getTime());
			return todoDAO.findNotSelectTodoListByDate(date1, todo_id, user_id);
		}
		
		public List<Todo> findNotSelectTodoList(int todo_id, String user_id) throws SQLException {
			return todoDAO.findNotSelectTodoList(todo_id, user_id);
		}
		
		public TodoDAO getTodoDAO() {
			return this.todoDAO;
		}

		public java.sql.Date findDateByTodoID(int todo_id) throws SQLException {
			return todoDAO.findDate(todo_id);
		}
}