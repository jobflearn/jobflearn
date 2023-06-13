package kr.binarybard.hireo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HireoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HireoApplication.class, args);
	}

}
