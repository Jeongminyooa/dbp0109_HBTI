package model.dao;

import model.Group;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class GroupDAO {

	private JDBCUtil jdbcUtil = null;
	
	public GroupDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil ��ü ����
	}
	
	public List<Group> findGroupList(int user_hbti, int currentPage) {
		 String sql = "SELECT name, descr, icon "
     			+ "FROM Group "
     			+ "WHERE hbti_id=? ";  
		 jdbcUtil.setSqlAndParameters(sql, new Object[] {user_hbti});	// JDBCUtil�� query���� �Ű� ���� ����
		 List<Group> groupList = new ArrayList<Group>();
		 try {
				ResultSet rs = jdbcUtil.executeQuery();		// query ����
				while(rs.next()) {
					Group g = new Group(
							rs.getInt("group_id"),
							rs.getString("name"),
							rs.getString("descr"),
							rs.getString("icon"));
					groupList.add(g);
				}
				return groupList;
		 } catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource ��ȯ
			}
			return null;
	}
}
