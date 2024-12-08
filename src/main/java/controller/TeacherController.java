package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Student;
import model.bean.Teacher;
import model.bo.BoardingClassBO;
import model.bo.StudentBO;
import model.bo.TeacherBO;

@WebServlet("/teachers")
public class TeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TeacherController() {
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

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = (String)request.getSession().getAttribute("username");
		Teacher teacher = TeacherBO.getInstance().selectByUsername(username);
		String mode = (String)request.getParameter("mode");
		RequestDispatcher rd = null;
		String destination = "";
		switch(mode) {
			case "teacherInfor":
				request.setAttribute("teacherInfor", teacher);
				request.setAttribute("teachClass", BoardingClassBO.getInstance().selectById(teacher.getBoardingClass_id()).getName());
				destination = "/teachers/teacherInfor.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "boardingFee":
				break;
			case "studentInfor":
				List<Student> listStudents = StudentBO.getInstance().selectByBoardingClass_id2(teacher.getBoardingClass_id());
				request.setAttribute("listStudents", listStudents);
				destination = "/teachers/studentInfor.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "updateTeacherInfor":
				updateTeacherInfor(request, response, teacher);
				break;
		}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private void updateTeacherInfor(HttpServletRequest request, HttpServletResponse response, Teacher teacher) throws IOException {
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
