package com.ford.shanghai.finder.dao;

import java.util.List;

import com.ford.shanghai.finder.dao.entity.InterestPoint;

public interface InterestFinderDAO {

	public List<InterestPoint> queryPOIsByConditions(String query, String loc);

	public Boolean savePOIs(List<InterestPoint> results);

}
