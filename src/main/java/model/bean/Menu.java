package model.bean;

import java.util.List;

public class Menu {
	private int menu_id;
	private boolean active;
	private List<Integer> food_ids;
	private List<Integer> eatingHistory_ids;

	public Menu() {
		super();
	}

	public Menu(int menu_id, boolean active, List<Integer> food_ids, List<Integer> eatingHistory_ids) {
		this.menu_id = menu_id;
		this.active = active;
		this.food_ids = food_ids;
		this.eatingHistory_ids = eatingHistory_ids;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Integer> getFood_ids() {
		return food_ids;
	}

	public void setFood_ids(List<Integer> food_ids) {
		this.food_ids = food_ids;
	}

	public List<Integer> getEatingHistory_ids() {
		return eatingHistory_ids;
	}

	public void setEatingHistory_ids(List<Integer> eatingHistory_ids) {
		this.eatingHistory_ids = eatingHistory_ids;
	}

}
