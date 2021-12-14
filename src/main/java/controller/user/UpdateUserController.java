package controller.user;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.service.UserManager;
import model.User;

public class UpdateUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (request.getMethod().equals("GET")) {
			// GET request: ȸ������ ���� form ��û
			// ������ UpdateUserFormController�� ó���ϴ� �۾��� ���⼭ ����
			UserManager manager = UserManager.getInstance();
			String update_id = UserSessionUtils.getLoginUserId(request.getSession());

			log.debug("UpdateForm Request : {}", update_id);

			User user = manager.findUser(update_id); //  // �����Ϸ��� ����� ���� �˻�
			request.setAttribute("user_id", user.getUser_id());
			request.setAttribute("user", user);

			return "/user/UpdateUserForm.jsp"; // �˻��� ����� ������ update form���� ����
		}
		// Post request : ȸ�� ���� ������ ����!
		
		UserManager manager = UserManager.getInstance();
		String tmp_user_id = UserSessionUtils.getLoginUserId(request.getSession());
		
		/* image ������ ���� ��� ���� ����Ǿ� �ִ� img�����ͼ� �״�� �ٽ� ��� */
		User user = manager.findUser(tmp_user_id);
		String image = user.getImage();
		log.debug("image: " , image);
		
		boolean check = ServletFileUpload.isMultipartContent(request);
		// ���۵� �������� ���ڵ� Ÿ���� multipart ���� ���θ� üũ�Ѵ�.
		// ���� multipart�� �ƴ϶�� ���� ������ ó������ �ʴ´�.
		String user_id = null;
		String password = null;
		String name = null;
		String descr = null;

		String filename = null;

		if (check) {// ���� ������ ���Ե� ���°� �´ٸ�

			// �Ʒ��� ���� �ϸ� Tomcat ���ο� ����� ������Ʈ�� ���� �ؿ� upload ������ ������
			ServletContext context = request.getServletContext();
			String path = context.getRealPath("/upload");
			File dir = new File(path);

			// Tomcat �ܺ��� ������ �����Ϸ��� �Ʒ��� ���� ���� ��η� ���� �̸��� ������
			// File dir = new File("C:/Temp");

			if (!dir.exists())
				dir.mkdir();
			// ���۵� ������ ������ ���� ��θ� �����.

			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// ���� ���ۿ� ���� �⺻���� ���� Factory Ŭ������ �����Ѵ�.
				factory.setSizeThreshold(10 * 1024);
				// �޸𸮿� �ѹ��� ������ �������� ũ�⸦ �����Ѵ�.
				// 10kb �� �޸𸮿� �����͸� �о� ���δ�.
				factory.setRepository(dir);
				// ���۵� �������� ������ ������ �ӽ� ������ �����Ѵ�.

				ServletFileUpload upload = new ServletFileUpload(factory);
				// Factory Ŭ������ ���� ���� ���ε� �Ǵ� ������ ó���� ��ü�� �����Ѵ�.
				upload.setSizeMax(10 * 1024 * 1024);
				// ���ε� �� ������ �ִ� �뷮�� 10MB���� ���� ����Ѵ�.
				upload.setHeaderEncoding("utf-8");
				// ���ε� �Ǵ� ������ ���ڵ��� �����Ѵ�.

				List<FileItem> items = (List<FileItem>) upload.parseRequest(request);

				// upload ��ü�� ���۵Ǿ� �� ��� �����͸� Collection ��ü�� ��´�.
				for (int i = 0; i < items.size(); ++i) {
					FileItem item = (FileItem) items.get(i);
					// commons-fileupload�� ����Ͽ� ���۹�����
					// ��� parameter�� FileItem Ŭ������ �ϳ��� ����ȴ�.

					String value = item.getString("utf-8");
					// �Ѿ�� ���� ���� �ѱ� ó���� �Ѵ�.

					if (item.isFormField()) {// �Ϲ� �� �����Ͷ��...
						if (item.getFieldName().equals("user_id"))
							user_id = value;
						else if (item.getFieldName().equals("password"))
							password = value;
						else if (item.getFieldName().equals("name"))
							name = value;
						else if (item.getFieldName().equals("descr"))
							descr = value;

					} else {// �����̶��...
						if (item.getFieldName().equals("image")) {
							// key ���� picture�̸� ���� ������ �Ѵ�.
							filename = item.getName();// ���� �̸� ȹ�� (�ڵ� �ѱ� ó�� ��)
							if (filename == null || filename.trim().length() == 0) {
								filename = image;
								continue;
							}
							// ������ ���۵Ǿ� ���� �ʾҴٸ� �ǳ� �ڴ�.
							filename = filename.substring(filename.lastIndexOf("\\") + 1);
							// ���� �̸��� ������ ��ü ��α��� �����ϱ� ������ �̸� �κи� �����ؾ� �Ѵ�.
							// ���� C:\Web_Java\aaa.gif��� �ϸ� aaa.gif�� �����ϱ� ���� �ڵ��̴�.
							File file = new File(dir, filename);
							item.write(file);
							// ������ upload ��ο� ������ �����Ѵ�.
							// FileItem ��ü�� ���� �ٷ� ��� ������ �� �ִ�.
						}
					}
				}

			} catch (SizeLimitExceededException e) {
				// ���ε� �Ǵ� ������ ũ�Ⱑ ������ �ִ� ũ�⸦ �ʰ��� �� �߻��ϴ� ����ó��
				e.printStackTrace();
			} catch (FileUploadException e) {
				// ���� ���ε�� ���õǾ� �߻��� �� �ִ� ���� ó��
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		User updateUser = new User(user_id, password, name, descr, filename);

		log.debug("Update User : {}", updateUser);

		/* UserManager manager = UserManager.getInstance(); */
		manager.update(updateUser);
		return "redirect:/user/myPage";
	}
}