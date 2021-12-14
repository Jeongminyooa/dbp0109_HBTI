package model.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Todo;


/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * Todo 테이블에서 커뮤니티 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class TodoDAO {
	private JDBCUtil jdbcUtil = null;
	
	public TodoDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
	
	
	public Todo create(Todo todo) throws SQLException {
		String sql = "INSERT INTO TODO VALUES (todo_seq.nextval, ?, SYSDATE, ?, 0)";
		Object[] param = new Object[] {todo.getContent(), todo.getUser_id()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil 에 insert문과 매개 변수 설정
						
		String key[] = {"todo_id"};	// PK 컬럼의 이름     
		try {    
			jdbcUtil.executeUpdate(key);  // insert 문 실행
		   	ResultSet rs = jdbcUtil.getGeneratedKeys();
		   	if(rs.next()) {
		   		int generatedKey = rs.getInt(1);   // 생성된 PK 값
		   		todo.setTodo_id(generatedKey); 	// id필드에 저장  
		   	}
		   	return todo;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return null;			
	}
	
		
	/**
	 * 투두 테이블에 새로운 행 생성 (PK 값은 Sequence를 이용하여 자동 생성)
	 */
	public int add(Todo todo) throws SQLException {
		String sql = "INSERT INTO TODO VALUES (todo_seq.nextval, ?, SYSDATE, ?, 0)";		
		Object[] param = new Object[] {todo.getContent(), todo.getUser_id()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil 에 insert문과 매개 변수 설정
						
		try {    
			return jdbcUtil.executeUpdate();  // insert 문 실행
		   
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;		
	}

	/**
	 * 기존의 투두 정보를 수정
	 */
	public int update(int todo_id, String content) throws SQLException {
		String sql = "UPDATE TODO "
					+ "SET content=? "
					+ "WHERE todo_id=?";
		Object[] param = new Object[] {content, todo_id};				
		jdbcUtil.setSqlAndParameters(sql, param);
			
		try {				
			int result = jdbcUtil.executeUpdate();	// update 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
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
		int result = jdbcUtil.executeUpdate();	// update 문 실행
		return result;
	} catch (Exception ex) {
		jdbcUtil.rollback();
		ex.printStackTrace();
	}
	finally {
		jdbcUtil.commit();
		jdbcUtil.close();	// resource 반환
	}		
	return 0;
}
	/**
	 * 주어진 ID에 해당하는 커뮤니티 정보를 삭제.
	 */
	public int delete(int todo_id ) throws SQLException {
		String sql = "DELETE FROM TODO WHERE todo_id=?";
		Object[] param = new Object[] {todo_id};		
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil에 delete문과 매개 변수 설정

		try {				
			int result = jdbcUtil.executeUpdate();	// delete 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}

	/**
	 * 날짜별 투두 정보를 검색하여 List에 저장 및 반환
	 */
	public List<Todo> findDateTodoList(java.sql.Date todo_date, String user_id) throws SQLException {
		 String sql = "SELECT todo_id, content, todo_date, user_id, is_done "
      		   + "FROM TODO "
      		   + "WHERE todo_date >= ? AND todo_date < ? + 1 AND user_id = ? "; 
		  jdbcUtil.setSqlAndParameters(sql, new Object[] {todo_date,todo_date, user_id});	// JDBCUtil에 query문과 매개 변수 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
			List<Todo> todoList = new ArrayList<Todo>();	
			while (rs.next()) {
				Todo todo = new Todo(		
						rs.getInt("todo_id"),
						rs.getString("content"),
						todo_date,
						rs.getString("user_id"),
						rs.getInt("is_done"));
				todoList.add(todo);				
			}		
			return todoList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	
	public List<Todo> findTodoList(String user_id) throws SQLException {
        String sql = "SELECT todo_id, content, todo_date, user_id, is_done " 
        		   + "FROM TODO "
        		   + "WHERE todo_date >= TO_DATE(SYSDATE) AND user_id = ? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});		// JDBCUtil에 query문 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
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
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	// 검색 날짜의 수정할 todo의 정보를 가져옴
	public Todo findTodo(java.sql.Date todo_date, int todo_id, String user_id) throws SQLException {
        String sql = "SELECT todo_id, content, todo_date, is_done " 
     		   + "FROM TODO "
        	  + "WHERE todo_date >= ? AND todo_date < ? + 1 AND todo_id=? AND user_id=? ";
        		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {todo_date, todo_date, todo_id, user_id});
		Todo todo = null;
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행	
			if (rs.next()) {
				 todo = new Todo(			
						rs.getInt("todo_id"),
						rs.getString("content"),
						todo_date,
						rs.getInt("is_done"));	
			}		
						
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return todo;
	}
	
	// 수정할 todo의 정보를 가져옴
	public Todo findTodo(int todo_id, String user_id) throws SQLException {
        String sql = "SELECT todo_id, content, todo_date, is_done " 
     		   + "FROM TODO "
        	  + "WHERE todo_date >= TO_DATE(SYSDATE) AND todo_id=? AND user_id=? ";
        		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {todo_id, user_id});
		Todo todo = null;
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행	
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
			jdbcUtil.close();		// resource 반환
		}
		return todo;
	}
	
	public List<Todo> findNotSelectTodoList(java.sql.Date todo_date, int todo_id, String user_id) throws SQLException{
		String sql = "SELECT todo_id, content, todo_date, user_id, is_done " 
     		   + "FROM TODO "
     		   + "WHERE todo_date >= ? AND todo_date < ? + 1 AND user_id = ? AND NOT todo_id =? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {todo_date, todo_date, user_id, todo_id});		// JDBCUtil에 query문 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
			List<Todo> todoList = new ArrayList<Todo>();	
			while (rs.next()) {
				Todo todo = new Todo(			
						rs.getInt("todo_id"),
						rs.getString("content"),
						todo_date,
						rs.getInt("is_done"));
				todoList.add(todo);			
			}		
			return todoList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
		
	public List<Todo> findNotSelectTodoList(int todo_id, String user_id) throws SQLException{
		String sql = "SELECT todo_id, content, todo_date, user_id, is_done " 
     		   + "FROM TODO "
     		   + "WHERE todo_date >= TO_DATE(SYSDATE) AND user_id = ?  AND NOT todo_id =? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id, todo_id});		// JDBCUtil에 query문 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
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
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	public int deleteUserAllTodo(String user_id) {
		String sql = "DELETE FROM Todo "
				+ "WHERE user_id=? ";
			jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});		// JDBCUtil에 query문 설정
						
			try {
				int rlt = jdbcUtil.executeUpdate();			// query 실행				
				return rlt;					
			} catch (Exception ex) {
				jdbcUtil.rollback();
				ex.printStackTrace();
			} finally {
				jdbcUtil.commit();
				jdbcUtil.close(); // resource 반환
			}
			return 0;
	}
	
	public Date findDate(int todo_id) throws SQLException {
		String sql = "SELECT todo_date " 
					+ "FROM TODO " 
					+ "WHERE todo_id=? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { todo_id });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				Date todo_date = rs.getDate("todo_date");
				return todo_date;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

}