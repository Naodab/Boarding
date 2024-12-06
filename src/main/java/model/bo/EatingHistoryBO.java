package model.bo;

import java.util.List;

import model.bean.EatingHistory;
import model.dao.EatingHistoryDAO;

public class EatingHistoryBO {
	private static EatingHistoryBO _instance;
	private EatingHistoryBO() {}
	
	public static EatingHistoryBO getInstance() {
		if (_instance == null) 
			_instance = new EatingHistoryBO();
		return _instance;
	}
	
	private EatingHistoryDAO dao = EatingHistoryDAO.getInstance();
	
	public boolean insert(EatingHistory t) {
		return dao.insert(t);
	}

	public boolean delete(EatingHistory t) {
		return dao.delete(t);
	}

	public boolean update(EatingHistory t) {
		return dao.update(t);
	}

	public List<EatingHistory> selectAll() {
		return dao.selectAll();
	}

	public EatingHistory selectById(EatingHistory t) {
		return dao.selectById(t);
	}

	public EatingHistory selectById(int t) {
		return dao.selectById(t);
	}

	public List<Integer> selectByMenu_id(int menu_id) {
		return dao.selectByMenu_id(menu_id);
	}

	public List<Integer> selectByBoardingFee_id(int boardingFee_id) {
		return dao.selectByBoardingFee_id(boardingFee_id);
	}
}
