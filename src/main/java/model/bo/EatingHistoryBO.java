package model.bo;

import java.util.List;

import model.bean.EatingHistory;
import model.bean.Food;
import model.bean.Menu;
import model.dao.EatingHistoryDAO;
import model.dao.FoodDAO;
import model.dao.MenuDAO;
import model.dto.EatingHistoryResponse;
import model.dto.SearchResponse;

public class EatingHistoryBO {
	private static EatingHistoryBO _instance;
	private EatingHistoryBO() {}
	
	public static EatingHistoryBO getInstance() {
		if (_instance == null) 
			_instance = new EatingHistoryBO();
		return _instance;
	}
	
	private final EatingHistoryDAO eatingHistoryDAO = EatingHistoryDAO.getInstance();
	private final MenuDAO menuDAO = MenuDAO.getInstance();
	private final FoodDAO foodDAO = FoodDAO.getInstance();
	
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

	public List<Integer> getMonthsValid() {
		return eatingHistoryDAO.getMonthsValid();
	}

	public SearchResponse<EatingHistoryResponse> getPages(int page, int amount, int month,
		String searchField, String search) {
		return new SearchResponse<>(eatingHistoryDAO.count(month, searchField, search),
			eatingHistoryDAO.getPages(page, amount, month, searchField, search).stream()
				.map(eatingHistory -> {
					EatingHistoryResponse response = toEatingHistoryResponse(eatingHistory);
					Menu menu = menuDAO.selectById(response.getMenuId());
					String mainFoods = "", subFoods = "";
					for (int foodId : menu.getFood_ids()) {
						Food food = foodDAO.selectById(foodId);
						if (food.getCategory())
							mainFoods += food.getName() + ", ";
						else
							subFoods += food.getName() + ", ";
					}
					mainFoods = mainFoods.substring(0, mainFoods.length() - 2);
					subFoods = subFoods.substring(0, subFoods.length() - 2);
					response.setMainFoods(mainFoods);
					response.setSubFood(subFoods);
					return response;
				}).toList());
	}

	private EatingHistoryResponse toEatingHistoryResponse(EatingHistory t) {
		return new EatingHistoryResponse(t.getEatingHistory_id(), t.getEating_day().toLocalDate(),
				t.getMenu_id(), null, null);
	}
}
