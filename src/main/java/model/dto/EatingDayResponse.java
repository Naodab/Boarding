package model.dto;

import java.sql.Date;
import java.util.List;

public class EatingDayResponse {
	private Date eatingDay;
	private List<String> mainMeals;
	private List<String> subMeals;
	
	public EatingDayResponse() {}
	
	public EatingDayResponse(Date eatingDay, List<String> mainMeals, List<String> subMeals) {
		this.eatingDay = eatingDay;
		this.mainMeals = mainMeals;
		this.subMeals = subMeals;
	}
	
	public Date getEatingDay() {
		return eatingDay;
	}
	
	public void setEatingDay(Date eatingDay) {
		this.eatingDay = eatingDay;
	}
	
	public List<String> getMainMeals() {
		return mainMeals;
	}
	
	public void setMainMeals(List<String> mainMeals) {
		this.mainMeals = mainMeals;
	}
	
	public List<String> getSubMeals() {
		return subMeals;
	}
	
	public void setSubMeals(List<String> subMeals) {
		this.subMeals = subMeals;
	}
}
