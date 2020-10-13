package org.vaadin.intergal.validation;

import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import coop.intergal.vaadin.rest.utils.DataService;

/**
 * Implementación de Binder.Validator que delega en el servicio de validación
 */
public class DynValidator<T> implements Validator<T> {

	private static final long serialVersionUID = 1L;

	private final ValidationConstraint<? super T> constraint;

	/**
	 * Crea una nueva instancia de {@code DynValidator} La regla de validación se
	 * analiza contra la instancia de {@link ValidationService} devuelta por
	 * {@link ValidationServiceProvider}
	 *
	 * que pueden ser utilizados en la validacion
	 *
	 * @param constraint La regla de validación
	 * @param metadata   metadatos sobre la propiedad
	 */
	public DynValidator(String constraint, ValidationMetadata<T> metadata) {
		this.constraint = ValidationServiceProvider.getInstance().parseConstraint(constraint, metadata);
	}

	@Override
	public ValidationResult apply(T value, ValueContext context) {
		String errorMessage = constraint.apply(value);
		if (errorMessage != null) {
			if (errorMessage.startsWith("WARNING"))
			{
				DataService.get().showError(errorMessage.substring(7));
				return ValidationResult.ok();//ValidationResult.create(errorMessage.substring(7), ErrorLevel.WARNING);
			}
			else
				return ValidationResult.error(errorMessage);
		} else {
			return ValidationResult.ok();
		}
	}

}
