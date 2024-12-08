package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.bean.BoardingClass;
import model.dao.BoardingClassDAO;
import model.dto.NameAndIdResponse;

public class BoardingClassBO {
	private static BoardingClassBO _instance;
	private BoardingClassBO() {}
	
	public static BoardingClassBO getInstance() {
		if (_instance == null) 
			_instance = new BoardingClassBO();
		return _instance;
	}
	
	private BoardingClassDAO boardingClassDAO = BoardingClassDAO.getInstance();
	
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
}
