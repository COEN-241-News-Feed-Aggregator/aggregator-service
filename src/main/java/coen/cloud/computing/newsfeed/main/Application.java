package coen.cloud.computing.newsfeed.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("coen.cloud.computing.newsfeed.*")
@EnableJpaRepositories("coen.cloud.computing.newsfeed.*")
@EntityScan("coen.cloud.computing.newsfeed.*")
public class Application {

	
  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }
}
