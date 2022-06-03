package com.jkk.aihome.datainject;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IDataHolder<T extends Serializable, ID> {
	List<T> findAll();

	Optional<T> findById(ID id);

	T save(T data);

	void deleteById(ID id);

	List<T> saveAll(List<T> newDataList);

	void delete(T data);

	void persistence();

	T update(T newData);
}
