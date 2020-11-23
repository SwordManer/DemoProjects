package com.ford.shanghai.finder.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ford.shanghai.finder.utils.IOUtil;

public abstract class AbstractControllerTest {

	protected MockMvc mockMvc;

	protected AbstractController controller;

	protected String sourceString;

	protected void setMvc(final String fileSrcPath) {
		
		if (fileSrcPath==null) {
			return;
		}
		
		String filePath = Thread.currentThread().getContextClassLoader().getResource(fileSrcPath).getFile();
		sourceString = IOUtil.readFromFile(filePath);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	protected void testGetRequest(final String requestUrl) throws Exception {

		String result = mockMvc.perform(get(requestUrl)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
//						.content(sourceString)
						)
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn()
					.getResponse()
					.getContentAsString();
	}
	
	protected void testPostRequest(final String requestUrl) throws Exception {
		
		String result = mockMvc.perform(post(requestUrl)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(sourceString)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}
}
