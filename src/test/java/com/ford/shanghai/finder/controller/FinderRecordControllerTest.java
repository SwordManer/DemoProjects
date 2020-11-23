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
public class FinderRecordControllerTest extends AbstractControllerTest{

	private final static String REQUEST_URL = "/records";

	@Before
	public void setMockMvc() {
		setMvc(null);
	}

	@Test
	@Ignore
	public void testFinderRecord() throws Exception {
		testGetRequest(REQUEST_URL);
	}

	@Autowired
	public void setController(FinderRecordController finderRecordController) {
		this.controller = finderRecordController;
	}
}
