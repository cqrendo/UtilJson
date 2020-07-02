package org.vaadin.intergal.validation;

import java.io.Serializable;
import java.util.function.Function;

/** Representa una regla de validación */
public interface ValidationConstraint<T> extends Function<T,String>, Serializable {

	@Override
	/**
	 * Devuelve el mensaje de error que resulta de validar el valor {@code t}, o
	 * {@code null} si la validación es exitosa
	 */
	String apply(T t);

}
