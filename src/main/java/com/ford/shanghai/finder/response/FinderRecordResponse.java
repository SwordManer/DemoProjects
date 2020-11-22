package com.ford.shanghai.finder.response;

import java.util.List;

import com.ford.shanghai.finder.model.FinderRecordModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FinderRecordResponse {

	private List<FinderRecordModel> finderRecords;

}
