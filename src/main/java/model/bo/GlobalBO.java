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

	private GlobalDAO dao = GlobalDAO.getInstance();

	public List<String> search(String table, String column, String condition, int amount) {
		return dao.search(table, column, condition, amount);
	}

	public List<String> sort(String table, String column, String condition, int amount, String isDESC) {
		return dao.sort(table, column, condition, amount, isDESC);
	}

	public int getSizeOf(String table, String condition) {
		return dao.getSizeOf(table, condition);
	}

	public int getLastIDOf(String table) {
		return dao.getLastIDOf(table);
	}

	public int getFirstIDOf(String table) {
		return dao.getFirstIDOf(table);
	}

	public boolean updateNULLForeignKey(String table, String column, String condition) {
		return dao.updateNULLForeignKey(table, column, condition);
	}

	public int getAuto_IncrementOf(String table) {
		return dao.getAuto_IncrementOf(table);
	}

	public List<String> getSortNumberOfStudents(String condition, int amount, String isDESC) {
		return dao.getSortNumberOfStudents(condition, amount, isDESC);
	}

	public List<String> getSortNumberOfMenu(String condition, int amount, String isDESC) {
		return dao.getSortNumberOfMenu(condition, amount, isDESC);
	}

	public List<String> searchMainFoodInMenu(String condition) {
		return dao.searchMainFoodInMenu(condition);
	}

	public List<String> searchSubFoodInMenu(String condition) {
		return dao.searchSubFoodInMenu(condition);
	}

}
