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
		String mode = request.getParameter("mode");
		String destination = "/"; 
		switch (mode) {
		case "login":
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String position = bo.authenticate(username, password);
			if (position == null)
				break;
			request.getSession().setAttribute("username", username);
			
			// check it before do anything
			request.getSession().setAttribute("position", position);
			if (position.equals("ADMIN")) {
				destination = "/admin/students.jsp";
			} else if (position.equals("TEACHER")) {
				
			} else if (position.equals("PARENTS")) {
				
			}
			break;
		}
		getServletContext().getRequestDispatcher(destination).forward(request, response);
	}

}
