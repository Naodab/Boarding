package model.dto;

import java.util.List;

public class PreAddMenu {
    private int nextId;
    private List<NameAndIdResponse> mainFoods;
    private List<NameAndIdResponse> subFoods;

    public PreAddMenu(int nextId, List<NameAndIdResponse> foods, List<NameAndIdResponse> subFoods) {
        this.nextId = nextId;
        this.mainFoods = foods;
        this.subFoods = subFoods;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public List<NameAndIdResponse> getMainFoods() {
        return mainFoods;
    }

    public void setMainFoods(List<NameAndIdResponse> mainFoods) {
        this.mainFoods = mainFoods;
    }

    public List<NameAndIdResponse> getSubFoods() {
        return subFoods;
    }

    public void setSubFoods(List<NameAndIdResponse> subFoods) {
        this.subFoods = subFoods;
    }
}
