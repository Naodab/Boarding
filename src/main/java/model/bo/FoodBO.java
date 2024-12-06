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
	
	private FoodDAO dao = FoodDAO.getInstance();
	
	public boolean insert(Food t) {
		return dao.insert(t);
	}

	public boolean delete(Food t) {
		return dao.delete(t);
	}

	public boolean update(Food t) {
		return dao.update(t);
	}

	public List<Food> selectAll() {
		return dao.selectAll();
	}

	public Food selectById(Food t) {
		return dao.selectById(t);
	}

	public Food selectById(int t) {
		return dao.selectById(t);
	}

	public List<Food> selectByCategory(boolean category) {
		return dao.selectByCategory(category);
	}
}
