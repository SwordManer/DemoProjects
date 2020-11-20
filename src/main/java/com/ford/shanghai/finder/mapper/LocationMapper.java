package com.ford.shanghai.finder.mapper;

import java.math.BigDecimal;

import org.mapstruct.Named;

import com.ford.shanghai.finder.feign.response.Location;

@Named("locationMapper")
public class LocationMapper {

	@Named("mapLoc2Location")
	Location mapLocation(String loc) {
		if (loc!=null&&!loc.isEmpty()) {
			String[] cord = loc.split(",");
			Location location = new Location();
			location.setLat(new BigDecimal(cord[0]));
			location.setLng(new BigDecimal(cord[1]));
			return location;
		}
		return null;
	}

	@Named("mapLocation2Loc")
	String mapLocation(Location location) {
		if (location!=null) {
			return location.toString();
		}
		return null;
	}
}
