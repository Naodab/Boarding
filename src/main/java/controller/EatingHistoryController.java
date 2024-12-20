package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bean.BoardingFee;
import model.bo.*;
import model.dto.EatingHistoryRequest;
import model.dto.EatingHistoryResponse;
import model.dto.SearchResponse;
import util.AdminUtil;
import util.LocalDateAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dto.EatingDayResponse;

@WebServlet("/eatingHistories")
public class EatingHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final EatingHistoryBO eatingHistoryBO = EatingHistoryBO.getInstance();
	private final BoardingFeeBO boardingFeeBO = BoardingFeeBO.getInstance();
	private final MenuBO menuBO = MenuBO.getInstance();
	private final static int EATING_HISTORIES_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

	private final GlobalBO globalBO = GlobalBO.getInstance();

    public EatingHistoryController() {
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

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String mode = request.getParameter("mode");
		RequestDispatcher rd = null;
		String destination = "";
		switch(mode) {
		case "eatingDay":
			int numberOfItems = globalBO.getSizeOf("boardingFee", "");
			request.setAttribute("numberOfItems", numberOfItems);
			List<Integer> monthsValid = eatingHistoryBO.getMonthsValid();
			request.setAttribute("monthsValid", monthsValid);
			List<EatingDayResponse> listEatingDayResponse = new ArrayList<EatingDayResponse>();
			if (request.getParameter("boardingFeeId") != null) {
				int boardingFeeId = Integer.parseInt(request.getParameter("boardingFeeId"));
				BoardingFee boardingFee = boardingFeeBO.selectByEndMonth(boardingFeeId);
				listEatingDayResponse = eatingHistoryBO.eatingDay(request, response, boardingFee.getBoardingFee_id());
				request.setAttribute("boardingFeeId", boardingFeeId);
			} else {
				listEatingDayResponse = eatingHistoryBO.eatingDay(request, response, globalBO.getFirstIDOf("boardingFee"));
			}
			request.setAttribute("listEatingDayResponse", listEatingDayResponse);
			destination = "/teachers/eatingDay.jsp";
			rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
			break;
		}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String destination = "/admin/eatingHistory.jsp";
		String mode = request.getParameter("mode");
		if (mode == null) {
			getServletContext().getRequestDispatcher(destination)
					.forward(request, response);
			return;
		}
		switch (mode) {
			case "see" -> {
				String search = request.getParameter("search");
				String searchField = request.getParameter("searchField");
				int page = Integer.parseInt(request.getParameter("page"));
				int month = Integer.parseInt(request.getParameter("month"));
				SearchResponse<EatingHistoryResponse> result = eatingHistoryBO.getPages(page,
						EATING_HISTORIES_PER_PAGE, month, searchField, search);
				response.getWriter().print(gson.toJson(result));
				response.flushBuffer();
			}
			case "months" -> {
				List<Integer> months = eatingHistoryBO.getMonthsValid();
				response.getWriter().write(gson.toJson(months));
				response.flushBuffer();
			}
			case "createFee" -> {
				BoardingFee boardingFee = (BoardingFee) request.getSession().getAttribute("boardingFee");
				SearchResponse<EatingHistoryResponse> result = eatingHistoryBO.createFee(boardingFee);
				response.getWriter().print(gson.toJson(result));
				response.flushBuffer();
			}
			case "menu" -> {
				response.getWriter().write(gson.toJson(menuBO.selectAllIds()));
				response.flushBuffer();
			}
			case "detailMenu" -> {
				int menuId = Integer.parseInt(request.getParameter("menuId"));
				response.getWriter().write(gson.toJson(menuBO
						.toMenuResponse(menuBO.selectById(menuId))));
				response.flushBuffer();
			}
			case "add" -> {
				StringBuilder jsonBuilder = new StringBuilder();
				BufferedReader reader = request.getReader();
				String line;
				while ((line = reader.readLine()) != null) {
					jsonBuilder.append(line);
				}
				String jsonData = jsonBuilder.toString();
				BoardingFee boardingFee = (BoardingFee) request.getSession().getAttribute("boardingFee");
				EatingHistoryRequest[] eatingHistories = gson.fromJson(jsonData, EatingHistoryRequest[].class);
				eatingHistoryBO.fee(boardingFee, Arrays.stream(eatingHistories).toList());
				response.flushBuffer();
			}
		}
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
}
