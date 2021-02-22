package webServer;

import java.util.ArrayList;
import java.util.Map;

import controls.Controller;
import dao.UserDao;
import dto.User;
import dto.UsersInfo;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/getFriendList.do")
public class GetFriendList implements Controller,DataBinding  {
	
	private UserDao userDao;
	public GetFriendList setAdminDao(UserDao userDao) {
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
		System.out.println("getFriendList 여기로 들어옴");
		User user = (User)model.get("userInfo");
		String friendList = userDao.getFriendList(user.getId());
		System.out.println(friendList);
		ArrayList<User> list = new ArrayList<User>();
		if(friendList.length()>0) {
			String [] ids = friendList.split("&");
			for(String id : ids) {
				list.add(userDao.getUserInfo(id));
//				list.add(UsersInfo.getUser(id));
			}
//			System.out.println("list"+list.get(0).getPhoto());
			model.put("result", 1);
			model.put("list", list);
		}else {
			model.put("result", -1);
		}
		return "/Jsp/getFriendListResult.jsp";
	}

}
