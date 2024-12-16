package model.bo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bean.EatingHistory;
import model.bean.Food;
import model.bean.Menu;
import model.bean.Parents;
import model.dao.*;
import model.dto.EatingDayResponse;
import model.dto.NameAndIdResponse;
import model.dto.ParentsResponse;
import model.dto.SearchResponse;

public class ParentsBO {
	private static ParentsBO _instance;

	private ParentsBO() {
	}

	public static ParentsBO getInstance() {
		if (_instance == null)
			_instance = new ParentsBO();
		return _instance;
	}

	private final ParentsDAO parentsDAO = ParentsDAO.getInstance();
	private final StudentDAO studentDAO = StudentDAO.getInstance();
	private final EatingHistoryDAO eatingHistoryDAO = EatingHistoryDAO.getInstance();
	private final GlobalDAO globalDAO = GlobalDAO.getInstance();
	private final MenuDAO menuDAO = MenuDAO.getInstance();
	private final FoodDAO foodDAO = FoodDAO.getInstance();

	public boolean insert(Parents t) {
		return parentsDAO.insert(t);
	}

	public boolean delete(Parents t) {
		return parentsDAO.delete(t);
	}

	public boolean deleteByID(int t) {
		return parentsDAO.deleteByID(t);
	}

	public boolean update(Parents t) {
		return parentsDAO.update(t);
	}

	public List<Parents> selectAll() {
		return parentsDAO.selectAll();
	}

	public Parents selectById(Parents t) {
		return parentsDAO.selectById(t);
	}

	public Parents selectById(int t) {
		return parentsDAO.selectById(t);
	}
	
	public List<NameAndIdResponse> getNameAndIds() {
		List<Parents> parents = parentsDAO.selectAll();
		List<NameAndIdResponse> result = new ArrayList<NameAndIdResponse>();
		for (Parents parent : parents) {
			result.add(new NameAndIdResponse(parent.getParents_id(), parent.getName()));
		}
		return result;
	}

	public List<ParentsResponse> getPageParents(int page, int amount, String searchField, String search,
												String sortField, String sortType) {
		return parentsDAO.getPageParents(page, amount, searchField, search, sortField, sortType)
				.stream().map(parents -> {
					ParentsResponse response = toParentsResponse(parents);
					response.setNumberChildren(studentDAO.selectByParents_id(parents.getParents_id()).size());
					return response;
				}).toList();
	}

	public SearchResponse<ParentsResponse> searchResponse(int page, int amount, String searchField, String search,
														  String sortField, String sortType) {
		return new SearchResponse<>(parentsDAO.count(searchField, search),
				getPageParents(page, amount, searchField, search, sortField, sortType));
	}

	public int count(String searchField, String info) {
		return parentsDAO.count(searchField, info);
	}

	public ParentsResponse toParentsResponse(Parents t) {
		ParentsResponse result = new ParentsResponse();
		result.setParents_id(t.getParents_id());
		result.setName(t.getName());
		result.setAddress(t.getAddress());
		result.setEmail(t.getEmail());
		result.setDateOfBirth(t.getDateOfBirth().toLocalDate());
		result.setSex(t.getSex());
		result.setPhone(t.getPhoneNumber());
		return result;
	}
	
	public Parents selectByUsername(String username) {
		return parentsDAO.selectByUsername(username);
	}

	public Map<Integer, String> getListWeek() {
		Map<Integer, String> result = new HashMap<Integer, String>();
		int eH_id = globalDAO.getLastIDOf("eatingHistory");
		EatingHistory last_EH = eatingHistoryDAO.selectById(eH_id);
		EatingHistory start_EH = eatingHistoryDAO.selectById(1);
		LocalDate last_day = last_EH.getEating_day().toLocalDate();
		LocalDate start_day = start_EH.getEating_day().toLocalDate();
		int daysOfFirstWeek = EatingHistory.getDaysOfWeek(start_day.getDayOfWeek());
		int daysOfLastWeek = EatingHistory.getDaysOfWeek(last_day.getDayOfWeek());
		long daysBetween = ChronoUnit.DAYS.between(start_day, last_day) - daysOfLastWeek - (7 - daysOfFirstWeek + 1) + 1;
		long weeks = daysBetween / 7 + 2;
		result.put(1, "Tuần 1 (" + start_day + " - " + start_day.plusDays(5 - daysOfFirstWeek) + ")");
		start_day = start_day.plusDays(7 - daysOfFirstWeek + 1);
		for (int i = 2; i <= weeks - 1; i++) {
			result.put(i, "Tuần " + i + " (" + start_day + " - " + start_day.plusDays(4) + ")");
			start_day = start_day.plusDays(7);
		}
		result.put((int) weeks, "Tuần " + weeks + " (" + start_day + " - " + last_day + ")");
		return result;
	}

	public List<EatingDayResponse> getEatingDay(String timeEating) {
		List<EatingDayResponse> listEatingDayResponses = new ArrayList<EatingDayResponse>();
		int startIndex = timeEating.indexOf('(') + 1;
		int endIndex = timeEating.indexOf(')');
		String dateRange = timeEating.substring(startIndex, endIndex);
		String[] dates = dateRange.split(" - ");
		String startDate = dates[0];
		String endDate = dates[1];
		List<EatingHistory> listEatingHistoryBetweenDays = eatingHistoryDAO.selectBetweenDays(startDate, endDate);
		for (int i = 0; i < listEatingHistoryBetweenDays.size(); i++) {
			Menu menu = menuDAO.selectById(listEatingHistoryBetweenDays.get(i).getMenu_id());
			List<String> mainMeals = new ArrayList<String>();
			List<String> subMeals = new ArrayList<String>();
			for (int foodId : menu.getFood_ids()) {
				Food food = foodDAO.selectById(foodId);
				if (food.getCategory())
					mainMeals.add(food.getName());
				else
					subMeals.add(food.getName());
			}
			if (subMeals.size() == 0) subMeals.add("Không có");
			listEatingDayResponses.add(new EatingDayResponse(listEatingHistoryBetweenDays.get(i).getEating_day(), mainMeals, subMeals));
		}
		return listEatingDayResponses;
	}
}
