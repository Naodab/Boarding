package model.bo;

import model.bean.User;
import model.dao.UserDAO;

public class UserBO {
	private static UserBO _instance;
	private UserBO() {}
	
	public static UserBO getInstance() {
		if (_instance == null)
			_instance = new UserBO();
		return _instance;
	}
	
	private UserDAO dao = UserDAO.getInstance();
	
	public String authenticate(String username, String password) {
		User user = dao.selectByUserName(username);
		if (user != null && user.getPassword().equals(password)) {
			return user.getPosition();
		}
		return null;
	}
}
