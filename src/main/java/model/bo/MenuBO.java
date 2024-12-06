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

	private MenuDAO dao = MenuDAO.getInstance();

	public boolean insert(Menu t) {
		return dao.insert(t);
	}

	public boolean delete(Menu t) {
		return dao.delete(t);
	}

	public boolean update(Menu t) {
		return dao.update(t);
	}

	public boolean updateActive(int menu_id, boolean active) {
		return dao.updateActive(menu_id, active);
	}

	public List<Menu> selectAll() {
		return dao.selectAll();
	}

	public Menu selectById(Menu t) {
		return dao.selectById(t);
	}

	public Menu selectById(int t) {
		return dao.selectById(t);
	}

}
