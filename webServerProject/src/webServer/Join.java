package webServer;

import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import dto.UsersInfo;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/join.do")
public class Join implements Controller,DataBinding  {
	
	private UserDao userDao;
	public Join setAdminDao(UserDao userDao) {
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
		System.out.println("조인  여기로 들어옴");
		User user = (User)model.get("userInfo");
		UsersInfo.addUser(user);
		int result = userDao.insertUser(user);
//		System.out.println("포토 ---"+user.getPhoto());
		System.out.println(result);
		System.out.println(UsersInfo.UserList.size());
		model.put("result", result);

		return "/Jsp/joinResult.jsp";
	}
}
