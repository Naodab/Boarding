package model.bo;

import java.util.List;

import model.bean.Teacher;
import model.dao.TeacherDAO;

public class TeacherBO {
	private static TeacherBO _instance;

	private TeacherBO() {
	}

	public static TeacherBO getInstance() {
		if (_instance == null)
			_instance = new TeacherBO();
		return _instance;
	}

	private TeacherDAO dao = TeacherDAO.getInstance();

	public boolean insert(Teacher t) {
		return dao.insert(t);
	}

	public boolean delete(Teacher t) {
		return dao.delete(t);
	}

	public boolean deleteById(int t) {
		return dao.deleteById(t);
	}

	public boolean update(Teacher t) {
		return dao.update(t);
	}

	public List<Teacher> selectAll() {
		return dao.selectAll();
	}

	public Teacher selectById(Teacher t) {
		return dao.selectById(t);
	}

	public Teacher selectById(int t) {
		return dao.selectById(t);
	}
	
	public Teacher selectByUsername(String username) {
		return dao.selectByUsername(username);
	}

	public int selectByBoardingClass_id(int boardingClass_id) {
		return dao.selectByBoardingClass_id(boardingClass_id);
	}

	public List<Teacher> getPageTeacher(int page, int amount) {
		return dao.selectPage(page, amount);
	}
}
