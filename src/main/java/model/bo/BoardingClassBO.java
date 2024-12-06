package model.bo;

import java.util.List;

import model.bean.BoardingClass;
import model.dao.BoardingClassDAO;

public class BoardingClassBO {
	private static BoardingClassBO _instance;
	private BoardingClassBO() {}
	
	public static BoardingClassBO getInstance() {
		if (_instance == null) 
			_instance = new BoardingClassBO();
		return _instance;
	}
	
	private BoardingClassDAO dao = BoardingClassDAO.getInstance();
	
	public boolean insert(BoardingClass t) {
		return dao.insert(t);
	}

	public boolean delete(BoardingClass t) {
		return dao.delete(t);
	}

	public boolean update(BoardingClass t) {
		return dao.update(t);
	}

	public List<BoardingClass> selectAll() {
		return dao.selectAll();
	}

	public BoardingClass selectById(BoardingClass t) {
		return dao.selectById(t);
	}

	public BoardingClass selectById(int t) {
		return dao.selectById(t);
	}
}
