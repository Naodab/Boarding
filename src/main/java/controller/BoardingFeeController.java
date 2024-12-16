package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import model.bean.Invoice;
import model.bo.InvoiceBO;
import model.dto.BoardingFeeResponse;

@WebServlet("/boardingFees")
public class BoardingFeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final InvoiceBO invoiceBO = InvoiceBO.getInstance();
       
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

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String mode = request.getParameter("mode");
		switch(mode) {
			case "updateBoardingFee":
				updateBoardingFee(request, response);
				break;
		}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
	}
	
	private void updateBoardingFee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String jsonBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Gson gson = new Gson();
		JsonArray jsonArray = gson.fromJson(jsonBody, JsonArray.class);
		List<BoardingFeeResponse> boardingFeeList = new ArrayList<BoardingFeeResponse>();
		for (JsonElement jsonElement : jsonArray) {
			int boardingFeeId = Integer.parseInt(extractValue(jsonElement.toString(), "boardingFeeId"));
			int student_id = Integer.parseInt(extractValue(jsonElement.toString(), "studentId"));
			int invoice_id = Integer.parseInt(extractValue(jsonElement.toString(), "invoiceId"));
			String statusStr = extractValue(jsonElement.toString(), "status");
			int status = 0;
			if (statusStr.equals("submitted")) status = 2;
			else if (statusStr.equals("printed")) status = 3;
			boardingFeeList.add(new BoardingFeeResponse(student_id, null, invoice_id, status, boardingFeeId));
        }
		for (BoardingFeeResponse boardingFeeResponse : boardingFeeList) {
			if (boardingFeeResponse.getStatus() == 2) {
				Invoice oldInvoice = invoiceBO.selectById(boardingFeeResponse.getInvoiceId());
				if (oldInvoice.getStatusPayment() == 0 || oldInvoice.getStatusPayment() == 1) {
					oldInvoice.setInvoice_id(boardingFeeResponse.getInvoiceId());
					oldInvoice.setStudent_id(boardingFeeResponse.getStudent_id());
					oldInvoice.setPayment_date(Date.valueOf(LocalDate.now()));
					oldInvoice.setBoardingFee_id(boardingFeeResponse.getBoardingFeeId());
					oldInvoice.setStatusPayment((byte)boardingFeeResponse.getStatus());
					invoiceBO.updateById(oldInvoice);
				}
			}
		}
	}
	
	private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
