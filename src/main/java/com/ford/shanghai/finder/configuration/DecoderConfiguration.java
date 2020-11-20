package com.ford.shanghai.finder.configuration;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ford.shanghai.finder.converter.CustomMappingJackson2HttpMessageConverter;

import feign.codec.Decoder;

@Configuration
public class DecoderConfiguration {

	@Bean
	public Decoder textPlainDecoder() {
		return new SpringDecoder(() -> new HttpMessageConverters(new CustomMappingJackson2HttpMessageConverter()));
	}
}
