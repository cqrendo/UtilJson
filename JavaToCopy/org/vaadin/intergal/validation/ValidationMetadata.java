package org.vaadin.intergal.validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta es una clase generica que comunica al validador el tipo de datos de la
 * propiedad y algunas otras propiedades arbitrarias que pueden ser de interés
 * para generar un mensaje.
 *
 * @param <T> el tipo de datos de la propiedad
 */
public final class ValidationMetadata<T> {

	private final Class<T> valueType;

	private Map<String, String> properties;

	private ValidationMetadata(Class<T> valueType, Map<String, String> properties) {
		this.valueType = valueType;
		this.properties = properties;
	}

	public Class<T> getValueType() {
		return valueType;
	}

	public static <T> ValidationMetadata<T> of(Class<T> valueType) {
		return new ValidationMetadata<>(valueType, new HashMap<>());
	}

	public ValidationMetadata<T> withProperty(String name, String value) {
		properties.put(name, value);
		return this;
	}

	public String getProperty(String key) {
		return properties.get(key);
	}

}
