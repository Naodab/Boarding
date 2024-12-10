package model.bo;

import model.bean.User;
import model.dao.UserDAO;

import java.util.List;

public class UserBO {
	private static UserBO _instance;
	private UserBO() {}
	
	public static UserBO getInstance() {
		if (_instance == null)
			_instance = new UserBO();
		return _instance;
	}
	
	private final UserDAO userDAO = UserDAO.getInstance();
	
	public String authenticate(String username, String password) {
		User user = userDAO.selectByUserName(username);
		if (user != null && user.getPassword().equals(password)) {
			return user.getPosition();
		}
		return null;
	}

	public boolean deleteByUsername(String t) {
		return userDAO.deleteByUsername(t);
	}

	public boolean insert(User t) {
		return userDAO.insert(t);
	}

	public boolean delete(User t) {
		return userDAO.delete(t);
	}

	public boolean update(User t) {
		return userDAO.update(t);
	}

	public boolean updatePassword(User t, String password) {
		return userDAO.updatePassword(t, password);
	}

	public List<User> selectAll() {
		return userDAO.selectAll();
	}

	public User selectById(User t) {
		return userDAO.selectById(t);
	}

	public User selectByUserName(String username) {
		return userDAO.selectByUserName(username);
	}

	public boolean alterDefaultPassword(String passwotd) {
		return userDAO.alterDefaultPassword(passwotd);
	}

	public String getDefaultPassword() {
		return userDAO.getDefaultPassword();
	}

	public boolean updateUsername(String oldUser, String newUser) {
		return userDAO.updateUsername(oldUser, newUser);
	}
}
