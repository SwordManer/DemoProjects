package com.ford.shanghai.finder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ford.shanghai.finder.model.FinderRecordModel;
import com.ford.shanghai.finder.response.FinderRecordResponse;
import com.ford.shanghai.finder.response.utils.Response;
import com.ford.shanghai.finder.service.FinderRecordService;

@RestController
public class FinderRecordController {

	@Autowired
	private FinderRecordService finderRecordService;

	@RequestMapping(value = "/records", method = RequestMethod.GET)
	public Response<FinderRecordResponse> fetchPointsOfInterest() {
		FinderRecordResponse response = new FinderRecordResponse();
		List<FinderRecordModel> records = finderRecordService.fetchRecords();
		response.setFinderRecords(records);
		return Response.buildSuccess(response);
	}
}
