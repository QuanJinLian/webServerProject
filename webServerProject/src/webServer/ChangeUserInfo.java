package webServer;

import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/changeUserInfo.do")
public class ChangeUserInfo  implements Controller,DataBinding {

	private UserDao userDao;
	public ChangeUserInfo setAdminDao(UserDao userDao) {
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
		System.out.println("ChangeUserInfo 여기로 들어옴");
		User user = (User)model.get("userInfo");
		int result = 0;
		if(user.getPhoto() != null) {
			result = userDao.updateUserInfo(user, "photo");
		}else if(user.getNikName() != null) {
			result = userDao.updateUserInfo(user, "nikName");
		}
		
		model.put("result", result);
		
		return "/Jsp/updateUserInfoResult.jsp";
	}

}
