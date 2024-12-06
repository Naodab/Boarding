package model.bean;

import java.util.List;

public class Food {
	private int food_id;
	private String name;
	private boolean category;
	private List<Integer> menu_ids;

	public Food() {
		super();
	}

	public Food(int food_id, String name, boolean category, List<Integer> menu_ids) {
		super();
		this.food_id = food_id;
		this.name = name;
		this.category = category;
		this.menu_ids = menu_ids;
	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getCategory() {
		return category;
	}

	public void setCategory(boolean category) {
		this.category = category;
	}

	public List<Integer> getMenu_ids() {
		return menu_ids;
	}

	public void setMenu_ids(List<Integer> menu_ids) {
		this.menu_ids = menu_ids;
	}
}
