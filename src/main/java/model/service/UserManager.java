package model.service;

import java.sql.SQLException;
import java.util.List;

import model.Group;
import model.User;
import model.dao.GroupDAO;
import model.dao.HBTIDAO;
import model.dao.PostDAO;
import model.dao.UserDAO;

public class UserManager {

	private GroupDAO groupDAO;
	private UserDAO userDAO;
	private HBTIDAO hbtiDAO;
	private PostDAO postDAO;
	private UserHBTIMatching matchRlt;
	
	public UserManager() {
		try {
			userDAO = new UserDAO();
			groupDAO = new GroupDAO();
			hbtiDAO = new HBTIDAO();
			postDAO = new PostDAO();
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
		return userDAO.remove(user_id);
	}
	
	// �׷� Ż�� + user_id�� ��� post ����
	public int quitGroup(String user_id, int group_id) throws SQLException {
		if(user_id.equals(groupDAO.findLeaderId(group_id))) { //leader���
			String newLeader_id = groupDAO.findNextLeader(user_id, group_id);//���� ���� ã��
			
			if(newLeader_id != null) {
				groupDAO.updateLeader(newLeader_id, group_id);
			}
			else { // �׷쿡 ���� ����� ���ٸ� �׷��� ����
				postDAO.deleteAllPost(group_id);
				return deleteGroup(group_id);
			}
		}
		postDAO.deleteUserAllPost(user_id);
		return userDAO.quitGroup(user_id);
	}
	
	//HBTI�� ������Ʈ�Ǹ� �׷� ������ �ʱ�ȭ
	public void updateHBTIGroup(String user_id, int oldHbti, int group_id) throws SQLException, UserHbtiException{	
		int nowHbti = findHBTI(user_id);
		if(oldHbti != nowHbti) { //hbti�� �ٲ���ٸ�
			//�׷��� �ִ� �� ���� �� �Ǵ�
			if(group_id != 0) { //�׷��� ������ leader���� �ƴ��� Ȯ��
				if(user_id.equals(groupDAO.findLeaderId(group_id))) { //leader���
					String nextLeader = groupDAO.findNextLeader(user_id, group_id);//���� ���� ã��
					groupDAO.updateLeader(nextLeader, group_id);
				}
				//�׷� Ż��
				userDAO.quitGroup(user_id);
			}
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
		
		if (hbti_id == 0) {
			throw new UserHbtiException(user_id + "�� HBTI�� �������� �ʽ��ϴ�.");
		}
		return hbti_id;
	}
	
	//hbti�� �̸��� ��ȯ
		public String findHbtiName(int hbti_id) throws SQLException, UserNotFoundException {
			String name = hbtiDAO.findHbtiName(hbti_id);

			if (name == null) {
				throw new UserNotFoundException(hbti_id + "�� �������� �ʽ��ϴ�.");
			}
			return name;
		}
		
		// group_id�� �׷� �̸� ��ȯ
		public String findGroupName(int group_id) throws SQLException, UserNotFoundException {
			return groupDAO.findGroupName(group_id);
		}
		
		// ���� ������� �׷��� ���� ��ȯ **hbti_id�� ���� ���� �� �ְ� ���� �ʿ�**
		public int findGroupCnt() throws SQLException {
			return groupDAO.findGroupCnt();
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
		
		System.out.println(group.getName() + "hellos");
		
		// �׷� �ο� ����
		int numOfMem = groupDAO.findNumberOfMember(group_id);
		
		group.setNumberOfMem(numOfMem);
		
		return group;
	}
	
	// �׷� �˻�
	public List<Group> searchGroupList(int hbti_id, String keyword) throws SQLException {
		return groupDAO.searchGroupList(hbti_id, keyword);
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
	public int createGroup(Group group) throws SQLException, OverTheLimitException {
		 if(group.getLimitation() > 30) {
			throw new OverTheLimitException("�׷� ������ 30���� �ʰ��� �� �����ϴ�.");
		} else if(group.getLimitation() < 2) {
			throw new OverTheLimitException("�׷� ������ ��� 2�� �̻��̾�� �մϴ�.");
		}
		groupDAO.create(group);
		int group_id = groupDAO.findGroupId(group.getName());
		return userDAO.updateUserGroupInfo(group_id, group.getLeader_id());
	}
	
	// �׷� ���� ����
	public int updateGroup(Group group) throws SQLException, OverTheLimitException {		
		int numOfMem = groupDAO.findNumberOfMember(group.getGroup_id());
		
		if(numOfMem > group.getLimitation()) {
			throw new OverTheLimitException("�׷� ������ �ø�����.");
		} else if(group.getLimitation() > 30) {
			throw new OverTheLimitException("�׷� ������ 30���� �ʰ��� �� �����ϴ�.");
		} else if(group.getLimitation() < 2) {
			throw new OverTheLimitException("�׷� ������ ��� 2�� �̻��̾�� �մϴ�.");
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
}
