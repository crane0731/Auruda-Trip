package com.sw.AurudaTrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling // 스케줄링 활성화
//@EnableAsync

public class AurudaTripApplication {

	public static void main(String[] args) {
		SpringApplication.run(AurudaTripApplication.class, args);
	}

}
