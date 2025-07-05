package com.assignment.code.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assignment.code.domain.WeatherInfo;
import com.assignment.code.handler.InvalidInputException;
import com.assignment.code.pojo.GeoResponse;
import com.assignment.code.pojo.WeatherInfoRequest;
import com.assignment.code.repository.WeatherInfoCrud;
import com.assignment.code.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private WeatherInfoCrud repository;

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${openweather.api.key}")
	private String apiKey;

	@Override
	public WeatherInfo getWeatherByPincode(WeatherInfoRequest request) {
		// check DB first
		try {
			Optional<WeatherInfo> optional = repository.findByPincodeAndForDate(request.getPincode(),
					request.getForDate());
			if (optional.isPresent()) {
				return optional.get();
			}

			// 1. geocode pincode → lat/lon
			String geoUrl = String.format("http://api.openweathermap.org/geo/1.0/zip?zip=%s,in&appid=%s",
					request.getPincode(), apiKey);

			var geoResponse = restTemplate.getForObject(geoUrl, GeoResponse.class);

			if (geoResponse == null) {
				throw new InvalidInputException("Invalid Pincode or cannot fetch coordinates.");
			}

			double lat = geoResponse.lat;
			double lon = geoResponse.lon;

			// 2. get weather
			String weatherUrl = String.format(
					"https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric", lat, lon,
					apiKey);

			var weatherResponse = restTemplate.getForObject(weatherUrl, WeatherResponse.class);

			// store in DB
			WeatherInfo weatherInfo = new WeatherInfo();
			weatherInfo.setPincode(request.getPincode());
			weatherInfo.setLatitude(lat);
			weatherInfo.setLongitude(lon);
			weatherInfo.setForDate(request.getForDate());
			weatherInfo.setWeatherDescription(weatherResponse.weather[0].description);

			repository.save(weatherInfo);
			return weatherInfo;
		} catch (Exception e) {
			throw new InvalidInputException("Invalid pincode or unable to fetch weather");
		}
	}

	static class WeatherResponse {
		public Main main;
		public Weather[] weather;

		static class Main {
			public double temp;
		}

		static class Weather {
			public String description;
		}
	}
}
