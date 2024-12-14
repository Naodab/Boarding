package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bo.UserBO;
import util.AdminUtil;
import util.LocalDateAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserBO userBO = UserBO.getInstance();
	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
			new LocalDateAdapter()).create();
	private static final int USERS_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;

	public AccountController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String mode = request.getParameter("mode");
		String destination = "/admin/users.jsp";
		if (!"Admin".equals(request.getSession().getAttribute("position"))) {
			request.setAttribute("message", "Bạn không có quyền truy cập vào chức năng này!!!");
			destination = "/index.jsp";
			getServletContext().getRequestDispatcher(destination).forward(request, response);
			return;
		}
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
				response.getWriter().write(gson.toJson(userBO.getPageUsers(page, USERS_PER_PAGE,
						searchField, search, sortField, sort)));
				response.flushBuffer();
			}
			case "changeDefault" -> {
				String newPassword= request.getParameter("password");
				userBO.alterDefaultPassword(newPassword);
				response.sendRedirect(request.getContextPath() + "/users");
			}
			case "getDefault" -> {
				response.getWriter().write("{\"result\": \"" + userBO.getDefaultPassword() + "\"}");
				response.flushBuffer();
			}
			case "reset" -> {
				String username = request.getParameter("username");
				userBO.updatePassword(username, userBO.getDefaultPassword());
				response.getWriter().write("{\"result\": true}");
				response.flushBuffer();
			}
		}
	}
}
