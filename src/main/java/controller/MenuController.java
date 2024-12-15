package controller;

import com.google.gson.Gson;
import model.bean.Menu;
import model.bo.FoodBO;
import model.bo.GlobalBO;
import model.bo.MenuBO;
import model.dto.MenuAdminResponse;
import model.dto.PreAddMenu;
import model.dto.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/menus")
public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final MenuBO menuBO = MenuBO.getInstance();
	private final FoodBO foodBO = FoodBO.getInstance();
	private final GlobalBO globalBO = GlobalBO.getInstance();
	private final static int MENUS_PER_PAGE = 10;
	private final Gson gson = new Gson();
       
    public MenuController() {
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
		String destination = "/admin/menu.jsp";
		String mode = request.getParameter("mode");
		if (mode == null) {
			getServletContext().getRequestDispatcher(destination).forward(request, response);
			return;
		}
		switch (mode) {
			case "see" -> {
				String search = request.getParameter("search");
				String searchField = request.getParameter("searchField");
				int page = Integer.parseInt(request.getParameter("page"));
				SearchResponse<MenuAdminResponse> result = menuBO.getPage(page,
						MENUS_PER_PAGE, searchField, search);
				response.getWriter().write(gson.toJson(result));
				response.getWriter().flush();
			}
			case "add" -> {
				Menu menu = getMenuFromRequest(request);
				menuBO.insert(menu);
				response.sendRedirect(request.getContextPath() + "/menus");
			}
			case "update" -> {
				Menu menu = getMenuFromRequest(request);
				menuBO.update(menu);
				response.sendRedirect(request.getContextPath() + "/menus");
			}
			case "preAdd" -> {
				response.getWriter().write(gson.toJson(new PreAddMenu(
						globalBO.getAuto_IncrementOf("menu"),
						foodBO.getNameAndIdOfCategory(true),
						foodBO.getNameAndIdOfCategory(false)
				)));
				response.flushBuffer();
			}
			case "changeActive" -> {
				int menuId = Integer.parseInt(request.getParameter("menuId"));
				Menu menu = menuBO.selectById(menuId);
				menu.setActive(!menu.isActive());
				menuBO.update(menu);
				response.flushBuffer();
			}
		}
	}

	private Menu getMenuFromRequest(HttpServletRequest request) {
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		int subFoodId = Integer.parseInt(request.getParameter("subFoodId"));
		List<Integer> mainFoodIds = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			String mainFoodId = request.getParameter("mainFoodId" + i);
			if (mainFoodId != null && mainFoodId != "") {
				mainFoodIds.add(Integer.parseInt(mainFoodId));
			}
		}
		mainFoodIds.add(subFoodId);
		String activeStr = request.getParameter("active");
		boolean active = activeStr == null || Boolean.parseBoolean(activeStr);
		return new Menu(menuId, active, mainFoodIds, null);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
}
