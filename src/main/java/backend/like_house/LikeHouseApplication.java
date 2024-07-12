package backend.like_house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LikeHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeHouseApplication.class, args);
	}

}
