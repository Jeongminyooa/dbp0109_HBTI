package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.Todo;
import model.dao.mybatis.mapper.TodoMapper;

public class TodoDAO {
	
private SqlSessionFactory sqlSessionFactory;
	
	// sqlSessionFactory 생성
	public TodoDAO() {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	// 투두 추가
		public int add(String content, int user_id) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				int result = sqlSession.getMapper(TodoMapper.class).addTodo(content, user_id);
				if (result > 0) {
					sqlSession.commit();
				}
				return result;	
			} finally {
				sqlSession.close();
			}
		}
		
		// 투두 내용 수정
		public int update(String content, int todo_id) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				int result = sqlSession.getMapper(TodoMapper.class).updateTodo(content, todo_id);
				if (result > 0) {
					sqlSession.commit();
				}
				return result;	
			} finally {
				sqlSession.close();
			}
		}
		
		// 투두 체크 수정
		public int updateIs_done(int is_done, int todo_id) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				int result = sqlSession.getMapper(TodoMapper.class).updateIs_done(is_done, todo_id);
				if (result > 0) {
					sqlSession.commit();
				}
				return result;	
			} finally {
				sqlSession.close();
			}
		}
		
		// 투두 삭제
		public int delete(int todo_id) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				int result = sqlSession.getMapper(TodoMapper.class).deleteTodo(todo_id);
				if (result > 0) {
					sqlSession.commit();
				}
				return result;	
			} finally {
				sqlSession.close();
			}
		}
		
	// 날짜별 투두 리스트를 반환
	public List<Todo> findDateTodoList(java.sql.Date todo_date, int user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(TodoMapper.class).findDateTodoListByUserId(todo_date, user_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// 오늘 투두 리스트를 반환
	public List<Todo> findDateTodoList(int user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(TodoMapper.class).findTodoListByUserId(user_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// 검색 날짜의 수정할 투두 정보 반환
	public Todo findTodo(java.sql.Date todo_date, int todo_id, int user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(TodoMapper.class).findTodoByDate(todo_date, todo_id, user_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// 오늘 날짜의 수정할 투두 정보 반환
		public Todo findTodo(int todo_id, int user_id) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			try {
				return sqlSession.getMapper(TodoMapper.class).findTodoByTodoID(todo_id, user_id);
			} finally {
				sqlSession.close();
			}
		}
	
	// 미완료 투두 리스트 반환
	public List<Todo> findNotSelectTodoListByDate(java.sql.Date todo_date, int todo_id, int user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(TodoMapper.class).findNotSelectTodoListByDate(todo_date, todo_id, user_id);
		} finally {
			sqlSession.close();
		}
	}
	
	public List<Todo> findNotSelectTodoList(int todo_id, int user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(TodoMapper.class).findNotSelectTodoList(todo_id, user_id);
		} finally {
			sqlSession.close();
		}
	}
	
	
	public int deleteUserAllTodo(int user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(TodoMapper.class).deleteUserAllTodo(user_id);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;	
		} finally {
			sqlSession.close();
		}
	}
	
	public Date findDate(int todo_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(TodoMapper.class).findDateByTodoID(todo_id);
		} finally {
			sqlSession.close();
		}
	}
	
	

}
