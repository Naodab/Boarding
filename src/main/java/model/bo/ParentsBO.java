package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.bean.Parents;
import model.dao.ParentsDAO;
import model.dao.StudentDAO;
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
}
