package com.ford.shanghai.finder.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.ford.shanghai.finder.dao.entity.InterestPoint;

public interface InterestPointDAO extends Repository<InterestPoint, Integer>, CrudRepository<InterestPoint, Integer> {

	@Query(value = "select * from interestpoint ip where ip.poi_type=?1 and ip.location=?2", nativeQuery = true)
	public Set<InterestPoint> queryPOIsByConditions(String query, String loc);

	@Query(value = "select * from interestpoint ip where ip.poi_type=?1 and ip.location in ?2", nativeQuery = true)
	public Set<InterestPoint> queryPOIsByLocs(String poiType, Set<String> locs);

}
