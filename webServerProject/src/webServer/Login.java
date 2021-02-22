package webServer;

import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/login.do")
public class Login implements Controller,DataBinding  {
	private UserDao userDao;
	public Login setAdminDao(UserDao userDao) {
		this.userDao = userDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
//				"id", String.class,
//				"pwd",String.class
				"userInfo", User.class
		};
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		System.out.println("여기로 들어옴");
		User user = (User)model.get("userInfo");
		User userInfo = userDao.login(user.getId(), user.getPwd());
		System.out.println("id---"+user.getId()+",  pwd --"+user.getPwd());
		if(userInfo != null) {
			System.out.println("로그인 성공");
			model.put("userInfo", userInfo);
			model.put("result", "성공");
		}else {
			System.out.println("로그인 실패");
			model.put("result", "실패");
		}

		return "/Jsp/loginResult.jsp";
	}



}
