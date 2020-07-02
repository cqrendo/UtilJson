package org.vaadin.intergal.validation;

public interface ValidationService {

	/**
	 * Devuelve un {@code ValidationConstraint} construido a partir de un string
	 * literal que especifica la restricción, y metadatos que describen la propiedad
	 */
	<T> ValidationConstraint<? super T> parseConstraint(String constraint, ValidationMetadata<T> metadata);

}
