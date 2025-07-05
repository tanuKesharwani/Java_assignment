package com.assignment.code.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.code.domain.WeatherInfo;

public interface WeatherInfoCrud extends JpaRepository<WeatherInfo, Long> {
	
	Optional<WeatherInfo> findByPincodeAndForDate(String pincode, LocalDate forDate);
}
