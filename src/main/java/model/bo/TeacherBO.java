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

	private TeacherDAO teacherDAO = TeacherDAO.getInstance();

	public boolean insert(Teacher t) {
		return teacherDAO.insert(t);
	}

	public boolean delete(Teacher t) {
		return teacherDAO.delete(t);
	}

	public boolean deleteById(int t) {
		return teacherDAO.deleteById(t);
	}

	public boolean update(Teacher t) {
		return teacherDAO.update(t);
	}

	public List<Teacher> selectAll() {
		return teacherDAO.selectAll();
	}

	public Teacher selectById(Teacher t) {
		return teacherDAO.selectById(t);
	}

	public Teacher selectById(int t) {
		return teacherDAO.selectById(t);
	}
	
	public Teacher selectByUsername(String username) {
		return teacherDAO.selectByUsername(username);
	}

	public int selectByBoardingClass_id(int boardingClass_id) {
		return teacherDAO.selectByBoardingClass_id(boardingClass_id);
	}

	public List<Teacher> getPageTeacher(int page, int amount) {
		return teacherDAO.selectPage(page, amount);
	}
}
