package webServer;

import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/idCheck.do")
public class IdCheck implements Controller,DataBinding  {

	private UserDao userDao;
	public IdCheck setAdminDao(UserDao userDao) {
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
		System.out.println("IdCheck 여기로 들어옴");
		User user = (User)model.get("userInfo");
		if(user != null) {
			boolean result = userDao.idCheck(user.getId());
			model.put("result", result);
		}else {
			model.put("result", "연결실패");
		}

		return "/Jsp/IdCheckResult.jsp";
	}

}
