package com.assignment.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.code.domain.WeatherInfo;
import com.assignment.code.pojo.WeatherInfoRequest;
import com.assignment.code.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherInfoController {

	@Autowired
    private WeatherService weatherService;

    @GetMapping
    public WeatherInfo getWeather(@RequestBody WeatherInfoRequest request) {       
        return weatherService.getWeatherByPincode(request);
    }
}

