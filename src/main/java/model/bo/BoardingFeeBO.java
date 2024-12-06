package model.bo;

import java.util.List;

import model.bean.BoardingFee;
import model.dao.BoardingFeeDAO;

public class BoardingFeeBO {
	private static BoardingFeeBO _instance;
	private BoardingFeeBO() {}
	
	public static BoardingFeeBO getInstance() {
		if (_instance == null) 
			_instance = new BoardingFeeBO();
		return _instance;
	}
	
	private BoardingFeeDAO boardingFeeDAO = BoardingFeeDAO.getInstance();
	
	public boolean insert(BoardingFee t) {
		return boardingFeeDAO.insert(t);
	}

	public boolean delete(BoardingFee t) {
		return boardingFeeDAO.delete(t);
	}

	public boolean update(BoardingFee t) {
		return boardingFeeDAO.update(t);
	}

	public List<BoardingFee> selectAll() {
		return boardingFeeDAO.selectAll();
	}

	public BoardingFee selectById(BoardingFee t) {
		return boardingFeeDAO.selectById(t);
	}

	public BoardingFee selectById(int t) {
		return boardingFeeDAO.selectById(t);
	}
}
