package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersInfo {
	public static Map<String, User> UserList = new HashMap<String, User>();
	
	public static void addUser(User user) {
		UserList.put(user.getId(), user);
	}
	
	public static void removeUser(String id) {
		UserList.remove(id);
	}
	
	public static User getUser(String id) {
		User user = UserList.get(id);
		return user;
	}
	

}
