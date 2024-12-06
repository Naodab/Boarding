package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.UserBO;

@WebServlet("/auth")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserBO bo = UserBO.getInstance();

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mode = request.getParameter("mode");
		String destination = "/index.jsp"; 
		switch (mode) {
		case "login":
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String position = bo.authenticate(username, password);
			if (position == null) {
				request.setAttribute("message", "Tài khoản hoặc mật khẩu không chính xác!!!");
				getServletContext().getRequestDispatcher(destination).forward(request, response);
				return;
			}
			request.getSession().setAttribute("username", username);
			
			// check it before do anything
			request.getSession().setAttribute("position", position);
			if (position.equals("Admin")) {
				destination = "/admin/students.jsp";
			} else if (position.equals("Teacher")) {
				destination = "/teacher/main.jsp";
			} else if (position.equals("Parents")) {
				
			}
			break;
		}
		response.sendRedirect(request.getContextPath() + destination);
		return;
	}
}
