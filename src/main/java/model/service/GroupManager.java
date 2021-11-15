package model.service;

import java.sql.SQLException;
import java.util.List;

import model.ChallengePost;
import model.Group;
import model.User;
import model.dao.GroupDAO;
import model.dao.UserDAO;
import model.dao.PostDAO;

public class GroupManager {
	private GroupDAO groupDAO;
	private UserDAO userDAO;
	private PostDAO postDAO;
	
	public GroupManager() {
		try {
			groupDAO = new GroupDAO();
			userDAO = new UserDAO();
			postDAO = new PostDAO();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static GroupManager groupManager = new GroupManager();
	
	public static GroupManager getInstance() {
		return groupManager;
	}
	
	public List<User> findUserList(int group_id) {
		return groupDAO.findUserList(group_id);
	}
	
	public List<ChallengePost> findPostList(int group_id) {
		return groupDAO.findPostList(group_id);
	}
	
	public String findLeaderName(int group_id) {
		return groupDAO.findLeaderName(group_id);
	}
	
	// user�� ç���� post�� ����Ͽ����� Ȯ��
	public boolean isPost(String user_id) throws ExistingChallengePostException {
		if(groupDAO.findPost(user_id) != null) {
			throw new ExistingChallengePostException("�̹� ç������ �����ϼ̽��ϴ�. ������ ç������ ������ּ���!");
		}
		return false;
	}
	// user�� ����� post�� ������.
	public ChallengePost findPost (String user_id) {
		return groupDAO.findPost(user_id);
	}
	
	public int addPost(ChallengePost post, int group_id) {
		return groupDAO.addPost(post, group_id);
	}
	
	public int quitMember(int group_id, String quit_id, String user_id) throws DoNotQuitLeaderException {
		if(quit_id.equals(user_id)) {
			throw new DoNotQuitLeaderException("�׷����� ������ �� �����ϴ�.");
		}
		return groupDAO.quitMember(quit_id);
	}
}
