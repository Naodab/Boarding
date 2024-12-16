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
	
	private EatingHistoryDAO eatingHistoryDAO = EatingHistoryDAO.getInstance();
	
	public boolean insert(EatingHistory t) {
		return eatingHistoryDAO.insert(t);
	}

	public boolean delete(EatingHistory t) {
		return eatingHistoryDAO.delete(t);
	}

	public boolean update(EatingHistory t) {
		return eatingHistoryDAO.update(t);
	}

	public List<EatingHistory> selectAll() {
		return eatingHistoryDAO.selectAll();
	}

	public EatingHistory selectById(EatingHistory t) {
		return eatingHistoryDAO.selectById(t);
	}

	public EatingHistory selectById(int t) {
		return eatingHistoryDAO.selectById(t);
	}

	public List<Integer> selectByMenu_id(int menu_id) {
		return eatingHistoryDAO.selectByMenu_id(menu_id);
	}

	public List<Integer> selectByBoardingFee_id(int boardingFee_id) {
		return eatingHistoryDAO.selectByBoardingFee_id(boardingFee_id);
	}
	
	public List<EatingHistory> selectBetweenDays(String startDay, String endDay) {
		return eatingHistoryDAO.selectBetweenDays(startDay, endDay);
	}
}
