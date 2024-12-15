package model.dto;

import java.time.LocalDate;

public class EatingHistoryResponse {
    private int eatingHistoryId;
    private LocalDate eatingDay;
    private int menuId;
    private String mainFoods;
    private String subFood;

    public EatingHistoryResponse(int eatingHistoryId, LocalDate eatingDay, int menuId, String mainFoods, String subFood) {
        this.eatingHistoryId = eatingHistoryId;
        this.eatingDay = eatingDay;
        this.menuId = menuId;
        this.mainFoods = mainFoods;
        this.subFood = subFood;
    }

    public EatingHistoryResponse() {}

    public int getEatingHistoryId() {
        return eatingHistoryId;
    }

    public void setEatingHistoryId(int eatingHistoryId) {
        this.eatingHistoryId = eatingHistoryId;
    }

    public LocalDate getEatingDay() {
        return eatingDay;
    }

    public void setEatingDay(LocalDate eatingDay) {
        this.eatingDay = eatingDay;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMainFoods() {
        return mainFoods;
    }

    public void setMainFoods(String mainFoods) {
        this.mainFoods = mainFoods;
    }

    public String getSubFood() {
        return subFood;
    }

    public void setSubFood(String subFood) {
        this.subFood = subFood;
    }
}
