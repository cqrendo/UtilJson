package org.vaadin.intergal.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
/** Un provider estático para {@link ValidationService} */
public class ValidationServiceProvider {

	private static ValidationService validationService;

//	@Autowired
	public ValidationServiceProvider(ValidationService validationService) {
		ValidationServiceProvider.validationService = validationService;
	}

	public static ValidationService getInstance() {
		return validationService;
	}

}
