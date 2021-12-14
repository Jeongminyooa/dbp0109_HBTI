package model.service;

import java.sql.SQLException;
import java.util.List;
import model.Todo;
import model.dao.TodoDAO;
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
		
		public Todo create(Todo todo) throws SQLException {
			return todoDAO.create(todo);
		}

		
		public int addTodo(Todo todo) throws SQLException {
			return todoDAO.add(todo);
		}

		public int update(int todo_id, String content) throws SQLException {
			return todoDAO.update(todo_id, content);
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
		
		public List<Todo> findTodoList(String user_id) throws SQLException {
			return todoDAO.findTodoList(user_id);
		}
		public Todo findTodo(java.util.Date date, int todo_id, String user_id) throws SQLException {
			java.sql.Date date1 = new java.sql.Date(date.getTime());
			return todoDAO.findTodo(date1, todo_id, user_id);
		}
		
		public Todo findTodo(int todo_id, String user_id) throws SQLException {
			return todoDAO.findTodo(todo_id, user_id);
		}
		// ���� ���θ� ������ ���θ���Ʈ
		public List<Todo> findNotSelectTodoList(java.util.Date date, int todo_id, String user_id) throws SQLException {
			java.sql.Date date1 = new java.sql.Date(date.getTime());
			return todoDAO.findNotSelectTodoList(date1, todo_id, user_id);
		}
		
		public List<Todo> findNotSelectTodoList(int todo_id, String user_id) throws SQLException {
			return todoDAO.findNotSelectTodoList(todo_id, user_id);
		}
		
		public TodoDAO getTodoDAO() {
			return this.todoDAO;
		}

		public java.sql.Date findDate(int todo_id) throws SQLException {
			return todoDAO.findDate(todo_id);
		}
}