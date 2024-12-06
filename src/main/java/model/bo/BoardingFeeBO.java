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
	
	private BoardingFeeDAO dao = BoardingFeeDAO.getInstance();
	
	public boolean insert(BoardingFee t) {
		return dao.insert(t);
	}

	public boolean delete(BoardingFee t) {
		return dao.delete(t);
	}

	public boolean update(BoardingFee t) {
		return dao.update(t);
	}

	public List<BoardingFee> selectAll() {
		return dao.selectAll();
	}

	public BoardingFee selectById(BoardingFee t) {
		return dao.selectById(t);
	}

	public BoardingFee selectById(int t) {
		return dao.selectById(t);
	}
}
