package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.bean.Absence;
import model.bean.Invoice;
import model.bean.Student;
import model.bean.Teacher;
import model.bean.User;
import model.bo.*;
import model.dto.NameAndIdResponse;
import model.dto.PreAddTeacherResponse;
import model.dto.SearchResponse;
import model.dto.TeacherResponse;
import util.AdminUtil;
import util.LocalDateAdapter;
import model.dto.AbsenceResponse;
import model.dto.BoardingFeeResponse;

@WebServlet("/teachers")
public class TeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
	private final TeacherBO teacherBO = TeacherBO.getInstance();
	private final UserBO userBO = UserBO.getInstance();
	private final GlobalBO globalBO = GlobalBO.getInstance();
	private final BoardingClassBO boardingClassBO = BoardingClassBO.getInstance();
	private final AbsenceBO absenceBO = AbsenceBO.getInstance();
	private final InvoiceBO invoiceBO = InvoiceBO.getInstance();

    public TeacherController() {
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

	private void teacherHandler(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = (String)request.getSession().getAttribute("username");
		Teacher teacher = TeacherBO.getInstance().selectByUsername(username);
		String mode = request.getParameter("mode");
		RequestDispatcher rd = null;
		String destination = "";
		switch(mode) {
			case "teacherInfor":
				request.setAttribute("teacherInfor", teacher);
				request.setAttribute("teachClass", BoardingClassBO.getInstance()
						.selectById(teacher.getBoardingClass_id()).getName());
				destination = "/teachers/teacherInfor.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "updateTeacherInfor":
				updateTeacherInfor(request, response, teacher);
				break;
			case "studentInfor":
				List<Student> listStudents = StudentBO.getInstance()
						.selectByBoardingClass_id2(teacher.getBoardingClass_id());
				request.setAttribute("listStudents", listStudents);
				destination = "/teachers/studentInfor.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "boardingFee":
				listStudents = StudentBO.getInstance().selectByBoardingClass_id2(teacher.getBoardingClass_id());
				int numberOfItems = globalBO.getSizeOf("boardingFee", "");
				request.setAttribute("numberOfItems", numberOfItems);
				ArrayList<BoardingFeeResponse> listBoardingFees;
				if (request.getParameter("boardingFeeId") != null) {
					int boardingFeeId = Integer.parseInt(request.getParameter("boardingFeeId"));
					listBoardingFees = boardingFee(request, response, listStudents, boardingFeeId);
				} else {
					listBoardingFees = boardingFee(request, response, listStudents, 1);
				}
				request.setAttribute("listBoardingFees", listBoardingFees);
				destination = "/teachers/boardingFee.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "eatingDay":
				break;
			case "changeToPhysical":
				listStudents = StudentBO.getInstance().selectByBoardingClass_id2(teacher.getBoardingClass_id());
				request.setAttribute("listStudents", listStudents);
				destination = "/teachers/physicalStudents.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
			case "changeToAbsent":
				listStudents = StudentBO.getInstance().selectByBoardingClass_id2(teacher.getBoardingClass_id());
				ArrayList<AbsenceResponse> listAbsences = new ArrayList<AbsenceResponse>();
				for (int i = 0; i < listStudents.size(); i++) {
					Absence absence = absenceBO.selectByStudentIdAndAbsenceDate(listStudents
							.get(i).getStudent_id(), Date.valueOf(LocalDate.now()));
					AbsenceResponse absenceInfo = new AbsenceResponse();
					if (absence != null) {
						absenceInfo = new AbsenceResponse(listStudents.get(i).getStudent_id(),
								listStudents.get(i).getName(), true, absence.getDayOfAbsence()
								.toLocalDate(), absence.getAbsence_id());
					} else {
						absenceInfo = new AbsenceResponse(listStudents.get(i).getStudent_id(),
								listStudents.get(i).getName(), false, null, 0);
					}
					listAbsences.add(absenceInfo);
				}
				request.setAttribute("listAbsences", listAbsences);
				destination = "/teachers/absentStudents.jsp";
				rd = getServletContext().getRequestDispatcher(destination);
				rd.forward(request, response);
				break;
		}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		String destination = "/admin/teachers.jsp";
		String mode = request.getParameter("mode");
		if (mode != null) {
            int TEACHERS_PER_PAGE = AdminUtil.ITEMS_PER_PAGE;
            switch (mode) {
				case "see":
					String search = request.getParameter("search");
					String searchField = request.getParameter("searchField");
					String sort = request.getParameter("sort");
					String sortField = request.getParameter("sortField");
					int page = Integer.parseInt(request.getParameter("page"));
					SearchResponse<TeacherResponse> result = teacherBO.search(page,
							TEACHERS_PER_PAGE, searchField, search, sortField, sort);
					response.getWriter().print(gson.toJson(result));
					return;
				case "add":
					Teacher teacherAdd = getTeacherFromRequest(request);
					userBO.insert(new User(teacherAdd.getPhoneNumber(), null,
							"Teacher", true, Date.valueOf(LocalDate.now())));
					teacherBO.insert(teacherAdd);
					response.sendRedirect(request.getContextPath() + "/teachers");
					return;
				case "preAdd":
					int nextId = globalBO.getAuto_IncrementOf("teacher");
					List<NameAndIdResponse> classesResponse = boardingClassBO.getNameAndIds();
					PreAddTeacherResponse responsePreAdd = new PreAddTeacherResponse(nextId,
							classesResponse);
					response.getWriter().print(gson.toJson(responsePreAdd));
					response.flushBuffer();
					return;
				case "delete":
					int teacherIdDelete = Integer.parseInt(request.getParameter("teacher_id"));
					Teacher teacherDelete = teacherBO.selectById(teacherIdDelete);
					userBO.deleteByUsername(teacherDelete.getPhoneNumber());
					teacherBO.deleteById(teacherIdDelete);
					response.setStatus(HttpServletResponse.SC_OK);
					return;
				case "update":
					Teacher teacherUpdate = getTeacherFromRequest(request);
					Teacher teacherBefore = teacherBO.selectById(teacherUpdate.getTeacher_id());
					if (!teacherBefore.getPhoneNumber().equals(teacherUpdate.getPhoneNumber())) {
						userBO.updateUsername(teacherBefore.getPhoneNumber(),
								teacherUpdate.getPhoneNumber());
					}
					teacherBO.update(teacherUpdate);
					response.sendRedirect(request.getContextPath() + "/teachers");
					return;
				case "preUpdate":
					List<NameAndIdResponse> preUpdateClasses = boardingClassBO.getNameAndIds();
					response.getWriter().print(gson.toJson(preUpdateClasses));
					response.flushBuffer();
					return;
			}
		}
		getServletContext().getRequestDispatcher(destination).forward(request, response);
	}

	private Teacher getTeacherFromRequest(HttpServletRequest request)
			throws UnsupportedEncodingException {
		int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phone");
		String email = request.getParameter("email");
		boolean sex = request.getParameter("sex").equals("Nam");
		Date dateOfBirth = Date.valueOf(LocalDate.parse(request.getParameter("dateOfBirth")));
		int boardingClass_id = Integer.parseInt(request.getParameter("boardingClass_id"));
		return new Teacher(name, dateOfBirth, address, sex,
				teacher_id, phoneNumber, email, boardingClass_id);
	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private void updateTeacherInfor(HttpServletRequest request,
		 HttpServletResponse response, Teacher teacher) throws IOException {
		 StringBuilder sb = new StringBuilder();
	     String line;
	     try (BufferedReader reader = request.getReader()) {
	     while ((line = reader.readLine()) != null) {
	    	 sb.append(line);
	       	}
	     }
	     String jsonData = sb.toString();
	     String address = extractValue(jsonData, "address");
	     String email = extractValue(jsonData, "email");
	     String phoneNumber = extractValue(jsonData, "phoneNumber");
	     teacher.setAddress(address);
	     teacher.setEmail(email);
	     teacher.setPhoneNumber(phoneNumber);
	     TeacherBO.getInstance().update(teacher);
	}
	
	private ArrayList<BoardingFeeResponse> boardingFee(HttpServletRequest request,
	    HttpServletResponse response, List<Student> listStudents, int id) {
		ArrayList<BoardingFeeResponse> listBoardingFees = new ArrayList<BoardingFeeResponse>();
		List<Integer> listInvoices = invoiceBO.selectByBoardingFeeId(id);
		for (Student std : listStudents) {
			List<Integer> invoices = invoiceBO.selectByStudentId(std.getStudent_id());
			for (int invoice_id : invoices) {
				if (listInvoices.contains(invoice_id)) {
					Invoice invoice = invoiceBO.selectById(invoice_id);
					BoardingFeeResponse boardingFee = new BoardingFeeResponse(std.getStudent_id(),
							std.getName(), invoice.getInvoice_id(), invoice.getStatusPayment());
					listBoardingFees.add(boardingFee);
				}
			}
		}
		return listBoardingFees;
	}

	private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
