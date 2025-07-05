package com.assignment.code.service;

import com.assignment.code.domain.WeatherInfo;
import com.assignment.code.pojo.WeatherInfoRequest;

public interface WeatherService {

	public WeatherInfo getWeatherByPincode(WeatherInfoRequest request);
	
}
