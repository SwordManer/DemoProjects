package com.ford.shanghai.finder.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.ford.shanghai.finder.dao.entity.InterestPoint;

public interface InterestFinderDAO extends Repository<InterestPoint, Integer>, CrudRepository<InterestPoint, Integer> {

//	@Query(value = "select InterestPoint from InterestPoint ip where ip.poiType=?1 and ip.location=?2")
	@Query(value = "select * from interest_point ip where ip.poi_type=?1 and ip.location=?2", nativeQuery = true)
	public List<InterestPoint> queryPOIsByConditions(String query, String loc);

}
