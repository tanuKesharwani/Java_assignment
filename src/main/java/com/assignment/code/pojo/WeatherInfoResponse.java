package com.assignment.code.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherInfoResponse {

	public Main main;
	public Weather[] weather;

	static class Main {
		public double temp;
	}

	static class Weather {
		public String description;
	}

}
