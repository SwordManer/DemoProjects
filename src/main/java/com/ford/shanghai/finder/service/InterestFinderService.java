package com.ford.shanghai.finder.service;

import com.ford.shanghai.finder.entity.InterestPointEntity;
import com.ford.shanghai.finder.entity.WaysidePOIsEntity;

public interface InterestFinderService {

	public InterestPointEntity fetchPathPlan(String origin, String destination);

	public String fetchSearching(String origin, String destination, String radius, String output);

	public WaysidePOIsEntity fetchPOI(String origin, String destination, String query);

}
