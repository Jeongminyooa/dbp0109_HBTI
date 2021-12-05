package model.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import model.Group;
import model.User;

public interface GroupMapper {
	// hbti_id�� �׷� ����Ʈ�� ����
	public List<Group> selectGroupListByGroupId(int hbti_id);
	
	// Ű���忡 �´� �׷� ����Ʈ�� ��ȯ
	public List<Group> selectGroupListByKeyword(@Param("hbti_id") int hbti_id,
										@Param("keyword") String keyword);
	// group_id�� �׷�� ����Ʈ�� ����
	public List<User> selectUserListByGroupId(int group_id);
	
	//group_id�� �׷� ���� ��ȯ
	public Group selectGroupByGroupId(int group_id);
	
	//user_id�� group_id�� ����
	public int selectGroupIdByUserId(String user_id);
	
	// group_id�� �׷�� �ο��� ��ȯ
	public int selectGroupNumByGroupId(int group_id);
	
	//group_id�� ���� �׷� �̸��� ��ȯ
	public String selectGroupNameByGroupId(int group_id);
	
	// hbti_id�� ������ �׷��� �� ���� ��ȯ
	public int selectGroupCntByHbtiId(int hbti_id);
	
	//group_id�� ���� id�� ��ȯ
	public Group selectGroupLeaderByGroupId(int group_id);
	
	//�׷��� id�� ������ ���� ���� �ĺ��� ��ȯ
	public String selectNextLeaderByGroupId(@Param("leader_id") String leader_id,
											@Param("group_id") int group_id);
	
	// �׷��� ���� ����
	public int updateLeader(@Param("leader_id") String leader_id,
							@Param("group_id") int group_id);
	
	// �׷� �̸��� �����ϴ��� Ȯ��
	public String selectExistingGroupName(String group_name);
	
	// �׷� �̸����� �׷� ���̵� ã�Ƴ�
	public int selectGroupIdByGroupName(String group_name);
	
	// �׷� ����
	public int insertGroup(Group group);
	
	// �׷� ���� ����
	public int updateGroup(Group group);
	
	// �׷� ����
	public int deleteGroup(int group_id);
	
	// ç���� ����Ʈ ���� ��ȯ
	public int selectChallengeListCnt();
	
	// ç���� ����Ʈ ������Ʈ (�����ٷ���)
	public int updateChallengeList(int cntList);
}
