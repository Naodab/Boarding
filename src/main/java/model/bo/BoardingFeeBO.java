package model.bo;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import model.bean.BoardingFee;
import model.bean.Invoice;
import model.bean.Parents;
import model.bean.Student;
import model.dao.BoardingFeeDAO;
import model.dao.InvoiceDAO;
import model.dao.StudentDAO;
import model.dto.BoardingFeeAdminResponse;
import model.dto.BoardingFeeResponse;

public class BoardingFeeBO {
	private static BoardingFeeBO _instance;
	private BoardingFeeBO() {}
	
	public static BoardingFeeBO getInstance() {
		if (_instance == null) 
			_instance = new BoardingFeeBO();
		return _instance;
	}
	
	private final BoardingFeeDAO boardingFeeDAO = BoardingFeeDAO.getInstance();
	private final InvoiceDAO invoiceDAO = InvoiceDAO.getInstance();
	private final StudentDAO studentDAO = StudentDAO.getInstance();
	
	public boolean insert(BoardingFee t) {
		return boardingFeeDAO.insert(t);
	}

	public boolean delete(BoardingFee t) {
		return boardingFeeDAO.delete(t);
	}

	public boolean update(BoardingFee t) {
		return boardingFeeDAO.update(t);
	}

	public List<BoardingFee> selectAll() {
		return boardingFeeDAO.selectAll();
	}

	public BoardingFee selectById(BoardingFee t) {
		return boardingFeeDAO.selectById(t);
	}

	public BoardingFee selectById(int t) {
		return boardingFeeDAO.selectById(t);
	}

	public BoardingFeeAdminResponse getResponseById(int boardingFeeId) {
		BoardingFee boardingFee = boardingFeeDAO.selectByEndMonth(boardingFeeId);
		long payedMoney = invoiceDAO.getPayedMoneyOfBoardingFee(boardingFee.getBoardingFee_id());
		int payedStudent = invoiceDAO.getPayedStudentOfBoardingFee(boardingFee.getBoardingFee_id());
		long totalMoney = invoiceDAO.getTotalMoneyOfBoardingFee(boardingFee.getBoardingFee_id());
		int nonPrintedInvoices = invoiceDAO.getNonPrintedInvoicesByBoardingFeeId(boardingFee.getBoardingFee_id());
		return new BoardingFeeAdminResponse(
			boardingFee.getBoardingFee_id(),
			boardingFee.getStart_day().toLocalDate(),
			boardingFee.getEnd_day().toLocalDate(),
			boardingFee.getMainCosts(),
			boardingFee.getSubCosts(),
			boardingFee.getNumberDays(),
			payedMoney,
			payedStudent,
			nonPrintedInvoices,
			totalMoney
		);
	}

	public BoardingFee selectByEndMonth(int endMonth) {
		return boardingFeeDAO.selectByEndMonth(endMonth);
	}

	public boolean existsByMonth(int month) {
		return boardingFeeDAO.selectByEndMonth(month) != null;
	}

	public void createNewFee(BoardingFee boardingFee) {

	}
	
	private final InvoiceBO invoiceBO = InvoiceBO.getInstance();
	
	public ArrayList<BoardingFeeResponse> boardingFee(List<Student> listStudents, int id) {
		ArrayList<BoardingFeeResponse> listBoardingFees = new ArrayList<>();
		List<Integer> listInvoices = invoiceBO.selectByBoardingFeeId(id);
		for (Student std : listStudents) {
			List<Integer> invoices = invoiceBO.selectByStudentId(std.getStudent_id());
			for (int invoice_id : invoices) {
				if (listInvoices.contains(invoice_id)) {
					Invoice invoice = invoiceBO.selectById(invoice_id);
					BoardingFeeResponse boardingFee = new BoardingFeeResponse(std.getStudent_id(),
							std.getName(), invoice.getInvoice_id(), invoice.getStatusPayment(), id);
					listBoardingFees.add(boardingFee);
				}
			}
		}
		return listBoardingFees;
	}
	
	public void updateBoardingFee(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	
	public List<BoardingFeeResponse> timeEating(Parents parent) {
		List<BoardingFeeResponse> listBoardingFeeResponses = new ArrayList<BoardingFeeResponse>();
		for (int i = 0; i < parent.getStudent_id().size(); i++) {
			Student student = studentDAO.selectById(parent.getStudent_id().get(i));
			List<Integer> result = invoiceBO.selectByStudentId(student.getStudent_id());
			for (int id : result) {
				Invoice iv = invoiceBO.selectById(id);
				BoardingFee bfee = boardingFeeDAO.selectById(iv.getBoardingFee_id());
				LocalDate date = bfee.getStart_day().toLocalDate();
				String timeToSubmit = "Tháng " + date.getMonthValue() + " năm " + date.getYear();;
				BoardingFeeResponse boardingFeeResponse = new BoardingFeeResponse(student.getStudent_id(), 
					student.getName(), iv.getInvoice_id(),
					(int)iv.getStatusPayment(), iv.getBoardingFee_id(),
					timeToSubmit, iv.getMoney());
				listBoardingFeeResponses.add(boardingFeeResponse);
			}
		}
		return listBoardingFeeResponses;
	}
}
