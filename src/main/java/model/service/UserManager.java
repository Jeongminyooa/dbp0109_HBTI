package model.service;

import java.sql.SQLException;
import java.util.List;

import model.Group;
import model.User;
import model.dao.GroupDAO;
import model.dao.UserDAO;

public class UserManager {

	private GroupDAO groupDAO;
	private UserDAO userDAO;
	
	public UserManager() {
		try {
			userDAO = new UserDAO();
			groupDAO = new GroupDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	private static UserManager userManager = new UserManager();
	
	public static UserManager getInstance() {
		return userManager;
	}
	
	public int isMatchWriter(String user_id, String writer_id) throws WriterMismatchException{
		if(!user_id.contentEquals(writer_id)) {
			throw new WriterMismatchException("�Խù� �ۼ��ڸ� ������ �����մϴ�.");
		}
		return 1;
	}
	
	// �׷쿡 �ҼӵǾ������� Ȯ��
	public int belongToGroup(String user_id) throws SQLException{
		int group_id = userDAO.belongToGroup(user_id);
		
		return group_id;
	}
	
	//user�� hbti ID�� ��ȯ
	public int findHBTI(String user_id) throws SQLException{
		int hbti_id = userDAO.findHBTI(user_id);
		return hbti_id;
	}
	
	// hbti�� ���� �׷� ����Ʈ�� ��ȯ
	public List<Group> findGroupList(int user_HBTI) throws SQLException {
		List<Group> groupList = groupDAO.findGroupList(user_HBTI);
		
		// ���� �׷� ����Ʈ�� ��� �ο��� �߰�
		for(int i = 0; i < groupList.size(); i++) {
			Group group = groupList.get(i);
			int numOfMem = groupDAO.findNumberOfMember(group.getGroup_id());
			group.setNumberOfMem(numOfMem);
		}
		return groupList;		
	}
	
	public Group findGroup(int group_id) throws SQLException {
		// �׷� �⺻ ����
		Group group = groupDAO.findGroup(group_id);
		
		// �׷� �ο� ����
		int numOfMem = groupDAO.findNumberOfMember(group.getGroup_id());
		group.setNumberOfMem(numOfMem);
		
		return group;
	}
	
	public List<Group> searchGroupList(int hbti_id, String keyword) throws SQLException {
		return groupDAO.searchGroupList(hbti_id, keyword);
	}
	
	public int joinGroup(int group_id, String user_id) throws OverTheLimitException {
		Group g = groupDAO.findGroup(group_id);
		int num = groupDAO.findNumberOfMember(group_id);
		
		if(g.getLimitation() == num) {
			throw new OverTheLimitException("�׷� ������ �ʰ��Ǿ����ϴ�.");
		}
		return groupDAO.updateMember(group_id, user_id);
	}
	
	public int createGroup(Group group) throws OverTheLimitException {
		int num = groupDAO.findNumberOfMember(group.getGroup_id());
		
		if(num > group.getLimitation()) {
			throw new OverTheLimitException("�׷� ������ �ø�����.");
		} else if(group.getLimitation() > 30) {
			throw new OverTheLimitException("�׷� ������ 30���� �ʰ��� �� �����ϴ�.");
		} else if(group.getLimitation() < 2) {
			throw new OverTheLimitException("�׷� ������ ��� 2�� �̻��̾�� �մϴ�.");
		}
		groupDAO.create(group);
		int group_id = groupDAO.findGroupId(group.getName());
		return groupDAO.updateMember(group_id, group.getLeader_id());
	}
	
	public User findUser(String user_id) throws SQLException {
		return userDAO.findUser(user_id);
	}
	
	public int updateGroup(Group group) throws OverTheLimitException {		
		int num = groupDAO.findNumberOfMember(group.getGroup_id());
		
		if(num > group.getLimitation()) {
			throw new OverTheLimitException("�׷� ������ �ø�����.");
		} else if(group.getLimitation() > 30) {
			throw new OverTheLimitException("�׷� ������ 30���� �ʰ��� �� �����ϴ�.");
		} else if(group.getLimitation() < 2) {
			throw new OverTheLimitException("�׷� ������ ��� 2�� �̻��̾�� �մϴ�.");
		}
		return groupDAO.update(group);	
	}
	
	public int deleteGroup(int group_id, List<User> userList) {
		for(int i = 0; i < userList.size(); i++) {
			groupDAO.quitMember(userList.get(i).getUser_id());
		}
		return groupDAO.delete(group_id);
	}
}
