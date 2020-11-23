package com.ford.shanghai.finder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ford.shanghai.finder.dao.FinderRecordDAO;
import com.ford.shanghai.finder.dao.entity.FinderRecord;
import com.ford.shanghai.finder.exception.DatabaseOperationException;
import com.ford.shanghai.finder.mapper.FinderRecordMapper;
import com.ford.shanghai.finder.model.FinderRecordModel;
import com.ford.shanghai.finder.service.FinderRecordService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FinderRecordServiceImpl implements FinderRecordService {

	@Autowired
	private FinderRecordDAO finderRecordDAO;

	@Override
	public List<FinderRecordModel> fetchRecords() throws DatabaseOperationException {
		try {
			List<FinderRecord> records = finderRecordDAO.queryRecords();
			return FinderRecordMapper.INSTANCE.toDomains(records);
		} catch (Exception e) {
			log.error("Error encountered while query record data from DB.");
			throw new DatabaseOperationException("Error encountered while query record data from DB.");
		}
	}
}
