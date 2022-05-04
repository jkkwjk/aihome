package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.IDataHolder;
import com.jkk.aihome.util.ScheduleUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.quartz.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据持有者
 */
@Slf4j
public class DataHolderImpl<T extends Serializable, ID> implements IDataHolder<T, ID> {
	protected ConcurrentMap<ID, DataWithUpdate<T>> dataMap;

	protected Function<T, ID> idFunction;

	protected JpaRepository<T, ID> repository;

//	private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

	public DataHolderImpl(Collection<T> data, Function<T, ID> idFunction, JpaRepository<T, ID> repository, Scheduler scheduler) {
		dataMap = data.stream().collect(Collectors.toConcurrentMap(idFunction, DataWithUpdate::new));
		this.idFunction = idFunction;
		this.repository = repository;

		ScheduleUtil.createPersistenceScheduleJob(scheduler, "0/10 * * * * ?", this);
	}

	@Override
	public T save(T newData) {
		ID id = idFunction.apply(newData);
		if (dataMap.containsKey(id)) {
			return this.update(newData);
		}
		newData = repository.save(newData);
		dataMap.put(id, new DataWithUpdate<>(newData));

		return newData;
	}

	public void delete(T data) {
		ID id = idFunction.apply(data);
		this.deleteById(id);
	}

	@Override
	public void deleteById(ID id) {
		if (!dataMap.containsKey(id)) {
			throw new RuntimeException("不存在该条数据");
		}
		repository.deleteById(id);
		dataMap.remove(id);
	}

	@Override
	public List<T> findAll() {
		return dataMap.values().stream().map(DataWithUpdate::getData).collect(Collectors.toList());
	}

	@Override
	public Optional<T> findById(ID id) {
		return Optional.ofNullable(dataMap.get(id).getData());
	}

	@Override
	public T update(T newData) {
		ID id = idFunction.apply(newData);
		dataMap.get(id).update(newData);

		return newData;
	}

	@Override
	public void persistence() {
//		reentrantReadWriteLock.writeLock().lock();
		List<T> updatedList = dataMap.values().stream()
				.filter(o -> !o.getData().equals(o.getOrigin()))
				.peek(o -> o.setOrigin(o.getData()))
				.map(DataWithUpdate::getData) // 保存的是最新的数据，可能会跟origin不一致，下次更新还会返回update的数据
				.collect(Collectors.toList());
//		reentrantReadWriteLock.writeLock().unlock();

		log.info("持久化数量：{}, id：{}", updatedList.size(), updatedList.stream().map(idFunction).toArray());
		repository.saveAll(updatedList);
	}

	static class DataWithUpdate<T extends Serializable>{
		T data;
		T origin;
		DataWithUpdate(T data) {
			setData(data);
			setOrigin(data);
		}
		synchronized void update(T newData) {
			setData(newData);
		}

		public void setOrigin(T origin) {
			this.origin = SerializationUtils.clone(origin);
		}

		public void setData(T data) {
			this.data = data;
		}

		public T getData() {
			return data;
		}

		public T getOrigin() {
			return origin;
		}
	}
}
