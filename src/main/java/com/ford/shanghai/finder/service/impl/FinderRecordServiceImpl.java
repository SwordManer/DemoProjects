package com.ford.shanghai.finder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ford.shanghai.finder.dao.FinderRecordDAO;
import com.ford.shanghai.finder.dao.entity.FinderRecord;
import com.ford.shanghai.finder.mapper.FinderRecordMapper;
import com.ford.shanghai.finder.model.FinderRecordModel;
import com.ford.shanghai.finder.service.FinderRecordService;

@Service
public class FinderRecordServiceImpl implements FinderRecordService {

	@Autowired
	private FinderRecordDAO finderRecordDAO;

	@Override
	public List<FinderRecordModel> fetchRecords() {
		List<FinderRecord> records = finderRecordDAO.queryRecords();
		List<FinderRecordModel> recordModels = FinderRecordMapper.INSTANCE.toDomains(records);
		return recordModels;
	}
}
