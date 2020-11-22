package com.ford.shanghai.finder.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ford.shanghai.finder.dao.entity.FinderRecord;
import com.ford.shanghai.finder.model.FinderRecordModel;

import org.mapstruct.factory.Mappers;

@Mapper
public interface FinderRecordMapper {

	FinderRecordMapper INSTANCE = Mappers.getMapper(FinderRecordMapper.class);

	public FinderRecordModel toDomain(FinderRecord finderRecord);

	public List<FinderRecordModel> toDomains(List<FinderRecord> finderRecords);
}
