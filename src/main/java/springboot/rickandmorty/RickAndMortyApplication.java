package springboot.rickandmorty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RickAndMortyApplication {
    public static void main(String[] args) {
        SpringApplication.run(RickAndMortyApplication.class, args);
    }
}
