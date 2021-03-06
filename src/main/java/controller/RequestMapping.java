package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.group.*;
import controller.challenge.*;
import controller.todo.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
   
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	
        mappings.put("/", new ForwardController("index.jsp"));
        
        
        mappings.put("/user/login/form", new ForwardController("/user/loginForm.jsp"));
        mappings.put("/user/login", new LoginController());
        
       
        mappings.put("/user/join", new JoinUserController());
        mappings.put("/user/hbtiTest", new HBTIController());
        mappings.put("/user/hbtiTestResult", new HBTIController());
        
  
        mappings.put("/main", new MainController());
        
     
        mappings.put("/user/myPage", new MyPageController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/user/quit", new QuitUserController());
        mappings.put("/group/quit", new QuitGroupController());
        
   
        mappings.put("/user/logout", new LogoutController());
        
      
         mappings.put("/todo/view", new ViewTodoController());
         mappings.put("/todo/date", new ViewTodoController());
         mappings.put("/todo/dateOk", new ViewTodoController());
         
     
        mappings.put("/todo/modifyForm", new ModifyTodoController());
        mappings.put("/todo/modifyDateForm", new ModifyTodoController());
        mappings.put("/todo/modify", new ModifyTodoController());
        mappings.put("/todo/date/modify", new ModifyTodoController()); 
        
        mappings.put("/todo/doCheck", new CheckTodoController());
        mappings.put("/todo/doNotCheck", new CheckTodoController());
       
        mappings.put("/todo/addForm", new ForwardController("/todo/addForm.jsp"));
        mappings.put("/todo/add", new AddTodoController());
       
    
        mappings.put("/todo/delete", new DeleteTodoController());
        mappings.put("/todo/date/delete", new DeleteTodoController());
        
        
        mappings.put("/group/main", new ViewGroupController());
        mappings.put("/group/search", new SearchGroupController());
        mappings.put("/group/join", new JoinGroupController());
        
       
        mappings.put("/group/createForm", new ForwardController("/group/createForm.jsp"));
        mappings.put("/group/create", new CreateGroupController());
        
    
        mappings.put("/group/updateForm", new UpdateGroupController());
        mappings.put("/group/update", new UpdateGroupController());
        mappings.put("/group/manageUser", new UpdateGroupController());
        
        
        mappings.put("/group/delete", new DeleteGroupController());
        
       
        mappings.put("/challenge/view", new ViewChallengeController());
        mappings.put("/challenge/myView", new ViewChallengeController());
        
       
        mappings.put("/challenge/addForm", new AddChallengeController());
        mappings.put("/challenge/add", new AddChallengeController());
        
      
        mappings.put("/challenge/updateForm", new UpdateChallengeController());
        mappings.put("/challenge/update", new UpdateChallengeController());
        mappings.put("/challenge/updateLike_btn", new UpdateChallengeController());
        
       
        mappings.put("/challenge/delete", new DeleteChallengeController());
       
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	
        return mappings.get(uri);
    }
}
