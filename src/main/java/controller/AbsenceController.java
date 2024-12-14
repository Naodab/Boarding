package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.bean.Absence;
import model.bo.AbsenceBO;

import java.io.BufferedReader;
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

@WebServlet("/absences")
public class AbsenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AbsenceBO absenceBO = AbsenceBO.getInstance();
       
    public AbsenceController() {
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
			case "updateAbsent":
				updateAbsent(request, response);
				break;
		}
	}

	private void adminHandler(HttpServletRequest request, HttpServletResponse response) {

	}
	
	private void parentsHandler(HttpServletRequest request, HttpServletResponse response) {

	}

	private void updateAbsent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray studentIdsArray = jsonObject.getAsJsonArray("studentIds");
		List<String> studentIdList = new ArrayList<>();
		studentIdsArray.forEach(jsonElement -> studentIdList.add(jsonElement.getAsString()));
		LocalDate absentDate = LocalDate.now();
		System.out.println(studentIdList.size());
		for (int i = 0; i < studentIdList.size(); i++) {
			System.out.println("bwts đầu vonng for!");
			int studentId = Integer.parseInt(studentIdList.get(i));
			Absence absence = absenceBO.selectByStudentIdAndAbsenceDate(studentId, Date.valueOf(absentDate));
			if (absence != null) {
				//dùng trong trường hợp giáo viên đã điểm danh rồi nhưng mà muốn điểm danh lại thì cập nhật hoặc xóa!
			} else {
				absence = new Absence();
				absence.setDayOfAbsence(Date.valueOf(absentDate));
				absence.setStudent_id(studentId);
				absenceBO.insert(absence);
			}
		}
	}
}
