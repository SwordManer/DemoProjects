package com.ford.shanghai.finder.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.ford.shanghai.finder.dao.entity.LocationInterestPoint;

public interface LocationInterestPointDAO extends Repository<LocationInterestPoint, Integer>, CrudRepository<LocationInterestPoint, Integer> {

//	@Query(value = "select InterestPoint from InterestPoint ip where ip.poiType=?1 and ip.location=?2")
	@Query(value = "select * from interest_point ip where ip.poi_type=?1 and ip.location=?2", nativeQuery = true)
	public Set<LocationInterestPoint> queryPOIsByLocation(String poiType, String loc);

	public Set<LocationInterestPoint> queryPOIsByLocs(String poiType, Set<String> locs);

}
