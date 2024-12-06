package model.bo;

import java.util.List;

import model.dao.GlobalDAO;

public class GlobalBO {
	private static GlobalBO _instance;

	private GlobalBO() {
	}

	public static GlobalBO getInstance() {
		if (_instance == null)
			_instance = new GlobalBO();
		return _instance;
	}

	private GlobalDAO globalDAO = GlobalDAO.getInstance();

	public List<String> search(String table, String column, String condition, int amount) {
		return globalDAO.search(table, column, condition, amount);
	}

	public List<String> sort(String table, String column, String condition, int amount, String isDESC) {
		return globalDAO.sort(table, column, condition, amount, isDESC);
	}

	public int getSizeOf(String table, String condition) {
		return globalDAO.getSizeOf(table, condition);
	}

	public int getLastIDOf(String table) {
		return globalDAO.getLastIDOf(table);
	}

	public int getFirstIDOf(String table) {
		return globalDAO.getFirstIDOf(table);
	}

	public boolean updateNULLForeignKey(String table, String column, String condition) {
		return globalDAO.updateNULLForeignKey(table, column, condition);
	}

	public int getAuto_IncrementOf(String table) {
		return globalDAO.getAuto_IncrementOf(table);
	}

	public List<String> getSortNumberOfStudents(String condition, int amount, String isDESC) {
		return globalDAO.getSortNumberOfStudents(condition, amount, isDESC);
	}

	public List<String> getSortNumberOfMenu(String condition, int amount, String isDESC) {
		return globalDAO.getSortNumberOfMenu(condition, amount, isDESC);
	}

	public List<String> searchMainFoodInMenu(String condition) {
		return globalDAO.searchMainFoodInMenu(condition);
	}

	public List<String> searchSubFoodInMenu(String condition) {
		return globalDAO.searchSubFoodInMenu(condition);
	}

}
