package model.bo;

import java.util.List;

import model.bean.Food;
import model.dao.FoodDAO;
import model.dto.FoodAdminResponse;
import model.dto.NameAndIdResponse;
import model.dto.SearchResponse;

public class FoodBO {
	private static FoodBO _instance;
	private FoodBO() {}
	
	public static FoodBO getInstance() {
		if (_instance == null) 
			_instance = new FoodBO();
		return _instance;
	}
	
	private final FoodDAO foodDAO = FoodDAO.getInstance();
	
	public boolean insert(Food t) {
		return foodDAO.insert(t);
	}

	public boolean delete(Food t) {
		return foodDAO.delete(t);
	}

	public boolean update(Food t) {
		return foodDAO.update(t);
	}

	public List<Food> selectAll() {
		return foodDAO.selectAll();
	}

	public Food selectById(Food t) {
		return foodDAO.selectById(t);
	}

	public Food selectById(int t) {
		return foodDAO.selectById(t);
	}

	public List<Food> selectByCategory(boolean category) {
		return foodDAO.selectByCategory(category);
	}

	public SearchResponse<FoodAdminResponse> getPage(int page, int amount, String searchField, String search) {
		return new SearchResponse<>(foodDAO.count(searchField, search),
			foodDAO.getPages(page, amount, searchField, search).stream()
				.map(food -> {
					FoodAdminResponse response = toFoodResponse(food);
					Food foodOld = foodDAO.selectById(food.getFood_id());
					response.setCanDelete(foodOld.getMenu_ids().isEmpty());
					response.setCategory(foodOld.getCategory());
					return response;
				}).toList());
	}

	public boolean existsByName(String name) {
		return foodDAO.existByName(name);
	}

	public List<NameAndIdResponse> getNameAndIds() {
		return foodDAO.selectAll().stream().map(food ->
				new NameAndIdResponse(food.getFood_id(),
						food.getName())).toList();
	}

	public List<NameAndIdResponse> getNameAndIdOfCategory(boolean category) {
		return foodDAO.selectByCategory(category).stream().map(food ->
				new NameAndIdResponse(food.getFood_id(),
						food.getName())).toList();
	}

	private FoodAdminResponse toFoodResponse(Food food) {
		return new FoodAdminResponse(food.getFood_id(), food.getName(),
				food.getCategory(), food.getMenu_ids().isEmpty());
	}
}
