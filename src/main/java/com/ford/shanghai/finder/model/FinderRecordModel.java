package com.ford.shanghai.finder.model;

import java.util.Date;

import lombok.Data;

@Data
public class FinderRecordModel {

	private String poiType;

	private String userId;

	protected Date queryTime;

	private String startLocation;

	private String endLocation;

}
