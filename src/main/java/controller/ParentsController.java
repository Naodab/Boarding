package controller;

import java.io.BufferedReader;
import java.io.IOException;
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
import model.bean.Parents;
import model.bean.User;
import model.bo.GlobalBO;
import model.bo.ParentsBO;
import model.bo.StudentBO;
import model.bo.UserBO;
import model.dto.NameAndIdResponse;
import model.dto.ParentsResponse;
import model.dto.SearchResponse;
import util.AdminUtil;
import util.LocalDateAdapter;

@WebServlet("/parents")
public class ParentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ParentsBO parentsBO = ParentsBO.getInstance();
	private final StudentBO studentBO = StudentBO.getInstance();
	private final GlobalBO globalBO = GlobalBO.getInstance();
	private final UserBO userBO = UserBO.getInstance();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

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
        switch (position) {
            case "Teacher" -> teacherHandler(request, response);
            case "Admin" -> adminHandler(request, response);
            case "Parents" -> parentsHandler(request, response);
        }
	}

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mode = (String)request.getParameter("mode");
		switch(mode) {
		case "parentInfor":
			Parents parent = parentInfor(request, response);
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
		String destination = "/admin/parents.jsp";
		String mode = request.getParameter("mode");
		if (mode != null) {
            int PARENTS_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;
            switch(mode) {
			case "see":
				String search = request.getParameter("search");
				String searchField = request.getParameter("searchField");
				String sort = request.getParameter("sort");
				String sortField = request.getParameter("sortField");
				int page = Integer.parseInt(request.getParameter("page"));
				SearchResponse<ParentsResponse> searchResponse = parentsBO
						.searchResponse(page, PARENTS_PER_PAGE, searchField, search, sortField, sort);
				response.getWriter().write(gson.toJson(searchResponse));
				response.flushBuffer();
				return;
			case "preAdd":
				int nextId = globalBO.getAuto_IncrementOf("parents");
				response.getWriter().write("{'nextId': " + nextId + "}");
				response.flushBuffer();
				return;
			case "children":
				int parents_id = Integer.parseInt(request.getParameter("parents_id"));
				List<NameAndIdResponse> nameAndIdResponses = studentBO.getNameAndIdByParents_id(parents_id);
				response.getWriter().write(gson.toJson(nameAndIdResponses));
				response.flushBuffer();
				return;
			case "delete":
				int id = Integer.parseInt(request.getParameter("parents_id"));
				parentsBO.deleteByID(id);
				userBO.deleteByUsername(request.getParameter("username"));
				return;
			case "update":
				Parents parents = getParentsFromRequest(request);
				Parents beforeUpdate = parentsBO.selectById(parents.getParents_id());
				parentsBO.update(parents);
				if (!beforeUpdate.getPhoneNumber().equals(parents.getPhoneNumber())) {
					User userUpdate = userBO.selectByUserName(beforeUpdate.getPhoneNumber());
					userUpdate.setUsername(parents.getPhoneNumber());
					userBO.update(userUpdate);
				}
				return;
			case "add":
				Parents addParents = getParentsFromRequest(request);
				parentsBO.insert(addParents);
				User newUser = new User(addParents.getPhoneNumber(), null, "Parents", true, Date.valueOf(LocalDate.now()));
				userBO.insert(newUser);
				return;
			}
		}
		getServletContext().getRequestDispatcher(destination).forward(request, response);
	}

	private Parents getParentsFromRequest(HttpServletRequest request) {
		int parents_id = Integer.parseInt(request.getParameter("parents_id"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phone");
		String email = request.getParameter("email");
		boolean sex = request.getParameter("sex").equals("Nam");
		Date dateOfBirth = Date.valueOf(LocalDate.parse(request.getParameter("dateOfBirth")));
		return new Parents(name, dateOfBirth, address, sex, parents_id, phoneNumber, email, null);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private Parents parentInfor(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
