package com.example.spring_team12_withfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 시간 변경
@SpringBootApplication
public class Spring_Team12_WithFEApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring_Team12_WithFEApplication.class, args);
	}

}
