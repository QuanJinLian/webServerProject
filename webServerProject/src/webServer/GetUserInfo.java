package webServer;

import java.util.ArrayList;
import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/searchUser.do")
public class GetUserInfo  implements Controller,DataBinding  {
	
	private UserDao userDao;
	public GetUserInfo setAdminDao(UserDao userDao) {
		this.userDao = userDao;
		return this;
	}
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"userInfo", User.class
		};
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		System.out.println("getUserInfo 여기로 들어옴");
		User user = (User)model.get("userInfo");
		String id = user.getId();
		user = userDao.getUserInfo(id);
		if(user != null) {
			model.put("result", 1);
			model.put("userInfo", user);
		}else {
			model.put("result", -1);
		}
		
		return "/Jsp/getUserInfoResult.jsp";
	}
}
