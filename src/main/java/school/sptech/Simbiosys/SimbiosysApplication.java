package school.sptech.Simbiosys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SimbiosysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimbiosysApplication.class, args);
	}

}
