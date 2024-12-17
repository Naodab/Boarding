package model.bo;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.bean.*;
import model.dao.*;
import model.dto.EatingHistoryRequest;
import model.dto.EatingHistoryResponse;
import model.dto.SearchResponse;

public class EatingHistoryBO {
	private static EatingHistoryBO _instance;
	private EatingHistoryBO() {}
	
	public static EatingHistoryBO getInstance() {
		if (_instance == null) 
			_instance = new EatingHistoryBO();
		return _instance;
	}
	
	private final EatingHistoryDAO eatingHistoryDAO = EatingHistoryDAO.getInstance();
	private final MenuDAO menuDAO = MenuDAO.getInstance();
	private final FoodDAO foodDAO = FoodDAO.getInstance();
	private final InvoiceDAO invoiceDAO = InvoiceDAO.getInstance();
	private final BoardingFeeDAO boardingFeeDAO = BoardingFeeDAO.getInstance();
	private final StudentDAO studentDAO = StudentDAO.getInstance();
	private final GlobalDAO globalDAO = GlobalDAO.getInstance();

	public static List<LocalDate> getListDateInMonth(int month, int year) {
		List<LocalDate> localDates = new ArrayList<>();
		int[] dayOfMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		for (int i = 1; i <= dayOfMonth[month]; i++) {
			LocalDate eatingDay = LocalDate.of(year, month, i);
			DayOfWeek dow = eatingDay.getDayOfWeek();
			if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
				localDates.add(eatingDay);
			}
		}
		return localDates;
	}


	public boolean insert(EatingHistory t) {
		return eatingHistoryDAO.insert(t);
	}

	public boolean delete(EatingHistory t) {
		return eatingHistoryDAO.delete(t);
	}

	public boolean update(EatingHistory t) {
		return eatingHistoryDAO.update(t);
	}

	public List<EatingHistory> selectAll() {
		return eatingHistoryDAO.selectAll();
	}

	public EatingHistory selectById(EatingHistory t) {
		return eatingHistoryDAO.selectById(t);
	}

	public EatingHistory selectById(int t) {
		return eatingHistoryDAO.selectById(t);
	}

	public List<Integer> selectByMenu_id(int menu_id) {
		return eatingHistoryDAO.selectByMenu_id(menu_id);
	}

	public List<Integer> selectByBoardingFee_id(int boardingFee_id) {
		return eatingHistoryDAO.selectByBoardingFee_id(boardingFee_id);
	}

	public List<Integer> getMonthsValid() {
		return eatingHistoryDAO.getMonthsValid();
	}

	public SearchResponse<EatingHistoryResponse> getPages(int page, int amount, int month,
		String searchField, String search) {
		return new SearchResponse<>(eatingHistoryDAO.count(month, searchField, search),
			eatingHistoryDAO.getPages(page, amount, month, searchField, search).stream()
				.map(this::toEatingHistoryResponse).toList());
	}

	private EatingHistoryResponse toEatingHistoryResponse(EatingHistory t) {
		EatingHistoryResponse response = new EatingHistoryResponse(t.getEatingHistory_id(),
				t.getEating_day().toLocalDate(),
				t.getMenu_id(), null, null);
		Menu menu = menuDAO.selectById(response.getMenuId());
		StringBuilder mainFoods = new StringBuilder();
        StringBuilder subFoods = new StringBuilder();
        for (int foodId : menu.getFood_ids()) {
			Food food = foodDAO.selectById(foodId);
			if (food.getCategory())
				mainFoods.append(food.getName()).append(", ");
			else
				subFoods.append(food.getName()).append(", ");
		}
		mainFoods = new StringBuilder(mainFoods.substring(0, mainFoods.length() - 2));
		subFoods = new StringBuilder(subFoods.substring(0, subFoods.length() - 2));
		response.setMainFoods(mainFoods.toString());
		response.setSubFood(subFoods.toString());
		return response;
	}

	public SearchResponse<EatingHistoryResponse> createFee(BoardingFee boardingFee) {
		List<EatingHistory> result = new ArrayList<>();
		LocalDate start = boardingFee.getStart_day().toLocalDate();
		List<LocalDate> localDates = getListDateInMonth(start.getMonthValue(), start.getYear());
		List<String> originalMenuIds = globalDAO.search("menu", "menu_id",
				"active is true", 0);
		List<String> backupMenuIds = new ArrayList<>(originalMenuIds);
		List<String> workingMenuIds = new ArrayList<>(backupMenuIds);
		Random random = new Random();
		for (LocalDate localDate : localDates) {
			if (workingMenuIds.isEmpty()) {
				workingMenuIds = new ArrayList<>(backupMenuIds);
			}
			int randomIndex = random.nextInt(workingMenuIds.size());
			String menuId = workingMenuIds.get(randomIndex);
			workingMenuIds.remove(randomIndex);
			result.add(new EatingHistory(
					0,
					Integer.parseInt(menuId),
					Date.valueOf(localDate),
					boardingFee.getBoardingFee_id()
			));
		}
		return new SearchResponse<>(result.size(), result
				.stream().map(this::toEatingHistoryResponse).toList());
	}

	public void fee(BoardingFee boardingFee,
		List<EatingHistoryRequest> eatingHistories) {
		int boardingFeeId = globalDAO.getAuto_IncrementOf("boardingFee");
		boardingFee.setBoardingFee_id(boardingFeeId);
		int days = eatingHistories.size();
		boardingFee.setNumberDays(days);
		int boardingFee_idlast = globalDAO.getLastIDOf("boardingFee");
		boardingFeeDAO.insert(boardingFee);
		List<Student> students = studentDAO.selectAll();
		for (Student std : students) {
			long returnMoney = 0;
			if (boardingFee_idlast != 0) {
				int month = boardingFee.getStart_day().toLocalDate().getMonthValue() - 1;
				if (month == 0)
					month = 12;
				String condition = "student_id = '" + std.getStudent_id() + "'";
				if (month != 8)
					condition += " AND month(absence_day) = '" + month + "'";
				int countAbsence = globalDAO.getSizeOf("absence", condition);
				BoardingFee bfLast = boardingFeeDAO.selectById(boardingFee_idlast);
				returnMoney += countAbsence * bfLast.getMainCosts();
				returnMoney += std.isSubMeal() ? countAbsence * bfLast.getSubCosts() : 0;
			}
			long money = days * boardingFee.getMainCosts();
			if (std.isSubMeal())
				money += days * boardingFee.getSubCosts();
			money -= returnMoney;
			Invoice invoice = new Invoice();
			invoice.setStudent_id(std.getStudent_id());
			invoice.setBoardingFee_id(boardingFeeId);
			invoice.setReturnMoney(returnMoney);
			invoice.setPayment_date(boardingFee.getStart_day());
			invoice.setStatusPayment((byte) 1);
			invoice.setMoney(money);
			invoiceDAO.insert(invoice);
		}
		for (EatingHistoryRequest request : eatingHistories) {
			EatingHistory eatingHistory = new EatingHistory();
			eatingHistory.setEating_day(Date.valueOf(request.getEating_day()));
			eatingHistory.setMenu_id(request.getMenu_id());
			eatingHistory.setBoardingFee_id(boardingFeeId);
			eatingHistoryDAO.insert(eatingHistory);
		}
	}

	public List<EatingHistory> selectBetweenDays(String startDay, String endDay) {
		return eatingHistoryDAO.selectBetweenDays(startDay, endDay);
	}
}
