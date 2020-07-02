package coop.intergal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vaadin.intergal.validation.ValidationService;
import org.vaadin.intergal.validation.ValidationServiceProvider;
import org.vaadin.intergal.validation.constrains.ValidationServiceImpl;

@Configuration
public class ApplicationConfiguration {
    @Bean
    ValidationService getValidationService() {
        return new ValidationServiceImpl();
    }

    @Bean
    ValidationServiceProvider getValidationServiceProvider(ValidationService validationService) {
        return new ValidationServiceProvider(validationService);
    }

}

