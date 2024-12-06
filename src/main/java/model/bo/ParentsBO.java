package model.bo;

import java.util.List;

import model.bean.Parents;
import model.dao.ParentsDAO;

public class ParentsBO {
	private static ParentsBO _instance;

	private ParentsBO() {
	}

	public static ParentsBO getInstance() {
		if (_instance == null)
			_instance = new ParentsBO();
		return _instance;
	}

	private ParentsDAO dao = ParentsDAO.getInstance();

	public boolean insert(Parents t) {
		return dao.insert(t);
	}

	public boolean delete(Parents t) {
		return dao.delete(t);
	}

	public boolean deleteByID(int t) {
		return dao.deleteByID(t);
	}

	public boolean update(Parents t) {
		return dao.update(t);
	}

	public List<Parents> selectAll() {
		return dao.selectAll();
	}

	public Parents selectById(Parents t) {
		return dao.selectById(t);
	}

	public Parents selectById(int t) {
		return dao.selectById(t);
	}

}
