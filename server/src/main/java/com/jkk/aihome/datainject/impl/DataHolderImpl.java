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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据持有者
 */
@Slf4j
public class DataHolderImpl<T extends Serializable, ID> implements IDataHolder<T, ID> {
	protected ConcurrentMap<ID, DataWithUpdate<T>> dataMap;

	protected ConcurrentMap<ID, T> deleteMap;

	protected Function<T, ID> idFunction;

	protected JpaRepository<T, ID> repository;

//	private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

	public DataHolderImpl(Collection<T> data, Function<T, ID> idFunction, JpaRepository<T, ID> repository, Scheduler scheduler) {
		dataMap = data.stream().collect(Collectors.toConcurrentMap(idFunction, DataWithUpdate::new));
		deleteMap = new ConcurrentHashMap<>();
		this.idFunction = idFunction;
		this.repository = repository;

		ScheduleUtil.createPersistenceScheduleJob(scheduler, "0/55 * * * * ?", this);
	}

	@Override
	public T save(T newData) {
		ID id = idFunction.apply(newData);
		if (id != null && dataMap.containsKey(id)) {
			return this.update(newData);
		}
		if (id == null) {
			// 主键需要数据库自动生成
			newData = repository.save(newData);
			dataMap.put(idFunction.apply(newData), new DataWithUpdate<>(newData));
		}else {
			dataMap.put(id, new DataWithUpdate<>(newData, true));
		}

		return newData;
	}

	@Override
	public List<T> saveAll(List<T> newDataList) {
		return newDataList.stream().map(this::save).collect(Collectors.toList());
	}

	@Override
	public void delete(T data) {
		ID id = idFunction.apply(data);
		this.deleteById(id);
	}

	@Override
	public void deleteById(ID id) {
		if (!dataMap.containsKey(id)) {
			throw new RuntimeException("不存在该条数据");
		}
		deleteMap.put(id, dataMap.get(id).getData());
		dataMap.remove(id);
	}

	@Override
	public List<T> findAll() {
		return dataMap.values().stream().map(DataWithUpdate::getData).collect(Collectors.toList());
	}

	@Override
	public Optional<T> findById(ID id) {
		DataWithUpdate<T> dataWithUpdate = dataMap.get(id);
		if (dataWithUpdate == null) {
			return Optional.empty();
		}else {
			return Optional.of(dataWithUpdate.getData());
		}
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

		if (deleteMap.size() != 0) {
			log.info("删除数量：{}, id：{}", deleteMap.size(), deleteMap.keySet().toArray());
			repository.deleteAll(deleteMap.values());
			deleteMap.clear();
		}

		if (updatedList.size() != 0) {
			log.info("持久化数量：{}, id：{}", updatedList.size(), updatedList.stream().map(idFunction).toArray());
			repository.saveAll(updatedList);
		}
	}

	static class DataWithUpdate<T extends Serializable>{
		private T data;
		private T origin;

		DataWithUpdate(T data, Boolean isInsert) {
			this.data = data;
			if (!isInsert) {
				setOrigin(data);
			}
		}
		DataWithUpdate(T data) {
			this(data, false);
		}
		synchronized void update(T newData) {
			this.data = newData;
		}

		public void setOrigin(T origin) {
			this.origin = SerializationUtils.clone(origin);
		}

		public T getData() {
			return data;
		}

		public T getOrigin() {
			return origin;
		}
	}
}
