package coop.intergal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import coop.intergal.ui.security.SecurityConfiguration;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackageClasses = { SecurityConfiguration.class, Application.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
