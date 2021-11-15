package controller.challenge;

import java.util.List;
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.ChallengePost;
import model.User;
import model.service.ExistingChallengePostException;
import model.service.GroupManager;
import model.service.UserManager;

//���� ���ε带 ���� API�� ����ϱ� ����...
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

//���� �뷮 �ʰ��� ���� Exception Ŭ������ FileUploadBase Ŭ������ Inner Ŭ������ ó��
import org.apache.commons.fileupload.servlet.*;

public class AddChallengeController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		UserManager userManager = UserManager.getInstance();
		GroupManager groupManager = GroupManager.getInstance();
		String user_id = "jeongmin";
		
		ChallengePost post;
		
		if(request.getServletPath().equals("/challenge/addForm")) {
			try {
				// ���� ��¥�� ChallengePost�� �ִ��� Ȯ��
				groupManager.isPost(user_id);
				return "/challenge/addForm.jsp";
				
			} catch (ExistingChallengePostException e) {
				request.setAttribute("addFailed", true);
				request.setAttribute("Exception", e);
				
				System.out.println(e.getMessage());
				post = groupManager.findPost(user_id);
				request.setAttribute("postInfo", post);
				
				return "/challenge/view.jsp";
			}
		}
		// ���� ���� �Ķ���͸� ó���ϴ� �ڵ�
		String content = null;
		String fileName = null;
		
		//���۵� �������� ���ڵ� Ÿ���� multipart ���� ���θ� üũ�Ѵ�.
		boolean check = ServletFileUpload.isMultipartContent(request);
		
		if(check) {
			// �Ʒ��� ���� �ϸ� Tomcat ���ο� ����� ������Ʈ�� ���� �ؿ� upload ������ ������ 
			ServletContext context = request.getServletContext();
			String path = context.getRealPath("/upload");
			File dir = new File(path);
			
			if(!dir.exists()) dir.mkdir();
		
		
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
	            //���� ���ۿ� ���� �⺻���� ���� Factory Ŭ������ �����Ѵ�.
	            factory.setSizeThreshold(10 * 1024);
	            //�޸𸮿� �ѹ��� ������ �������� ũ�⸦ �����Ѵ�.
	            //10kb �� �޸𸮿� �����͸� �о� ���δ�.
	            factory.setRepository(dir);
	            //���۵� �������� ������ ������ �ӽ� ������ �����Ѵ�.        
	            
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            //Factory Ŭ������ ���� ���� ���ε� �Ǵ� ������ ó���� ��ü�� �����Ѵ�.
	            upload.setSizeMax(10 * 1024 * 1024);
	            //���ε� �� ������ �ִ� �뷮�� 10MB���� ���� ����Ѵ�.
	            upload.setHeaderEncoding("utf-8");
	            //���ε� �Ǵ� ������ ���ڵ��� �����Ѵ�.
	            
	            List<FileItem> items = (List<FileItem>)upload.parseRequest(request);
	            
	            //upload ��ü�� ���۵Ǿ� �� ��� �����͸� Collection ��ü�� ��´�.
	            for(int i = 0; i < items.size(); ++i) {
	            	FileItem item = (FileItem)items.get(i);
	            	//commons-fileupload�� ����Ͽ� ���۹����� 
	            	//��� parameter�� FileItem Ŭ������ �ϳ��� ����ȴ�.
	            	
	            	String value = item.getString("utf-8");
	            	//�Ѿ�� ���� ���� �ѱ� ó���� �Ѵ�.
	            	
	            	if(item.isFormField()) {//�Ϲ� �� �����Ͷ��...                		
	            		if(item.getFieldName().equals("content")) content = value;
	            		//key ���� content�̸� content ������ ���� �����Ѵ�.
	            	}
	            	else {//�����̶��...
	            		if(item.getFieldName().equals("image")) {
	            		//key ���� picture�̸� ���� ������ �Ѵ�.
	            			fileName = item.getName();//���� �̸� ȹ�� (�ڵ� �ѱ� ó�� ��)
	            			if(fileName == null || fileName.trim().length() == 0) continue;
	            			//������ ���۵Ǿ� ���� �ʾҴٸ� �ǳ� �ڴ�.
	            			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
	            			//���� �̸��� ������ ��ü ��α��� �����ϱ� ������ �̸� �κи� �����ؾ� �Ѵ�.
	            			//���� C:\Web_Java\aaa.gif��� �ϸ� aaa.gif�� �����ϱ� ���� �ڵ��̴�.
	            			File file = new File(dir, fileName);
	            			item.write(file);
	            			//������ upload ��ο� ������ �����Ѵ�.
	            			//FileItem ��ü�� ���� �ٷ� ��� ������ �� �ִ�.
	            		}
	            	}
	            }
			} catch(SizeLimitExceededException e) {
			//���ε� �Ǵ� ������ ũ�Ⱑ ������ �ִ� ũ�⸦ �ʰ��� �� �߻��ϴ� ����ó��
				e.printStackTrace();           
            }catch(FileUploadException e) {
            //���� ���ε�� ���õǾ� �߻��� �� �ִ� ���� ó��
                e.printStackTrace();
            }catch(Exception e) {            
                e.printStackTrace();
            }
			try {
				User user = userManager.findUser(user_id);
				// ���� �α����� user_Id�� �׷� ���̵� ������.
				int group_id = userManager.belongToGroup(user_id);
				
				post = new ChallengePost(
						user.getName(),
						user_id,
						content,
						fileName,
						0
						);
				groupManager.addPost(post, group_id);
				
				request.setAttribute("postInfo", post);
				
				return "/challenge/view.jsp";
			} catch (Exception e) { e.printStackTrace(); }
		
		}
		
		return null;
	}

}
