package model.bo;

import java.util.List;

import model.bean.Food;
import model.dao.FoodDAO;

public class FoodBO {
	private static FoodBO _instance;
	private FoodBO() {}
	
	public static FoodBO getInstance() {
		if (_instance == null) 
			_instance = new FoodBO();
		return _instance;
	}
	
	private FoodDAO foodDAO = FoodDAO.getInstance();
	
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
}
