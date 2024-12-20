package model.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Teacher;
import model.dao.BoardingClassDAO;
import model.dao.TeacherDAO;
import model.dto.NameAndIdResponse;
import model.dto.SearchResponse;
import model.dto.TeacherResponse;

public class TeacherBO {
	private static TeacherBO _instance;

	private TeacherBO() {
	}

	public static TeacherBO getInstance() {
		if (_instance == null)
			_instance = new TeacherBO();
		return _instance;
	}

	private final TeacherDAO teacherDAO = TeacherDAO.getInstance();
	private final BoardingClassDAO boardingClassDAO = BoardingClassDAO.getInstance();

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

	public List<TeacherResponse> getPageTeacher(int page, int amount, String searchField, String search,
			String sortField, String sortType) {
		return teacherDAO.selectPage(page, amount, searchField, search, sortField, sortType).stream()
				.map(this::toTeacherResponse).toList();
	}

	public SearchResponse<TeacherResponse> search(int page, int amount, String searchField, String search,
			String sortField, String sortType) {
		return new SearchResponse<>(teacherDAO.count(searchField, search),
				getPageTeacher(page, amount, searchField, search, sortField, sortType));
	}

	public TeacherResponse toTeacherResponse(Teacher t) {
		TeacherResponse response = new TeacherResponse(t.getTeacher_id(), t.getName(), t.getDateOfBirth().toLocalDate(),
				t.getAddress(), t.getPhoneNumber(), t.getEmail(), t.getSex());
		response.setBoardingClass(boardingClassDAO.selectById(t.getBoardingClass_id()).getName());
		return response;
	}

	public List<NameAndIdResponse> getNameAndIds() {
		return selectAll().stream().map(teacher -> new NameAndIdResponse(teacher.getTeacher_id(), teacher.getName()))
				.toList();
	}

	public void updateTeacherInfor(HttpServletRequest request, HttpServletResponse response, Teacher teacher)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		try (BufferedReader reader = request.getReader()) {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		}
		String jsonData = sb.toString();
		String address = extractValue(jsonData, "address");
		String email = extractValue(jsonData, "email");
		String phoneNumber = extractValue(jsonData, "phoneNumber");
		teacher.setAddress(address);
		teacher.setEmail(email);
		teacher.setPhoneNumber(phoneNumber);
		TeacherBO.getInstance().update(teacher);
	}

	private String extractValue(String json, String key) {
		String searchKey = "\"" + key + "\":\"";
		int startIndex = json.indexOf(searchKey) + searchKey.length();
		int endIndex = json.indexOf("\"", startIndex);
		return json.substring(startIndex, endIndex);
	}
}
