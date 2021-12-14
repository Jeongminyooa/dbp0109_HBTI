package model.dao.mybatis.mapper;

import java.util.List;

import model.ChallengePost;

public interface PostMapper {

	// user_id�� ���� ��¥ post�� ã��
	public ChallengePost selectPostByUserId(String user_id);
	
	// group_id�� ���� ��¥�� post list�� ����.
	public List<ChallengePost> selectPostListByGroupId(int group_id);
	
	// post �߰�
	public int insertPost(ChallengePost post);
	
	// post ����
	public int updatePost(ChallengePost post);
	
	// ���ƿ� ��ư 1 �߰�
	public int updatePostLikeByPostId(int post_id);
	
	// post ����
	public int deletePost(int post_id);
	
	// group_id�� ��� ����Ʈ ���� (�׷� ����)
	public int deleteAllPostByGroupId(int group_id);
	
	// user_id�� ��� ����Ʈ ���� (�׷� Ż��)
	public int deleteAllPostByUserId(String writer_id);
}
