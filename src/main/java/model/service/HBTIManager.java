package model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.HBTI;

import model.dao.mybatis.HbtiDAO;

public class HBTIManager {
	private static HBTIManager hbtiMan = new HBTIManager();

	private HbtiDAO hbtiDAO;
	
	private HBTIManager() {
		try {
			hbtiDAO = new HbtiDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HBTIManager getInstance() {
		return hbtiMan;
	}
	
	public HBTI findHBTI(int hbti_id) throws SQLException {
		HBTI hbti = hbtiDAO.findHBTI(hbti_id);
		return hbti;
	}
	
	public String findHbtiImg(String name) throws SQLException {
		return hbtiDAO.findHbtiImg(name);
	}
	
	/* Ranking */
	/* hbti_id�� �ش��ϴ� �׷��� �� �ο� �� */
	public int numOfGroupMem(int hbti_id) {
		// �ϴ� hbti_id�� group�� ���� �ִ��� group_id ã�ƿ���
		List<Integer> groupList = new ArrayList<>();
		groupList = hbtiDAO.group_idByHBTI(hbti_id);// groupDAO�� �ٲٱ�

		// groupList�� List�ϳ� �� �������� �� group�� �ο� �� ���ͼ� �� �ο� �� ���ϱ�
		int sum = 0;
		for (int i = 0; i < groupList.size(); i++) {
			int group_id = groupList.get(i);
			sum += hbtiDAO.getNumberOfUsersInGroup(group_id);// groupDAO�� �ٲٱ�
		}

		return sum;
	}

	public int numOfUserWhoDidChallengeInGroup(int hbti_id) {
		// �ϴ� hbti_id�� group�� ���� �ִ��� group_id ã�ƿ���
		List<Integer> groupList = new ArrayList<>();
		groupList = hbtiDAO.group_idByHBTI(hbti_id);

		// groupList�� List�ϳ� �� �������� �� group�� User_id �������� List��
		List<String> userListEachGroup = new ArrayList<>();
		int cnt = 0;
		for (int i = 0; i < groupList.size(); i++) {
			int group_id = groupList.get(i);

			userListEachGroup = hbtiDAO.userListEachGroup(group_id); // �׷쿡 �ִ� user����Ʈ �ҷ�����
			
			List<String> userList = new ArrayList<>();
			for (int j = 0; j < userListEachGroup.size(); j++) {
				userList.add(userListEachGroup.get(j));
			}
			
			cnt += hbtiDAO.todayChallegeUserNum(userList);
		}
		return cnt;
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