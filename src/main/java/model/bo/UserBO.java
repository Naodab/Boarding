package model.bo;

import model.bean.Parents;
import model.bean.Teacher;
import model.bean.User;
import model.dao.ParentsDAO;
import model.dao.TeacherDAO;
import model.dao.UserDAO;
import model.dto.SearchResponse;
import model.dto.UserResponse;

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
	private final ParentsDAO parentsDAO = ParentsDAO.getInstance();
	private final TeacherDAO teacherDAO = TeacherDAO.getInstance();
	
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
		String password = userDAO.getDefaultPassword();
		return password.substring(1, password.length() - 1);
	}

	public boolean updatePassword(String username, String password) {
		return userDAO.updatePassword(username, password);
	}

	public boolean updateUsername(String oldUser, String newUser) {
		return userDAO.updateUsername(oldUser, newUser);
	}

	public boolean existsByUsername(String username) {
		return selectByUserName(username) != null;
	}

	public SearchResponse<UserResponse> getPageUsers(int page, int amount,
		String searchField, String search, String sortField, String sortType) {
		return new SearchResponse<>(userDAO.count(searchField, search),
			userDAO.getPageUsers(page, amount, searchField,
					search, sortField, sortType).stream().map(user -> {
				UserResponse userResponse = toUserResponse(user);
				if ("Parents".equals(userResponse.getPosition())) {
					Parents parents = parentsDAO.selectByUsername(user.getUsername());
					if (parents != null) {
						userResponse.setUserId(parents.getParents_id());
						userResponse.setName(parents.getName());
					}
				} else if ("Teachers".equals(userResponse.getPosition())) {
					Teacher teacher = teacherDAO.selectByUsername(user.getUsername());
					if (teacher != null) {
						userResponse.setUserId(teacher.getTeacher_id());
						userResponse.setName(teacher.getName());
					}
				}
				return userResponse;
			}).toList());
	}

	private UserResponse toUserResponse(User u) {
		return new UserResponse(u.isAvtive(), u.getPosition(),
				u.getUsername(), u.getLastLogin().toLocalDate());
	}

	public boolean checkPasswordDefault(String password) {
		return userDAO.getDefaultPassword().equals(password);
	}
}
