package model.dao;

import java.util.List;

public interface DAOInterface<T> {
	public boolean insert(T t);

	public boolean delete(T t);

	public boolean update(T t);

	public List<T> selectAll();

	public T selectById(T t);
}
