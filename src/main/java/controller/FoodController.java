package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bean.Food;
import model.bo.FoodBO;
import model.bo.GlobalBO;
import model.dto.FoodAdminResponse;
import model.dto.SearchResponse;
import util.LocalDateAdapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/foods", asyncSupported = true)
public class FoodController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final FoodBO foodBO = FoodBO.getInstance();
	private final GlobalBO globalBO = GlobalBO.getInstance();
	private final static int FOODS_PER_PAGE = 16;
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
       
    public FoodController() {
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
		String destination = "/admin/foods.jsp";
		String mode = request.getParameter("mode");
		if (mode == null) {
			getServletContext().getRequestDispatcher(destination).forward(request, response);
			return;
		}
		switch (mode) {
			case "see" -> {
				AsyncContext async = request.startAsync();
				async.start(() -> {
					try {
						String search = request.getParameter("search");
						String searchField = request.getParameter("searchField");
						int page = Integer.parseInt(request.getParameter("page"));
						response.getWriter().write(gson.toJson(foodBO.getPage(page, FOODS_PER_PAGE, searchField, search)));
					} catch (Exception e) {e.printStackTrace();}
					finally { async.complete(); }
				});
			}
			case "add" -> {
				Food newFood = getFoodFromRequest(request);
				foodBO.insert(newFood);
				response.sendRedirect(request.getContextPath() + "/foods");
			}
			case "update" -> {
				Food updateFood = getFoodFromRequest(request);
				foodBO.update(updateFood);
				response.sendRedirect(request.getContextPath() + "/foods");
			}
			case "preAdd" -> {
				int nextId = globalBO.getAuto_IncrementOf("food");
				response.getWriter().write("{\"nextId\": " + nextId + "}");
				response.flushBuffer();
			}
			case "checkDelete" -> {
				int foodId = Integer.parseInt(request.getParameter("foodId"));
				Food food = foodBO.selectById(foodId);
				boolean result = food.getMenu_ids().isEmpty();
				response.getWriter().write(gson.toJson("{\"result\": " + result + "}"));
				response.flushBuffer();
			}
			case "delete" -> {
				int foodId = Integer.parseInt(request.getParameter("foodId"));
				foodBO.delete(new Food(foodId, null, false, null));
				response.flushBuffer();
			}
			case "checkExist" -> {
				String name = request.getParameter("name");
				response.getWriter().write("{\"result\": " + foodBO.existsByName(name) + "}");
				response.flushBuffer();
			}
		}
	}

	private Food getFoodFromRequest(HttpServletRequest request) {
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		String foodName = request.getParameter("name");
		boolean category = Boolean.parseBoolean(request.getParameter("category"));
		return new Food(foodId, foodName, category, null);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}

}
