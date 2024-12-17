package model.bo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.bean.BoardingClass;
import model.bean.Parents;
import model.bean.Student;
import model.dao.BoardingClassDAO;
import model.dao.ParentsDAO;
import model.dao.StudentDAO;
import model.dto.NameAndIdResponse;
import model.dto.SearchStudentResponse;
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

	private final StudentDAO studentDAO = StudentDAO.getInstance();
	private final ParentsDAO parentsDAO = ParentsDAO.getInstance();
	private final BoardingClassDAO boardingClassDAO = BoardingClassDAO.getInstance();

	public StudentResponse toStudentResponse(Student student) {
		StudentResponse response =  new StudentResponse(student.getStudent_id(), student.getName(),
				student.getDateOfBirth().toLocalDate(),
				student.getAddress(), student.getSex(), student.isSubMeal(), student.getParents_id(),
				student.getBoardingClass_id());
		Parents parents = parentsDAO.selectById(student.getParents_id());
		if (parents != null)
			response.setParentName(parents.getName());
		else System.out.println(student.getParents_id());
		BoardingClass boardingClass = boardingClassDAO.selectById(student.getBoardingClass_id());
		if (boardingClass != null)
			response.setBoardingClassName(boardingClassDAO
					.selectById(student.getBoardingClass_id()).getName());
		response.setHeight(student.getHeight());
		response.setWeight(student.getWeight());
		return response;
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

	public boolean adminUpdate(Student t) {
		return studentDAO.adminUpdate(t);
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

	public List<StudentResponse> getPageStudent(int page, int amount,
			String searchField, String search, String sortField, String sortType) {
		return studentDAO.getPageStudents(page, amount, searchField, search, sortField, sortType)
				.stream().map(this::toStudentResponse).toList();
	}

	public SearchStudentResponse searchStudent(int page, int amount,
	    String searchField, String search, String sortField, String sortType) {
		int size = studentDAO.count(searchField, search);
		List<StudentResponse> students = getPageStudent(page, amount,
				searchField, search, sortField, sortType);
		return new SearchStudentResponse(size, students);
	}

	public List<NameAndIdResponse> getNameAndIdByParents_id(int parents_id) {
		return selectByParents_id(parents_id).stream().map(student -> {
			Student student1 = studentDAO.selectById(student);
			return new NameAndIdResponse(student1.getStudent_id(), student1.getName());
		}).toList();
	}
	
	public void updatePhysical(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Gson gson = new Gson();
		Type studentListType = new TypeToken<List<Student>>() {}.getType();
		List<Student> studentLists = gson.fromJson(json.toString(), studentListType);
        for (Student studentList : studentLists) {
            Student student = selectById(studentList.getStudent_id());
            student.setHeight(studentList.getHeight());
            student.setWeight(studentList.getWeight());
            update(student);
        }
	}
	
	public StudentResponse studentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String jsonData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Student student =  selectById(Integer.parseInt(extractValue(jsonData, "studentId")));
		StudentResponse responseStudent = toStudentResponse(student);
		return responseStudent;
	}

	private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
