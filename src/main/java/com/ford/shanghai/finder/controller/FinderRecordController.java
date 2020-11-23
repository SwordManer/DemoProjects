package com.ford.shanghai.finder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ford.shanghai.finder.model.FinderRecordModel;
import com.ford.shanghai.finder.response.FinderRecordResponse;
import com.ford.shanghai.finder.response.utils.Response;
import com.ford.shanghai.finder.response.utils.ResponseEnum;
import com.ford.shanghai.finder.service.FinderRecordService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class FinderRecordController extends AbstractController{

	@Autowired
	private FinderRecordService finderRecordService;

	@RequestMapping(value = "/records", method = RequestMethod.GET)
	public Response<FinderRecordResponse> fetchFinderRecords() {
		log.info("Entering into the finderRecord service...");
		try {
			FinderRecordResponse response = new FinderRecordResponse();
			List<FinderRecordModel> records = finderRecordService.fetchRecords();
			response.setFinderRecords(records);
			return Response.buildSuccess(response);
		} catch (Exception e) {
			log.error("Error encountered while fetch records from DB.", e);
			return Response.buildError(ResponseEnum.ERROR);			
		}
	}
}
