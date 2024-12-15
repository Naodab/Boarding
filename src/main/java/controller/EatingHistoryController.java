package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bean.BoardingFee;
import model.bean.EatingHistory;
import model.bo.EatingHistoryBO;
import model.bo.MenuBO;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/eatingHistories")
public class EatingHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final EatingHistoryBO eatingHistoryBO = EatingHistoryBO.getInstance();
	private final MenuBO menuBO = MenuBO.getInstance();
	private final static int EATING_HISTORIES_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

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

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) {

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
				BoardingFee boardingFee = (BoardingFee) request.getSession().getAttribute("boardingFee");
				String jsonData = jsonBuilder.toString();
				EatingHistoryRequest[] eatingHistories = gson.fromJson(jsonData, EatingHistoryRequest[].class);
				eatingHistoryBO.fee(boardingFee, Arrays.stream(eatingHistories).toList());
				response.flushBuffer();
			}
		}
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
}
