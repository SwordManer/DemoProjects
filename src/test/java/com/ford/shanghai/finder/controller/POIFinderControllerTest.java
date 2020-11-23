package com.ford.shanghai.finder.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ford.shanghai.finder.InterestFinderApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { InterestFinderApplication.class })
@WebAppConfiguration
@AutoConfigureMockMvc
public class POIFinderControllerTest extends AbstractControllerTest{

	private final static String FILE_SRC_PATH = "file/request/post/poifinder.json";

	private final static String REQUEST_URL = "/path/driving/poi";

	@Before
	public void setMockMvc() {
		setMvc(FILE_SRC_PATH);
	}

	@Ignore
	@Test
	public void testFetchPointsOfInterest() throws Exception {
		testPostRequest(REQUEST_URL);
	}

	@Autowired
	public void setPOIFinderController(POIFinderController poiFinderController) {
		this.controller = poiFinderController;
	}
}
