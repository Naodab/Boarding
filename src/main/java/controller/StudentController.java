package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.bean.Student;
import model.bo.BoardingClassBO;
import model.bo.GlobalBO;
import model.bo.ParentsBO;
import model.bo.StudentBO;
import model.dto.ListParentsAndClasses;
import model.dto.NameAndIdResponse;
import model.dto.SearchStudentResponse;
import model.dto.StudentResponse;
import util.AdminUtil;
import util.LocalDateAdapter;

@WebServlet("/students")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
	private final StudentBO studentBO = StudentBO.getInstance();
	private final ParentsBO parentsBO = ParentsBO.getInstance();
	private final BoardingClassBO boardingClassBO = BoardingClassBO.getInstance();
	private final GlobalBO globalBO = GlobalBO.getInstance();

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
        switch (position) {
            case "Teacher" -> teacherHandler(request, response);
            case "Admin" -> adminHandler(request, response);
            case "Parents" -> parentsHandler(request, response);
        }
	}

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mode = request.getParameter("mode");
		switch (mode) {
			case "studentInfo":
				studentInfo(request, response);
				break;
			case "updatePhysical":
				updatePhysical(request, response);
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
		if (mode != null) {
			int STUDENT_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;
			switch (mode) {
				case "see":
					String search = request.getParameter("search");
					String searchField = request.getParameter("searchField");
					String sort = request.getParameter("sort");
					String sortField = request.getParameter("sortField");
					int page = Integer.parseInt(request.getParameter("page"));
					SearchStudentResponse result = studentBO.searchStudent(page, STUDENT_PER_PAGE, searchField, search, sortField, sort);
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
					response.sendRedirect(request.getContextPath() + "/students");
					return;
				case "preAdd":
					int nextId = globalBO.getAuto_IncrementOf("student");
					List<NameAndIdResponse> parentsAdd = parentsBO.getNameAndIds();
					List<NameAndIdResponse> classesAdd = boardingClassBO.getNameAndIds();
					ListParentsAndClasses preAdd = new ListParentsAndClasses(nextId, parentsAdd, classesAdd);
					response.getWriter().write(gson.toJson(preAdd));
					return;
				case "add":
					Student addStudent = getStudentFromRequest(request);
					if (studentBO.insert(addStudent))
						System.out.println("ADD student " + addStudent.getStudent_id());
					response.sendRedirect(request.getContextPath() + "/students");
					return;
				case "delete":
					int student_id = Integer.parseInt(request.getParameter("student_id"));
					System.out.println(student_id);
					if (studentBO.deleteByID(student_id))
						System.out.println("DELETE student " + student_id);
					response.sendRedirect(request.getContextPath() + "/students");
					return;
			}
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

	private void updatePhysical(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Gson gson = new Gson();
		Type studentListType = new TypeToken<List<Student>>() {}.getType();
		List<Student> studentLists = gson.fromJson(json.toString(), studentListType);
		for (int i = 0; i < studentLists.size(); i++) {
			Student student = studentBO.selectById(studentLists.get(i).getStudent_id());
			student.setHeight(studentLists.get(i).getHeight());
			student.setWeight(studentLists.get(i).getWeight());
			studentBO.update(student);
		}
	}

	private void studentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String jsonData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Student student =  studentBO.selectById(Integer.parseInt(extractValue(jsonData, "studentId")));
		StudentResponse responseStudent = new StudentResponse(student.getStudent_id(), 
															  student.getName(), 
															  student.getDateOfBirth().toLocalDate(),
															  student.getAddress(),
															  student.getSex(), student.getBoardingClass_id(),
															  student.getHeight(), student.getWeight());
		response.getWriter().write(gson.toJson(responseStudent));
	}

	private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}