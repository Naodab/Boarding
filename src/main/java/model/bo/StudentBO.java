package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.bean.Student;
import model.dao.BoardingClassDAO;
import model.dao.ParentsDAO;
import model.dao.StudentDAO;
import model.dto.StudentResponse;

public class StudentBO {
	private static StudentBO _instance;

	private StudentBO() {
	}

	public static StudentBO getInstance() {
		if (_instance == null)
			_instance = new StudentBO();
		return _instance;
	}

	private StudentDAO studentDAO = StudentDAO.getInstance();
	private ParentsDAO parentsDAO = ParentsDAO.getInstance();
	private BoardingClassDAO boardingClassDAO = BoardingClassDAO.getInstance();

	private StudentResponse toStudentResponse(Student student) {
		return new StudentResponse(student.getStudent_id(), student.getName(), student.getDateOfBirth(),
				student.getAddress(), student.getSex(), student.isSubMeal());
	}

	public boolean insert(Student t) {
		return studentDAO.insert(t);
	}

	public boolean delete(Student t) {
		return studentDAO.delete(t);
	}

	public boolean deleteByID(int t) {
		return studentDAO.deleteByID(t);
	}

	public boolean update(Student t) {
		return studentDAO.update(t);
	}

	public List<Student> selectAll() {
		return studentDAO.selectAll();
	}

	public Student selectById(Student t) {
		return studentDAO.selectById(t);
	}

	public Student selectById(int t) {
		return studentDAO.selectById(t);
	}

	public List<Integer> selectByBoardingClass_id(int boardingClass_id) {
		return studentDAO.selectByBoardingClass_id(boardingClass_id);
	}

	public List<Integer> selectByParents_id(int parents_id) {
		return studentDAO.selectByParents_id(parents_id);
	}

	public List<Student> selectByBoardingClass_id2(int boardingClass_id) {
		return studentDAO.selectByBoardingClass_id2(boardingClass_id);
	}

	public List<StudentResponse> getPageStudent(int page, int amount) {
		List<Student> students = studentDAO.getPageStudents(page, amount);
		List<StudentResponse> result = new ArrayList<StudentResponse>();
		for (Student student : students) {
			StudentResponse response = toStudentResponse(student);
			response.setParentName(parentsDAO.selectById(student.getParents_id()).getName());
			response.setBoardingClassName(boardingClassDAO.selectById(student.getBoardingClass_id()).getName());
			result.add(response);
		}
		return result;
	}
}
