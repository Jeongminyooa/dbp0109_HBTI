package model.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Todo;


/**
 * ����� ������ ���� �����ͺ��̽� �۾��� �����ϴ� DAO Ŭ����
 * Todo ���̺��� Ŀ�´�Ƽ ������ �߰�, ����, ����, �˻� ���� 
 */
public class TodoDAO {
	private JDBCUtil jdbcUtil = null;
	
	public TodoDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil ��ü ����
	}
	
	
	public Todo create(Todo todo) throws SQLException {
		String sql = "INSERT INTO TODO VALUES (todo_seq.nextval, ?, SYSDATE, ?, 0)";
		Object[] param = new Object[] {todo.getContent(), todo.getUser_id()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil �� insert���� �Ű� ���� ����
						
		String key[] = {"todo_id"};	// PK �÷��� �̸�     
		try {    
			jdbcUtil.executeUpdate(key);  // insert �� ����
		   	ResultSet rs = jdbcUtil.getGeneratedKeys();
		   	if(rs.next()) {
		   		int generatedKey = rs.getInt(1);   // ������ PK ��
		   		todo.setTodo_id(generatedKey); 	// id�ʵ忡 ����  
		   	}
		   	return todo;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return null;			
	}
	
		
	/**
	 * ���� ���̺� ���ο� �� ���� (PK ���� Sequence�� �̿��Ͽ� �ڵ� ����)
	 */
	public int add(Todo todo) throws SQLException {
		String sql = "INSERT INTO TODO VALUES (todo_seq.nextval, ?, SYSDATE, ?, 0)";		
		Object[] param = new Object[] {todo.getContent(), todo.getUser_id()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil �� insert���� �Ű� ���� ����
						
		try {    
			return jdbcUtil.executeUpdate();  // insert �� ����
		   
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;		
	}

	/**
	 * ������ ���� ������ ����
	 */
	public int update(int todo_id, String content) throws SQLException {
		String sql = "UPDATE TODO "
					+ "SET content=? "
					+ "WHERE todo_id=?";
		Object[] param = new Object[] {todo_id};				
		jdbcUtil.setSqlAndParameters(sql, param);
			
		try {				
			int result = jdbcUtil.executeUpdate();	// update �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;
	}

	public int updateIs_done(int todo_id, int is_done) throws SQLException {
		String sql = "UPDATE TODO "
				+ "SET is_done=? "
				+ "WHERE todo_id=?";
	Object[] param = new Object[] {is_done, todo_id};				
	jdbcUtil.setSqlAndParameters(sql, param);
		
	try {				
		int result = jdbcUtil.executeUpdate();	// update �� ����
		return result;
	} catch (Exception ex) {
		jdbcUtil.rollback();
		ex.printStackTrace();
	}
	finally {
		jdbcUtil.commit();
		jdbcUtil.close();	// resource ��ȯ
	}		
	return 0;
}
	/**
	 * �־��� ID�� �ش��ϴ� Ŀ�´�Ƽ ������ ����.
	 */
	public int delete(int todo_id ) throws SQLException {
		String sql = "DELETE FROM TODO WHERE todo_id=?";
		Object[] param = new Object[] {todo_id};		
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil�� delete���� �Ű� ���� ����

		try {				
			int result = jdbcUtil.executeUpdate();	// delete �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;
	}

	/**
	 * ��¥�� ���� ������ �˻��Ͽ� List�� ���� �� ��ȯ
	 */
	public List<Todo> findDateTodoList(Date todo_date) throws SQLException {
		 String sql = "SELECT todo_id, content, todo_date, user_id, is_done "
      		   + "FROM TODO "
      		   + "WHERE TO_CHAR(todo_date)=? "; 
		  jdbcUtil.setSqlAndParameters(sql, new Object[] {todo_date});	// JDBCUtil�� query���� �Ű� ���� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Todo> todoList = new ArrayList<Todo>();	
			while (rs.next()) {
				Todo todo = new Todo(		
						rs.getInt("todo_id"),
						rs.getString("content"),
						todo_date,
						rs.getString("user_id"),
						rs.getInt("is_done"));
				todoList.add(todo);				// List�� Community ��ü ����
			}		
			return todoList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	
	public List<Todo> findTodoList(String user_id) throws SQLException {
        String sql = "SELECT todo_id, content, todo_date, user_id, is_done " 
        		   + "FROM TODO "
        		   + "WHERE todo_date >= TO_DATE(SYSDATE) AND user_id = ? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Todo> todoList = new ArrayList<Todo>();	
			while (rs.next()) {
				Todo todo = new Todo(			
						rs.getInt("todo_id"),
						rs.getString("content"),
						rs.getDate("todo_date"),
						rs.getInt("is_done"));
				todoList.add(todo);			
			}		
			return todoList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	public Todo findTodo() throws SQLException {
        String sql = "SELECT todo_id, content, todo_date, user_id, is_done " 
     		   + "FROM TODO "
        	  + "WHERE TO_CHAR(todo_date)= TO_CHAR(SYSDATE) ";
        		
		jdbcUtil.setSqlAndParameters(sql, null);
		Todo todo = null;
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����	
			if (rs.next()) {
				 todo = new Todo(			
						rs.getInt("todo_id"),
						rs.getString("content"),
						rs.getDate("todo_date"),
						rs.getInt("is_done"));	
			}		
						
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return todo;
	}
	
}
