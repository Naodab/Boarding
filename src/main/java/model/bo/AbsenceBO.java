package model.bo;

import java.sql.Date;
import java.util.List;

import model.bean.Absence;
import model.dao.AbsenceDAO;

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
}
