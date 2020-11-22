package com.ford.shanghai.finder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.ford.shanghai.finder.dao.entity.FinderRecord;

public interface FinderRecordDAO extends Repository<FinderRecord, Integer>, CrudRepository<FinderRecord, Integer>{

	@Query(value = "select * from finderrecord", nativeQuery = true)
	public List<FinderRecord> queryRecords();
}
