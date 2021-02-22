package webServer;

import java.util.ArrayList;
import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/updateFriendList.do")
public class UpdateFriendList  implements Controller,DataBinding  {
	
	private UserDao userDao;
	public UpdateFriendList setAdminDao(UserDao userDao) {
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
		System.out.println("updateFriendList 여기로 들어옴");
		User user = (User)model.get("userInfo");
		int result = userDao.UpdateFriendList(user);
//		String[] friends = user.getFriendList().split("&");
//		ArrayList<User> friendList = null ;
//		if(result >0) {
//			for(String friend : friends) {
//				friendList.add(userDao.getUserInfo(friend));
//			}
//		}else {
//			
//		}
		model.put("result",result);
		return "/Jsp/updateFriendListResult.jsp";
	}

}
