package scheduler;

import java.sql.SQLException;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import model.service.GroupManager;

// ����ȭ ó���� ���� ������̼� �߰� : �ش� �����ٷ��� ������ ���� ���� ��û ���
@DisallowConcurrentExecution 
public class ChallengeScheduler implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// ���� ��ų ���� ����
		System.out.println("test : " + new Date(System.currentTimeMillis()));
		
		GroupManager groupManager = GroupManager.getInstance();
		try {
			int rlt = groupManager.assignChallenge();
			if (rlt == 0) {
				throw new JobExecutionException();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new JobExecutionException();
		}
	}

}
