package com.ford.shanghai.finder.response;

import java.util.List;

import com.ford.shanghai.finder.model.FinderRecordModel;

import lombok.Data;

@Data
public class FinderRecordResponse {

	private List<FinderRecordModel> finderRecords;

}
