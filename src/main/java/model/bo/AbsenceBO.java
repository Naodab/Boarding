package model.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.bean.Absence;
import model.bean.Student;
import model.dao.AbsenceDAO;
import model.dto.AbsenceResponse;

public class AbsenceBO {
	private static AbsenceBO _instance;
	private AbsenceBO() {}
	
	public static AbsenceBO getInstance() {
		if (_instance == null) 
			_instance = new AbsenceBO();
		return _instance;
	}
	
	private AbsenceDAO absenceDAO = AbsenceDAO.getInstance();
	
	public Absence checkExist(int t, Date time) {
		return absenceDAO.checkExist(t, time);
	}

	public boolean insert(Absence t) {
		return absenceDAO.insert(t);
	}

	public boolean delete(Absence t) {
		return absenceDAO.delete(t);
	}

	public boolean deleteById(int t) {
		return absenceDAO.deleteById(t);
	}

	public boolean update(Absence t) {
		return absenceDAO.update(t);
	}

	public List<Absence> selectAll() {
		return absenceDAO.selectAll();
	}

	public Absence selectById(Absence t) {
		return absenceDAO.selectById(t);
	}

	public Absence selectById(int t) {
		return absenceDAO.selectById(t);
	}

	public List<Integer> selectByStudentId(int student_id) {
		return absenceDAO.selectByStudentId(student_id);
	}

	public Absence selectByStudentIdAndAbsenceDate(int student_id, Date absence_date) { return absenceDAO.selectByStudentIdAndAbsenceDate(student_id, absence_date); }
	
	public ArrayList<AbsenceResponse> listAbsences(List<Student> listStudents) {
		ArrayList<AbsenceResponse> listAbsences = new ArrayList<AbsenceResponse>();
		for (int i = 0; i < listStudents.size(); i++) {
			Absence absence = selectByStudentIdAndAbsenceDate(listStudents
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
		return listAbsences;
	}
	
	public void updateAbsent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray studentIdsArray = jsonObject.getAsJsonArray("studentIds");
		List<String> studentIdList = new ArrayList<>();
		studentIdsArray.forEach(jsonElement -> studentIdList.add(jsonElement.getAsString()));
		LocalDate absentDate = LocalDate.now();
		for (int i = 0; i < studentIdList.size(); i++) {
			int studentId = Integer.parseInt(studentIdList.get(i));
			Absence absence = selectByStudentIdAndAbsenceDate(studentId, Date.valueOf(absentDate));
			if (absence != null) {
				//dùng trong trường hợp giáo viên đã điểm danh rồi nhưng mà muốn điểm danh lại thì cập nhật hoặc xóa!
			} else {
				absence = new Absence();
				absence.setDayOfAbsence(Date.valueOf(absentDate));
				absence.setStudent_id(studentId);
				insert(absence);
			}
		}
	}
}
