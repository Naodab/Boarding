package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bean.BoardingClass;
import model.bo.BoardingClassBO;
import model.bo.GlobalBO;
import model.bo.TeacherBO;
import model.dto.*;
import util.AdminUtil;
import util.LocalDateAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/boardingClasses")
public class BoardingClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BOARDING_CLASSES_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;
	private final BoardingClassBO boardingClassBO = BoardingClassBO.getInstance();
	private final GlobalBO globalBO = GlobalBO.getInstance();
	private final TeacherBO teacherBO = TeacherBO.getInstance();
	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
			new LocalDateAdapter()).create();

	public BoardingClassController() {
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

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) {

	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String destination = "/admin/boardingClass.jsp";
		String mode = request.getParameter("mode");
		if (mode == null) {
			getServletContext().getRequestDispatcher(destination).forward(request, response);
			return;
		}
		switch (mode) {
			case "see" -> {
				String search = request.getParameter("search");
				String searchField = request.getParameter("searchField");
				String sort = request.getParameter("sort");
				String sortField = request.getParameter("sortField");
				int page = Integer.parseInt(request.getParameter("page"));
				SearchResponse<BoardingClassResponse> searchResponse = boardingClassBO.
						getPageBoardingClasses(page, BOARDING_CLASSES_PER_PAGE,
								searchField, search, sortField, sort);
				response.getWriter().print(gson.toJson(searchResponse));
				response.flushBuffer();
			}
			case "add" -> {
				BoardingClass boardingClass = getBoardingClassFromRequest(request);
				boardingClassBO.insert(boardingClass);
				response.sendRedirect(request.getContextPath() + "/boardingClasses");
			}
			case "student" -> {
				int page = Integer.parseInt(request.getParameter("page"));
				int boardingClassId = Integer.parseInt(request.getParameter("boardingClassId"));
				SearchResponse<NameAndIdResponse> detailStudent = boardingClassBO
						.getStudentDetail(boardingClassId, page, 8);
				response.getWriter().print(gson.toJson(detailStudent));
				response.flushBuffer();
			}
			case "preAdd" -> {
				int nextId = globalBO.getAuto_IncrementOf("boardingClass");
				List<NameAndIdResponse> teachers = teacherBO.getNameAndIds();
				response.getWriter().print(gson.toJson(new PreAddBoardingClass(nextId, teachers)));
				response.flushBuffer();
			}
			case "checkRoom" -> {
				response.getWriter().write("{\"result\": " + boardingClassBO
						.existsByRoom(request.getParameter("room")) + "}");
				response.flushBuffer();
			}
			case "preUpdate" -> {
				List<NameAndIdResponse> teachers = teacherBO.getNameAndIds();
				response.getWriter().print(gson.toJson(teachers));
				response.flushBuffer();
			}
			case "update" -> {
				BoardingClass boardingClass = getBoardingClassFromRequest(request);
				boardingClassBO.update(boardingClass);
				response.sendRedirect(request.getContextPath() + "/boardingClasses");
			}
			case "delete" -> {
				int id = Integer.parseInt(request.getParameter("boardingClassId"));
				boardingClassBO.deleteById(id);
				response.sendRedirect(request.getContextPath() + "/boardingClasses");
			}
		}
	}

	private BoardingClass getBoardingClassFromRequest(HttpServletRequest request) {
		int boardingClassId = Integer.parseInt(request.getParameter("boardingClassId"));
		String name = request.getParameter("name");
		String room = request.getParameter("room");
		int numberOfBed = Integer.parseInt(request.getParameter("numberOfBed"));
		int teacherId = Integer.parseInt(request.getParameter("teacherId"));
		return new BoardingClass(boardingClassId, name, numberOfBed,
				room, teacherId, null);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}

}
