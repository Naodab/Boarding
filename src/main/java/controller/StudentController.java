package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.bean.Student;
import model.bo.BoardingClassBO;
import model.bo.GlobalBO;
import model.bo.ParentsBO;
import model.bo.StudentBO;
import model.dto.ListParentsAndClasses;
import model.dto.NameAndIdResponse;
import model.dto.SearchStudentResponse;
import util.LocalDateAdapter;

@WebServlet("/students")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int STUDENT_PER_PAGE = 12;
	private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
	private StudentBO studentBO = StudentBO.getInstance();
	private ParentsBO parentsBO = ParentsBO.getInstance();
	private BoardingClassBO boardingClassBO = BoardingClassBO.getInstance();
	private GlobalBO globalBO = GlobalBO.getInstance();

	public StudentController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String position = (String) request.getSession().getAttribute("position");
		if (position.equals("Teacher")) {
			teacherHandler(request, response);
		} else if (position.equals("Admin")) {
			adminHandler(request, response);
		} else if (position.equals("Parents")) {
			parentsHandler(request, response);
		}
	}

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mode = (String)request.getParameter("mode");
		switch(mode) {
			case "studentInfor":
				Student student = studentInfor(request, response);
				String responseJson = jsonStudentResponse(student);
				response.getWriter().write(responseJson);
				break;
		}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String mode = request.getParameter("mode");
		String destination = "/admin/students.jsp";
		if (mode != null)
			switch (mode) {
			case "see":
				String search = request.getParameter("search");
				String sort = request.getParameter("sort");
				String sortField = request.getParameter("sortField");
				int page = Integer.parseInt(request.getParameter("page"));
				SearchStudentResponse result = studentBO.searchStudent(page, STUDENT_PER_PAGE, search, sortField, sort);
				String json = gson.toJson(result);
				response.getWriter().write(json);
				return;
			case "preUpdate":
				List<NameAndIdResponse> parents = parentsBO.getNameAndIds();
				List<NameAndIdResponse> classes = boardingClassBO.getNameAndIds();
				ListParentsAndClasses preUpdate = new ListParentsAndClasses(0, parents, classes);
				response.getWriter().write(gson.toJson(preUpdate));
				return;
			case "update":
				Student student = getStudentFromRequest(request);
				studentBO.adminUpdate(student);
				break;
			case "preAdd":
				int nextId = globalBO.getAuto_IncrementOf("student");
				List<NameAndIdResponse> parentsAdd = parentsBO.getNameAndIds();
				List<NameAndIdResponse> classesAdd = boardingClassBO.getNameAndIds();
				ListParentsAndClasses preAdd = new ListParentsAndClasses(nextId, parentsAdd, classesAdd);
				response.getWriter().write(gson.toJson(preAdd));
				return;
			case "add":
				Student addStudent = getStudentFromRequest(request);
				studentBO.insert(addStudent);
				break;
			case "delete":
				int student_id = Integer.parseInt(request.getParameter("student_id"));
				studentBO.deleteByID(student_id);
				return;
			}
		getServletContext().getRequestDispatcher(destination).forward(request, response);
	}

	private Student getStudentFromRequest(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		int student_id = Integer.parseInt(request.getParameter("student_id"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		Date dateOfBirth = Date.valueOf(LocalDate.parse(request.getParameter("dateOfBirth")));
		int parents_id = Integer.parseInt(request.getParameter("parents_id"));
		int boardingClass_id = Integer.parseInt(request.getParameter("boardingClass_id"));
		boolean subMeal = request.getParameter("subMeal").equals("true");
		boolean sex = request.getParameter("sex").equals("Nam");
		return new Student(name, dateOfBirth, address, sex, student_id, 0, 0, parents_id, boardingClass_id, subMeal,
				null, null);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}

	private Student studentInfor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder sb = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	    while ((line = reader.readLine()) != null) {
	    	sb.append(line);
	       }
	    }
	    String jsonData = sb.toString();
	    String student_id = extractValue(jsonData, "studentId");
	    return StudentBO.getInstance().selectById(Integer.parseInt(student_id));
	}

	private String jsonStudentResponse(Student student) {
		String jsonResponse = String.format("{"
                + "\"name\":\"%s\","
                + "\"dateOfBirth\":\"%s\","
                + "\"address\":\"%s\","
                + "\"sex\":\"%s\","
                + "\"student_id\": %d,"
                + "\"weight\":%f,"
                + "\"height\":%f"
                + "}", 	student.getName(),
                		student.getDateOfBirth().toString(),
                		student.getAddress(),
                		student.getSex(),
                		student.getStudent_id(),
                		student.getWeight(),
                		student.getHeight());
		return jsonResponse;
	}

	private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
