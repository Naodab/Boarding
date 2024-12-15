package model.dto;

import java.util.List;

public class MenuAdminResponse {
    private int menuId;
    private boolean active;
    private List<String> mainFoods;
    private String subFood;

    public MenuAdminResponse() {}

    public MenuAdminResponse(int menuId, boolean active) {
        this.menuId = menuId;
        this.active = active;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getMainFoods() {
        return mainFoods;
    }

    public void setMainFoods(List<String> mainFoods) {
        this.mainFoods = mainFoods;
    }

    public String getSubFood() {
        return subFood;
    }

    public void setSubFood(String subFood) {
        this.subFood = subFood;
    }
}
