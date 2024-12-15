package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bo.BoardingClassBO;
import model.bo.InvoiceBO;
import util.LocalDateAdapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/invoices")
public class InvoiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final InvoiceBO invoiceBO = InvoiceBO.getInstance();
	private final BoardingClassBO boardingClassBO = BoardingClassBO.getInstance();
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
	private final static int INVOICES_PER_PAGE = 8;
       
    public InvoiceController() {
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
            throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String mode = request.getParameter("mode");
		if (mode == null) return;
		switch (mode) {
			case "see" -> {
				String search = request.getParameter("search");
				String searchField = request.getParameter("searchField");
				int page = Integer.parseInt(request.getParameter("page"));
				int boardingFeeId = Integer.parseInt(request.getParameter("boardingFeeId"));
				response.getWriter().write(gson.toJson(invoiceBO.getPage(page,
						INVOICES_PER_PAGE, boardingFeeId, searchField, search)));
				response.flushBuffer();
			}
			case "print" -> {
				int boardingFeeId = Integer.parseInt(request.getParameter("boardingFeeId"));
				invoiceBO.updateStatus(boardingFeeId);
				response.flushBuffer();
			}
			case "class" -> {
				int boardingFeeId = Integer.parseInt(request.getParameter("boardingFeeId"));
				int boardingClassId = Integer.parseInt(request.getParameter("boardingClassId"));
				int page = Integer.parseInt(request.getParameter("page"));
				response.getWriter().write(gson.toJson(invoiceBO.getPageByClass(page,
						10, boardingFeeId, boardingClassId)));
				response.flushBuffer();
			}
		}
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
}
