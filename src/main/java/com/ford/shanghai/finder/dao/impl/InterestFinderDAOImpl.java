package com.ford.shanghai.finder.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ford.shanghai.finder.dao.InterestFinderDAO;
import com.ford.shanghai.finder.dao.entity.InterestPoint;

@Repository
public class InterestFinderDAOImpl implements InterestFinderDAO {

	@Override
	public List<InterestPoint> queryPOIsByConditions(String query, String loc) {
		
		
		
		return null;
	}

	@Override
	public Boolean savePOIs(List<InterestPoint> results) {

		return null;
	}

}
