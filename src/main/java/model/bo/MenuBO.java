package model.bo;

import java.util.List;

import model.bean.Menu;
import model.dao.MenuDAO;

public class MenuBO {
	private static MenuBO _instance;

	private MenuBO() {
	}

	public static MenuBO getInstance() {
		if (_instance == null)
			_instance = new MenuBO();
		return _instance;
	}

	private MenuDAO menuDAO = MenuDAO.getInstance();

	public boolean insert(Menu t) {
		return menuDAO.insert(t);
	}

	public boolean delete(Menu t) {
		return menuDAO.delete(t);
	}

	public boolean update(Menu t) {
		return menuDAO.update(t);
	}

	public boolean updateActive(int menu_id, boolean active) {
		return menuDAO.updateActive(menu_id, active);
	}

	public List<Menu> selectAll() {
		return menuDAO.selectAll();
	}

	public Menu selectById(Menu t) {
		return menuDAO.selectById(t);
	}

	public Menu selectById(int t) {
		return menuDAO.selectById(t);
	}

}
