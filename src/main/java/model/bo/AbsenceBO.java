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
	
	private AbsenceDAO dao = AbsenceDAO.getInstance();
	
	public Absence checkExist(int t, Date time) {
		return dao.checkExist(t, time);
	}

	public boolean insert(Absence t) {
		return dao.insert(t);
	}

	public boolean delete(Absence t) {
		return dao.delete(t);
	}

	public boolean deleteById(int t) {
		return dao.deleteById(t);
	}

	public boolean update(Absence t) {
		return dao.update(t);
	}

	public List<Absence> selectAll() {
		return dao.selectAll();
	}

	public Absence selectById(Absence t) {
		return dao.selectById(t);
	}

	public Absence selectById(int t) {
		return dao.selectById(t);
	}

	public List<Integer> selectByStudentId(int student_id) {
		return dao.selectByStudentId(student_id);
	}
}
