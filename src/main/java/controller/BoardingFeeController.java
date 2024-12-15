package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/boardingFees")
public class BoardingFeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardingFeeController() {
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

	private void adminHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String destination = "/admin/boardingFee.jsp";
		String mode = request.getParameter("mode");
		if (mode == null) {
			getServletContext().getRequestDispatcher(destination).forward(request, response);
			return;
		}
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}

}
