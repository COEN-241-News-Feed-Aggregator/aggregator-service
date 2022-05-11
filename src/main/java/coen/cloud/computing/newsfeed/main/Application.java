package coen.cloud.computing.newsfeed.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("coen.cloud.computing.newsfeed")
public class Application {

	
  public static void main(String[] args) {
	  System.out.println("Hello");
    SpringApplication.run(Application.class);
  }
}
