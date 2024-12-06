package model.bo;

import java.util.List;

import model.bean.Student;
import model.dao.StudentDAO;

public class StudentBO {
	private static StudentBO _instance;
	private StudentBO() {}
	
	public static StudentBO getInstance() {
		if (_instance == null)
			_instance = new StudentBO();
		return _instance;
	}
	
	private StudentDAO dao = StudentDAO.getInstance();
	public boolean insert(Student t) {
		return dao.insert(t);
	}

	public boolean delete(Student t) {
		return dao.delete(t);
	}

	public boolean deleteByID(int t) {
		return dao.deleteByID(t);
	}

	public boolean update(Student t) {
		return dao.update(t);
	}

	public List<Student> selectAll() {
		return dao.selectAll();
	}

	public Student selectById(Student t) {
		return dao.selectById(t);
	}

	public Student selectById(int t) {
		return dao.selectById(t);
	}

	public List<Integer> selectByBoardingClass_id(int boardingClass_id) {
		return dao.selectByBoardingClass_id(boardingClass_id);
	}

	public List<Integer> selectByParents_id(int parents_id) {
		return dao.selectByParents_id(parents_id);
	}

	public List<Student> selectByBoardingClass_id2(int boardingClass_id) {
		return dao.selectByBoardingClass_id2(boardingClass_id);
	}	
}
