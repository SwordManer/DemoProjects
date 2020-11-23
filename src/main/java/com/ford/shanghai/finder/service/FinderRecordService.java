package com.ford.shanghai.finder.service;

import java.util.List;

import com.ford.shanghai.finder.model.FinderRecordModel;

public interface FinderRecordService {

	public List<FinderRecordModel> fetchRecords() throws Exception;

}
