package com.ford.shanghai.finder.entity;

import java.util.List;

import com.ford.shanghai.finder.feign.response.PointOfInterest;

import lombok.Data;

@Data
public class WaysidePOIsEntity {

	private List<PointOfInterest> searchingResults;
}
