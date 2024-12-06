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

	private ParentsDAO parentsDAO = ParentsDAO.getInstance();

	public boolean insert(Parents t) {
		return parentsDAO.insert(t);
	}

	public boolean delete(Parents t) {
		return parentsDAO.delete(t);
	}

	public boolean deleteByID(int t) {
		return parentsDAO.deleteByID(t);
	}

	public boolean update(Parents t) {
		return parentsDAO.update(t);
	}

	public List<Parents> selectAll() {
		return parentsDAO.selectAll();
	}

	public Parents selectById(Parents t) {
		return parentsDAO.selectById(t);
	}

	public Parents selectById(int t) {
		return parentsDAO.selectById(t);
	}

}
