package model.service;

import java.sql.SQLException;
import java.util.List;

import model.ChallengePost;
import model.User;
import model.dao.GroupDAO;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.service.exception.DoNotQuitLeaderException;
import model.service.exception.ExistingChallengePostException;

public class GroupManager {
	private GroupDAO groupDAO;
	private PostDAO postDAO;
	private UserDAO userDAO;
	
	public GroupManager() {
		try {
			groupDAO = new GroupDAO();
			postDAO = new PostDAO();
			userDAO = new UserDAO();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static GroupManager groupManager = new GroupManager();
	
	public static GroupManager getInstance() {
		return groupManager;
	}
	
	public List<User> findUserList(int group_id) throws SQLException{
		return groupDAO.findUserList(group_id);
	}
	
	public List<ChallengePost> findPostList(int group_id) throws SQLException{
		return postDAO.findPostList(group_id);
	}
	
	public String findLeaderName(int group_id) throws SQLException{
		return groupDAO.findLeaderName(group_id);
	}
	
	// user�� ç���� post�� ����Ͽ����� Ȯ��
	public boolean isPost(String user_id) throws SQLException, ExistingChallengePostException {
		if(postDAO.findPost(user_id) != null) {
			throw new ExistingChallengePostException("�̹� ç������ �����ϼ̽��ϴ�. ������ ç������ ������ּ���!");
		}
		return false;
	}
	// user�� ����� post�� ������.
	public ChallengePost findPost (String user_id) throws SQLException{
		return postDAO.findPost(user_id);
	}
	
	public int updatePost(ChallengePost post) {
		return postDAO.updatePost(post);
	}
	public int addPost(ChallengePost post, int group_id) throws SQLException{
		return postDAO.addPost(post, group_id);
	}
	
	// post�� ���ƿ� ��ư
	public int updatePostLike(int post_id) {
		return postDAO.updatePostLike(post_id);
	}
	
	// ������ post ����
	public int deletePost(int post_id) throws SQLException{
		return postDAO.deletePost(post_id);
	}
	
	public int quitMember(int group_id, String quit_id, String user_id) throws DoNotQuitLeaderException {
		if(quit_id.equals(user_id)) {
			throw new DoNotQuitLeaderException("�׷����� ������ �� �����ϴ�.");
		}
		return userDAO.quitGroup(quit_id);
	}
	// �׷� ���� �� �ش� �׷��� post�� ���� �����ȴ�.
	public int deleteAllPost(int group_id) throws SQLException{
		return postDAO.deleteAllPost(group_id);
	}
	
	public int assignChallenge() throws SQLException {
		int cntList = groupDAO.cntOfChallengeList();
		return groupDAO.assignChallenge(cntList);
	}
}
