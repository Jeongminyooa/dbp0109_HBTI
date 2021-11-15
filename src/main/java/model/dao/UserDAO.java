package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Group;
import model.User;

public class UserDAO {
	private JDBCUtil jdbcUtil = null;
	
	public UserDAO() {
		jdbcUtil = new JDBCUtil(); //JDBCUtil ��ü ����
	}
	
	public int belongToGroup(String user_id) throws SQLException {
		String sql = "SELECT group_id "
				+ "FROM UserInfo "
				+ "WHERE user_id=? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if(rs.next()) {
				int group_id = rs.getInt("group_id");
				return group_id;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return 0; // �׷� ������ ������ 0�� ��ȯ
	}
	
	public int findHBTI(String user_id) throws SQLException {
		String sql = "SELECT hbti_id "
				+ "FROM UserInfo "
				+ "WHERE user_id=? ";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_id});
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if(rs.next()) {
				int hbti_id = rs.getInt("hbti_id");
				return hbti_id;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return 0; //hbti_id�� ���ٸ� 0�� ��ȯ
	}
	
	public User findUser(String user_id) throws SQLException {
	      String sql = "SELECT password, name, descr, image, group_id, hbti_id "
	            + "FROM USERINFO " + "WHERE user_id=?";
	      jdbcUtil.setSqlAndParameters(sql, new Object[] { user_id }); // JDBCUtil   query문과 ׺��  �     ��  

	      try {
	         ResultSet rs = jdbcUtil.executeQuery(); 
	         if (rs.next()) {  
	            User user = new User(   
	                  user_id, rs.getString("password"), rs.getString("name"), rs.getString("descr"), rs.getString("image"), rs.getInt("group_id"), rs.getInt("hbti_id") );
	            return user;
	         }
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      } finally {
	         jdbcUtil.close();  
	      }
	      return null;
	   }
	
}
