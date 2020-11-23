package com.ford.shanghai.finder.service;

import com.ford.shanghai.finder.entity.WaysidePOIsEntity;

public interface InterestFinderService {

	public WaysidePOIsEntity fetchPOI(String origin, String destination, String query) throws Exception;

}
