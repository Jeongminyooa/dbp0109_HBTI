package model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Group;
import model.User;
import model.dao.mybatis.GroupDAO;
import model.dao.HBTIDAO;
import model.dao.mybatis.PostDAO;
import model.dao.TodoDAO;
import model.dao.UserDAO;
import model.service.exception.ExistingGroupException;
import model.service.exception.ExistingUserException;
import model.service.exception.OverTheLimitException;
import model.service.exception.PasswordMismatchException;
import model.service.exception.UserHbtiException;
import model.service.exception.UserNotFoundException;

public class UserManager {

	private GroupDAO groupDAO;
	private UserDAO userDAO;
	private HBTIDAO hbtiDAO;
	private PostDAO postDAO;
	private TodoDAO todoDAO;
	private UserHBTIMatching matchRlt;
	
	public UserManager() {
		try {
			userDAO = new UserDAO();
			groupDAO = new GroupDAO();
			hbtiDAO = new HBTIDAO();
			postDAO = new PostDAO();
			todoDAO = new TodoDAO();
			matchRlt = new UserHBTIMatching(userDAO);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	private static UserManager userManager = new UserManager();
	
	public static UserManager getInstance() {
		return userManager;
	}
	public UserDAO getUserDAO() {
		return this.userDAO;
	}
	
	//���̵�� �н������ �α���
	public boolean login(String user_id, String password)
			throws SQLException, UserNotFoundException, PasswordMismatchException {
		User user = findUser(user_id);

		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
		return true;
	}
	
	public int updateLoginDate(String user_id) throws SQLException {
		return userDAO.updateLoginDate(user_id);
	}
	
	// user ����
	public int create(User user) throws SQLException, ExistingUserException {
		if (userDAO.existingUser(user.getUser_id()) == true) {
			throw new ExistingUserException(user.getUser_id() + "�� �����ϴ� ���̵��Դϴ�.");
		}
		
		return userDAO.create(user);
	}

	// user ���� ����
	public int update(User user) throws SQLException, UserNotFoundException {
		return userDAO.update(user);
	}

	// user ����
	public int remove(String user_id) throws SQLException, UserNotFoundException {
		int group_id = groupDAO.findGroupUserId(user_id);
		if(group_id != 0) {
			quitGroup(user_id, group_id);
		}
		todoDAO.deleteUserAllTodo(user_id);
		return userDAO.remove(user_id);
	}
	
	// �׷� Ż�� + user_id�� ��� post ����
	public int quitGroup(String user_id, int group_id) throws SQLException {
		Group g = groupDAO.findLeader(group_id);
		if(user_id.equals(g.getLeader_id())) { //leader���
			String newLeader_id = groupDAO.findNextLeader(user_id, group_id);//���� ���� ã��
			
			if(newLeader_id != null) {
				groupDAO.updateLeader(newLeader_id, group_id);
				postDAO.deleteUserAllPost(user_id);
			}
			else { // �׷쿡 ���� ����� ���ٸ� �׷��� ����
				postDAO.deleteAllPost(group_id);
				return deleteGroup(group_id);
			}
		}
		
		else
			postDAO.deleteUserAllPost(user_id);
		
		return userDAO.quitGroup(user_id);
	}
	
	//HBTI�� ������Ʈ�Ǹ� �׷� ������ �ʱ�ȭ
	public void updateHBTIGroup(String user_id, int oldHbti, int group_id) throws SQLException, UserHbtiException{	
		int nowHbti = findHBTI(user_id);
		if(oldHbti != nowHbti) { //hbti�� �ٲ���ٸ�
			quitGroup(user_id, group_id);
		}
		// �ٲ��� �ʾҴٸ� �ƹ��͵� ���ص� �ȴ�.
	}
	
	// user_id�� ������ ��ȯ
	public User findUser(String user_id) throws SQLException, UserNotFoundException {
		User user = userDAO.findUser(user_id);

		if (user == null) {
			throw new UserNotFoundException(user_id + "�� �������� �ʴ� ���̵��Դϴ�.");
		}
		return user;
	}
	
	
	//user�� hbti ID�� ��ȯ (HBTI �׽�Ʈ�� ���ߴٸ� 0�� ��ȯ)
	public int findHBTI(String user_id) throws SQLException, UserHbtiException{
		int hbti_id = userDAO.findHBTI(user_id);

		return hbti_id;
	}
	
	//hbti�� �̸��� ��ȯ
		public String findHbtiName(int hbti_id) throws SQLException, UserNotFoundException {
			String name = hbtiDAO.findHbtiName(hbti_id);
			
			return name;
		}
		
		// group_id�� �׷� �̸� ��ȯ
		public String findGroupName(int group_id) throws SQLException, UserNotFoundException {
			return groupDAO.findGroupName(group_id);
		}
		
		// ���� ������� �׷��� ���� ��ȯ **hbti_id�� ���� ���� �� �ְ� ���� �ʿ�**
		public int findGroupCnt(int hbti_id) throws SQLException {
			return groupDAO.findGroupCnt(hbti_id);
		}
		
		// �׽�Ʈ ����� ���� HBTI ��Ī
		public int updateHBTI(String user_id, String[] testRst) throws SQLException, UserNotFoundException {
			return matchRlt.matchingHBTIResult(user_id, testRst);
		}
		
	// �׷쿡 �ҼӵǾ������� Ȯ��
	public int belongToGroup(String user_id) throws SQLException{
		int group_id = userDAO.belongToGroup(user_id);
		
		return group_id;
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
	
	// �׷� ������ ����
	public Group findGroup(int group_id) throws SQLException {
		// �׷� �⺻ ����
		Group group = groupDAO.findGroup(group_id);
		
		// �׷� �ο� ����
		int numOfMem = groupDAO.findNumberOfMember(group_id);
		
		group.setNumberOfMem(numOfMem);
		
		return group;
	}
	
	// �׷� �˻�
	public List<Group> searchGroupList(int hbti_id, String keyword) throws SQLException {
		List<Group> searcgGroupList = groupDAO.searchGroupList(hbti_id, keyword);
		// ���� �׷� ����Ʈ�� ��� �ο��� �߰�
		for(int i = 0; i < searcgGroupList.size(); i++) {
			Group group = searcgGroupList.get(i);
			int numOfMem = groupDAO.findNumberOfMember(group.getGroup_id());
			group.setNumberOfMem(numOfMem);
		}
		return searcgGroupList;
	}
	
	// �׷� ����
	public int joinGroup(int group_id, String user_id) throws SQLException, OverTheLimitException {
		Group g = groupDAO.findGroup(group_id);
		int num = groupDAO.findNumberOfMember(group_id);
		
		if(g.getLimitation() == num) {
			throw new OverTheLimitException("�׷� ������ �ʰ��Ǿ����ϴ�.");
		}
		return userDAO.updateUserGroupInfo(group_id, user_id);
	}
	
	//�׷� ����
	public int createGroup(Group group) throws SQLException, OverTheLimitException, ExistingGroupException {
		 if(group.getLimitation() > 30) {
			throw new OverTheLimitException("�׷� ������ 30���� �ʰ��� �� �����ϴ�.");
		} else if(group.getLimitation() < 2) {
			throw new OverTheLimitException("�׷� ������ ��� 2�� �̻��̾�� �մϴ�.");
		}
		 
		 boolean exist = groupDAO.existingGroupName(group.getName());
			if(exist) {
				throw new ExistingGroupException("�̹� �����ϴ� �׷� �̸��Դϴ�.");
			}
			
		groupDAO.create(group);
		int group_id = groupDAO.findGroupId(group.getName());
		return userDAO.updateUserGroupInfo(group_id, group.getLeader_id());
	}
	
	// �׷� ���� ����
	public int updateGroup(Group group) throws OverTheLimitException, ExistingGroupException {		
		int numOfMem = groupDAO.findNumberOfMember(group.getGroup_id());
		
		if(numOfMem > group.getLimitation()) {
			throw new OverTheLimitException("�׷� ������ �ø�����.");
		} else if(group.getLimitation() > 30) {
			throw new OverTheLimitException("�׷� ������ 30���� �ʰ��� �� �����ϴ�.");
		} else if(group.getLimitation() < 2) {
			throw new OverTheLimitException("�׷� ������ ��� 2�� �̻��̾�� �մϴ�.");
		}
		
		boolean exist = groupDAO.existingGroupName(group.getName());
		if(exist) {
			throw new ExistingGroupException("�̹� �����ϴ� �׷� �̸��Դϴ�.");
		}
		return groupDAO.update(group);	
	}
	
	// �׷� ����
	public int deleteGroup(int group_id) throws SQLException{
		userDAO.deleteGroup(group_id);
		return groupDAO.delete(group_id);
	}
	
	// user_id�� todo ������ �޾ƿ� ** �������� : ��¥ �Ķ���͸� �޾� �ش��ϴ� ���� ���ڵ常 select **
	public List<String> isTodo(String user_id) throws SQLException {
		return userDAO.isTodo(user_id);
	}
	
	public List<String> isChallenged(String user_id) throws SQLException{
		return userDAO.isChallenged(user_id);
	}
	
	/* main���� ��� */
	/* hbti_id�� �ش��ϴ� �׷��� �� �ο� �� */
	public int numOfGroupMem(int hbti_id) {
		// �ϴ� hbti_id�� group�� ���� �ִ��� group_id ã�ƿ���
		List<Integer> groupList = new ArrayList<>();
		groupList = userDAO.group_idByHBTI(hbti_id);//groupDAO�� �ٲٱ�

		// groupList�� List�ϳ� �� �������� �� group�� �ο� �� ���ͼ� �� �ο� �� ���ϱ�
		int sum = 0;
		for (int i = 0; i < groupList.size(); i++) {
			int group_id = groupList.get(i);
			sum += userDAO.getNumberOfUsersInGroup(group_id);//groupDAO�� �ٲٱ�
		}

		return sum;
	}

	public int numOfUserWhoDidChallengeInGroup(int hbti_id) {
		// �ϴ� hbti_id�� group�� ���� �ִ��� group_id ã�ƿ���
		List<Integer> groupList = new ArrayList<>();
		groupList = userDAO.group_idByHBTI(hbti_id);

		// groupList�� List�ϳ� �� �������� �� group�� User_id �������� List��
		List<String> userList = new ArrayList<>();
		List<String> userListEachGroup = new ArrayList<>();
		for (int i = 0; i < groupList.size(); i++) {
			int group_id = groupList.get(i);

			userListEachGroup = userDAO.userListEachGroup(group_id); // �׷쿡 �ִ� user����Ʈ �ҷ�����

			/*
			 * for (int k = 0; k < userListEachGroup.size(); k++) {
			 * System.out.println("userListEachGroup : " + userListEachGroup.get(k)); }
			 */
			for (int j = 0; j < userListEachGroup.size(); j++) {
				userList.add(userListEachGroup.get(j)); // �׷츶�� ��������
			} // ������� �ϸ� �� hbti�� ���� ��� user_id list
		}
		for (int i = 0; i < userList.size(); i++) {
			System.out.println("user_id : " + userList.get(i));
		}
		int cnt = 0;
		for (int i = 0; i < userList.size(); i++) {
			if (userDAO.didChallengeUser(userList.get(i)))
				cnt++;
		}
		return cnt;
	}

	// ��ŷ ���ϱ�
	public int ranking(int hbti_id) {
		return numOfUserWhoDidChallengeInGroup(hbti_id);

	}

	// �ۼ�Ʈ ���ϱ�
	public double percentOfChallenge(int hbti_id) {
		double A = (double) numOfUserWhoDidChallengeInGroup(hbti_id);
		double B = (double) numOfGroupMem(hbti_id);
		double percentage = 0;
		if (A == 0 || B == 0) {
			percentage = 0;
		} else
			percentage = A / B * 100.0;

		return percentage;
	}
}
