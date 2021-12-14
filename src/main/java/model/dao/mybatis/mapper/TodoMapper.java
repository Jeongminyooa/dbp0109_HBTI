package model.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import model.Todo;


public interface TodoMapper {
   
   public int addTodo(@Param("content") String content,
         @Param("user_id") int user_id);
   
   public int updateTodo(@Param("content") String content,
         @Param("todo_id") int todo_id);
   
   public int updateIs_done(@Param("is_done") int is_done,
         @Param("todo_id") int todo_id);
   
   public int deleteTodo(int todo_id);
   
   public List<Todo> findDateTodoListByUserId(@Param("todo_date") java.sql.Date todo_date,
         @Param("user_id") int user_id);

   public List<Todo> findTodoListByUserId(int user_id);
   
   public Todo findTodoByDate(@Param("todo_date") java.sql.Date todo_date,
         @Param("todo_id") int todo_id,
         @Param("user_id") int user_id);
   
   public Todo findTodoByTodoID(
         @Param("todo_id") int todo_id,
         @Param("user_id") int user_id);
   
   public List<Todo> findNotSelectTodoListByDate(@Param("todo_date") java.sql.Date todo_date,
         @Param("todo_id") int todo_id,
         @Param("user_id") int user_id);
   
   public List<Todo> findNotSelectTodoList(
         @Param("todo_id") int todo_id,
         @Param("user_id") int user_id);
   
   public int deleteUserAllTodo(int user_id);
   
   public java.sql.Date findDateByTodoID(int todo_id);


}