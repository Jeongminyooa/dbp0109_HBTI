package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import model.Group;
import model.User;
import model.dao.mybatis.mapper.GroupMapper;

public class GroupDAO {

	private SqlSessionFactory sqlSessionFactory;
	
	// sqlSessionFactory ����
	public GroupDAO() {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	// hbti_id�� �׷� ����Ʈ�� ���� O
	public List<Group> findGroupList(int hbti_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupListByGroupId(hbti_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// Ű���忡 �´� �׷� ����Ʈ�� ��ȯ O
	public List<Group> searchGroupList(int hbti_id, String keyword) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupListByKeyword(hbti_id, keyword);
		} finally {
			sqlSession.close();
		}
	}
	
	// group_id�� �׷�� ����Ʈ�� ���� O
	public List<User> findUserList(int group_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectUserListByGroupId(group_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// group_id�� �׷� ������ ��ȯ O
	public Group findGroup(int group_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupByGroupId(group_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// ����ID�� �׷�ID�� ã�ƿ�
	public int findGroupUserId(String user_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupIdByUserId(user_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// group_id�� �׷�� �ο��� ��ȯ
	public int findNumberOfMember(int group_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupNumByGroupId(group_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// group_id�� ���� �׷� �̸��� ��ȯ
	public String findGroupName(int group_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupNameByGroupId(group_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// ������ �׷��� �� ���� ��ȯ O
	public int findGroupCnt(int hbti_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupCntByHbtiId(hbti_id);
		} finally {
			sqlSession.close();
		}
	}
	
	//group_id�� ���� ����(name, id) ��ȯ
	public Group findLeader(int group_id) { 
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupLeaderByGroupId(group_id);
		} finally {
			sqlSession.close();
		}
	}
	
	// ���� ���� �ĺ��� ��ȯ
	public String findNextLeader(String user_id, int group_id) { 
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectNextLeaderByGroupId(user_id, group_id);
		} finally {
			sqlSession.close();
		}
	}
	

	// �׷��� ���� ����
	public int updateLeader(String leader_id, int group_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(GroupMapper.class).updateLeader(leader_id, group_id);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;	
		} finally {
			sqlSession.close();
		}
	}
	
	// �׷� �̸��� �����ϴ��� Ȯ��
	public boolean existingGroupName(String group_name) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			String name = sqlSession.getMapper(GroupMapper.class).selectExistingGroupName(group_name);
			
			if(name != null) return true;
			return false;
		} finally {
			sqlSession.close();
		}
	}
	
	// �׷� �̸����� �׷� ���̵� ã�Ƴ�
	public int findGroupId(String group_name) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectGroupIdByGroupName(group_name);
		} finally {
			sqlSession.close();
		}
	}
	
	// �׷� ���� O
	public int create(Group group) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(GroupMapper.class).insertGroup(group);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;	
		} finally {
			sqlSession.close();
		}
	}
	
	// �׷� ���� ���� O
	public int update(Group group) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(GroupMapper.class).updateGroup(group);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;	
		} finally {
			sqlSession.close();
		}
	}
	
	// �׷� ����
	public int delete(int group_id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(GroupMapper.class).deleteGroup(group_id);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;	
		} finally {
			sqlSession.close();
		}
	}
	
	// ç���� ����Ʈ ���� ��ȯ
	public int cntOfChallengeList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(GroupMapper.class).selectChallengeListCnt();
		} finally {
			sqlSession.close();
		}
	}
	// �������� ç���� ����
	public int assignChallenge(int cntList) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(GroupMapper.class).updateChallengeList(cntList);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;	
		} finally {
			sqlSession.close();
		}
	}
		
}
