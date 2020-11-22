package com.ford.shanghai.finder.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ford.shanghai.finder.dao.entity.InterestPoint;
import com.ford.shanghai.finder.feign.response.PointOfInterest;

import org.mapstruct.factory.Mappers;

@Mapper(uses = {LocationMapper.class})
public interface InterestPointMapper {

	InterestPointMapper INSTANCE = Mappers.getMapper(InterestPointMapper.class);

	@Mappings({
		@Mapping(source = "location", target = "location", qualifiedByName = {"locationMapper", "mapLocation2Loc"} ),
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "channel", ignore = true),
		@Mapping(target = "poiType", ignore = true),
		@Mapping(target = "createTime", ignore = true),
		@Mapping(target = "updateTime", ignore = true)
	})
	public InterestPoint toDto(PointOfInterest poi);

	public List<InterestPoint> toDtos(List<PointOfInterest> poi);

	@Mappings({
		@Mapping(source = "location", target = "location", qualifiedByName = {"locationMapper", "mapLoc2Location"} )
	})
	public PointOfInterest toDomain(InterestPoint interestPoint);

	public Set<PointOfInterest> toDomains(Set<InterestPoint> interestPoints);
}
