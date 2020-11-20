package com.ford.shanghai.finder.converter;

import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	@Override
	public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
		super.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
	}
}