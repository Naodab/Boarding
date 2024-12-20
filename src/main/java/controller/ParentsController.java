package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.bean.Parents;
import model.bean.User;
import model.bo.BoardingFeeBO;
import model.bo.GlobalBO;
import model.bo.ParentsBO;
import model.bo.StudentBO;
import model.bo.UserBO;
import model.dto.BoardingFeeResponse;
import model.dto.EatingDayResponse;
import model.dto.NameAndIdResponse;
import model.dto.ParentResponse;
import model.dto.ParentsResponse;
import model.dto.SearchResponse;
import util.AdminUtil;
import util.LocalDateAdapter;

@WebServlet("/parents")
public class ParentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ParentsBO parentsBO = ParentsBO.getInstance();
	private final StudentBO studentBO = StudentBO.getInstance();
	private final BoardingFeeBO boardingFeeBO = BoardingFeeBO.getInstance();
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
		String mode = request.getParameter("mode");
		switch(mode) {
		case "parentInfo":
			Parents parent = parentsBO.parentInfo(request, response);
			ParentResponse responseParent = new ParentResponse(parent.getName(), parent.getDateOfBirth(),
					parent.getParents_id(), parent.getSex(), parent.getAddress(), parent.getPhoneNumber(),
					parent.getEmail());
			response.getWriter().write(gson.toJson(responseParent));
			break;
		case "detailParents":
			Parents parentDetail = parentsBO.parentInfo(request, response);
			ParentsResponse response1 = parentsBO.toParentsResponse(parentDetail);
			response.getWriter().write(gson.toJson(response1));
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
				response.getWriter().write("{\"nextId\": " + nextId + "}");
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
				response.sendRedirect(request.getContextPath() + "/parents");
				return;
			case "update":
				Parents parents = getParentsFromRequest(request);
				Parents beforeUpdate = parentsBO.selectById(parents.getParents_id());
				parentsBO.update(parents);
				if (!beforeUpdate.getPhoneNumber().equals(parents.getPhoneNumber())) {
					userBO.updateUsername(beforeUpdate.getPhoneNumber(),
							parents.getPhoneNumber());
				}
				response.sendRedirect(request.getContextPath() + "/parents");
				return;
			case "add":
				Parents addParents = getParentsFromRequest(request);
				if (parentsBO.insert(addParents))
					System.out.println("Add" + addParents.getName());
				User newUser = new User(addParents.getPhoneNumber(), null, "Parents", true, Date.valueOf(LocalDate.now()));
				userBO.insert(newUser);
				response.sendRedirect(request.getContextPath() + "/parents");
				return;
			}
		}
		getServletContext().getRequestDispatcher(destination).forward(request, response);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mode = (String)request.getParameter("mode");
		String username = (String)request.getSession().getAttribute("username");
		Parents parent = parentsBO.selectByUsername(username);
		RequestDispatcher rd = null;
		String destination = "";
		switch(mode) {
			case "parentInfor":
				List<Integer> studentIdList = parent.getStudent_id();
				List<String> studentNameList = new ArrayList<String>();
				List<Integer> teacherIdList = new ArrayList<Integer>();
				List<String> teacherNameList = new ArrayList<String>();
				parentsBO.updateAllList(parent, studentNameList, teacherIdList, teacherNameList);
				ParentResponse parentResponse = new ParentResponse(parent.getName(), parent.getDateOfBirth(), 
					parent.getParents_id(), parent.getSex(),
					parent.getAddress(), parent.getPhoneNumber(),
					parent.getEmail(), studentIdList, teacherIdList,
					studentNameList, teacherNameList);
				request.setAttribute("parentInfor", parentResponse);
				destination = "/parents/parentsInfor.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "seeBoardingFees":
				List<BoardingFeeResponse> listBoardingFeeResponses = new ArrayList<BoardingFeeResponse>();
				listBoardingFeeResponses = boardingFeeBO.timeEating(parent);
				request.setAttribute("listBoardingFeeResponses", listBoardingFeeResponses);
				destination = "/parents/historyPayment.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "seeEatingHistory":
				Map<Integer, String> weekMap = parentsBO.getListWeek();
				request.setAttribute("weekMap", weekMap);
				List<EatingDayResponse> listEatingDayResponses = new ArrayList<EatingDayResponse>();
			    String timeEating = request.getParameter("timeEating");
				if (timeEating != null) {
					listEatingDayResponses = parentsBO.getEatingDay(timeEating);
					request.setAttribute("timeEating", timeEating);
					request.setAttribute("listEatingDayResponses", listEatingDayResponses);
				} else {
					
				}
				destination = "/parents/historyEating.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
		}
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
}
