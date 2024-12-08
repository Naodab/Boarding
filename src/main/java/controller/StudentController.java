package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Student;
import model.bo.StudentBO;

@WebServlet("/students")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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

	private void adminHandler(HttpServletRequest request, HttpServletResponse response) {

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
