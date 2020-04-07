package pwr.zpi.socialballspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pwr.zpi.socialballspring.exception.NotFoundException;

@SpringBootApplication
public class SocialBallSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialBallSpringApplication.class, args);
	}

}
