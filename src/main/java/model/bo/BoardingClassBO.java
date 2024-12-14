package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.bean.BoardingClass;
import model.dao.BoardingClassDAO;
import model.dao.StudentDAO;
import model.dao.TeacherDAO;
import model.dto.BoardingClassResponse;
import model.dto.NameAndIdResponse;
import model.dto.SearchResponse;

public class BoardingClassBO {
	private static BoardingClassBO _instance;
	private BoardingClassBO() {}
	
	public static BoardingClassBO getInstance() {
		if (_instance == null) 
			_instance = new BoardingClassBO();
		return _instance;
	}
	
	private final BoardingClassDAO boardingClassDAO = BoardingClassDAO.getInstance();
	private final StudentDAO studentDAO = StudentDAO.getInstance();
	private final TeacherDAO teacherDAO = TeacherDAO.getInstance();
	
	public boolean insert(BoardingClass t) {
		return boardingClassDAO.insert(t);
	}

	public boolean delete(BoardingClass t) {
		return boardingClassDAO.delete(t);
	}

	public boolean update(BoardingClass t) {
		return boardingClassDAO.update(t);
	}

	public List<BoardingClass> selectAll() {
		return boardingClassDAO.selectAll();
	}

	public BoardingClass selectById(BoardingClass t) {
		return boardingClassDAO.selectById(t);
	}

	public BoardingClass selectById(int t) {
		return boardingClassDAO.selectById(t);
	}
	
	public List<NameAndIdResponse> getNameAndIds() {
		List<BoardingClass> classes = boardingClassDAO.selectAll();
		List<NameAndIdResponse> result = new ArrayList<NameAndIdResponse>();
		for (BoardingClass classs : classes) {
			result.add(new NameAndIdResponse(classs.getBoardingClass_id(), classs.getName()));
		}
		return result;
	}

	public BoardingClassResponse toBoardingClassResponse(BoardingClass t) {
		return new BoardingClassResponse(t.getBoardingClass_id(), t.getTeacher_id(),
				t.getName(), t.getNumberOfBed(), t.getRoom());
	}

	public SearchResponse<BoardingClassResponse> getPageBoardingClasses(int page, int amount,
		String searchField, String search, String sortField, String sortType) {
		return new SearchResponse<>(boardingClassDAO.count(searchField, search),
			boardingClassDAO.getPageBoardingClass(page, amount, searchField,
				search, sortField, sortType).stream().map(boardingClass -> {
					BoardingClassResponse r = toBoardingClassResponse(boardingClass);
					r.setNumberStudent(studentDAO.selectByBoardingClass_id(boardingClass
							.getBoardingClass_id()).size());
					r.setTeacherName(teacherDAO.selectById(boardingClass
							.getTeacher_id()).getName());
					return r;
				}
			).toList()
		);
	}

	public SearchResponse<NameAndIdResponse> getStudentDetail(int boardingClassId, int page, int amount) {
		return new SearchResponse<>(studentDAO.selectByBoardingClass_id(boardingClassId).size(),
			studentDAO.getPageStudents(page, amount,
			"boardingClass_id", boardingClassId + "",
			null, null).stream().map(student ->
				new NameAndIdResponse(student.getStudent_id(), student.getName())
			).toList());
	}

	public boolean existsByRoom(String room) {
		return boardingClassDAO.existByRoom(room);
	}

	public boolean deleteById(int id) {
		return boardingClassDAO.deleteById(id);
	}
}
