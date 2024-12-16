package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.bean.User;
import model.bo.UserBO;

@WebServlet("/auth")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserBO userBO = UserBO.getInstance();
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public UserController() {
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
		String destination = "/index.jsp"; 
		switch (mode) {
		case "login":
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String position = userBO.authenticate(username, password);
			if (position == null) {
				request.setAttribute("message", "Tài khoản hoặc mật khẩu không chính xác!!!");
				getServletContext().getRequestDispatcher(destination).forward(request, response);
				return;
			}
			request.getSession().setAttribute("username", username);
			User user = userBO.selectByUserName(username);
			user.setLastLogin(Date.valueOf(LocalDate.now()));
			userBO.update(user);
			// check it before do anything
			request.getSession().setAttribute("position", position);
			if (position.equals("Admin")) {
				destination = "/students";
			} else if (position.equals("Teacher")) {
				destination = "/teachers?mode=teacherInfor";
			} else if (position.equals("Parents")) {
				destination = "/parents?mode=parentInfor";
			}
			break;
		case "checkExist":
			if (request.getSession().getAttribute("position") == null) return;
			String usernameCheck = request.getParameter("username");
			response.getWriter().write("{\"result\": "+ userBO.existsByUsername(usernameCheck) + "}");
			response.flushBuffer();
			return;
		case "changePassword":
			if (request.getSession().getAttribute("position") == null) return;
			String usernameChange = (String) request.getSession().getAttribute("username");
			StringBuilder jsonBuilder = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonBuilder.append(line);
			}
			String jsonData = jsonBuilder.toString();
			JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
			String oldPassword = jsonObject.get("oldPassword").getAsString();
			String newPassword = jsonObject.get("password").getAsString();
			String positionCheck = userBO.authenticate(usernameChange, oldPassword);
			if (positionCheck == null) {
				response.getWriter().write("{\"result\": " + false +"}");
			} else {
				userBO.updatePassword(usernameChange, newPassword);
				response.getWriter().write("{\"result\": " + true +"}");
			}
			response.flushBuffer();
			return;
		case "logout":
			request.getSession().removeAttribute("position");
			request.getSession().removeAttribute("username");
		}
		response.sendRedirect(request.getContextPath() + destination);
	}
}
