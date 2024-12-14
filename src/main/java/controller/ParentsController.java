package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Parents;
import model.bo.ParentsBO;

@WebServlet("/parents")
public class ParentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ParentsController() {
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
		case "parentInfo":
			Parents parent = parentInfo(request, response);
			String responseJson = jsonParentResponse(parent);
			response.getWriter().write(responseJson);
			break;
	}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String destination = "/adimn/parents.jsp";
		String mode = request.getParameter("mode");
		if (mode != null) {
			switch(mode) {
			case "see":
				String search = request.getParameter("search");
				String sort = request.getParameter("sort");
				String sortField = request.getParameter("sortField");
				int page = Integer.parseInt(request.getParameter("page"));
				
			}
		}
		getServletContext().getRequestDispatcher(destination).forward(request, response);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private Parents parentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder sb = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	    while ((line = reader.readLine()) != null) {
	    	sb.append(line);
	       }
	    }
	    String jsonData = sb.toString();
	    String parent_id = extractValue(jsonData, "parentId");
	    return ParentsBO.getInstance().selectById(Integer.parseInt(parent_id));
	}
	
	private String jsonParentResponse(Parents parent) {
		String jsonResponse = String.format("{"
                + "\"name\":\"%s\","
                + "\"dateOfBirth\":\"%s\","
                + "\"address\":\"%s\","
                + "\"sex\":\"%s\","
                + "\"parent_id\": %d,"
                + "\"email\":\"%s\","
                + "\"phoneNumber\":\"%s\""
                + "}", 	parent.getName(),
                		parent.getDateOfBirth().toString(),
                		parent.getAddress(),
                		parent.getSex(),
                		parent.getParents_id(),
                		parent.getEmail(),
                		parent.getPhoneNumber());
		return jsonResponse;
	}
	
	private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
