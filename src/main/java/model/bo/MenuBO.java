package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.bean.Food;
import model.bean.Menu;
import model.dao.FoodDAO;
import model.dao.MenuDAO;
import model.dto.MenuAdminResponse;
import model.dto.SearchResponse;

public class MenuBO {
	private static MenuBO _instance;

	private MenuBO() {
	}

	public static MenuBO getInstance() {
		if (_instance == null)
			_instance = new MenuBO();
		return _instance;
	}

	private final MenuDAO menuDAO = MenuDAO.getInstance();
	private final FoodDAO foodDAO = FoodDAO.getInstance();

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

	public SearchResponse<MenuAdminResponse> getPage(int page, int amount,
    	String searchField, String search) {
		return new SearchResponse<>(menuDAO.count(searchField, search),
			menuDAO.getPage(page, amount, searchField, search).stream()
				.map(menu -> {
					MenuAdminResponse response = toMenuResponse(menu);
					List<String> mainFoods = new ArrayList<>();
					String subFood = "";
					for (int food_id : menu.getFood_ids()) {
						Food food = foodDAO.selectById(food_id);
						if (food.getCategory()) {
							mainFoods.add(food.getName());
						} else {
							subFood = food.getName();
						}
					}
					response.setMainFoods(mainFoods);
					response.setSubFood(subFood);
					return response;
				}).toList());
	}

	private MenuAdminResponse toMenuResponse(Menu t) {
		return new MenuAdminResponse(t.getMenu_id(), t.isActive());
	}
}
