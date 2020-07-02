package org.vaadin.intergal.validation.constrains;

import java.time.LocalDate;
import java.util.Arrays;

import org.vaadin.intergal.validation.ValidationMetadata;

import coop.intergal.vaadin.rest.utils.DynamicDBean;


/**
 * Restricciones utilizadas con ValidationServiceImpl
 *
 * @author Javier
 *
 */
public class Constraints {

	private static String formatMessage(ValidationMetadata<?> metadata, String message) {
		String fieldName = metadata.getProperty("name");
		if (fieldName == null) {
			return message;
		} else {
			return fieldName + " " + message;
		}
	}

	public static String positive(Integer value, ValidationMetadata<?> metadata) {
		return greaterThan(value, metadata, "0");
	}

	public static String greaterThan(Integer value, ValidationMetadata<?> metadata, String arg) {
		int limit = Integer.parseInt(arg);
		if (value == null || value > limit) {
			return null;
		} else {
			return formatMessage(metadata, "debe ser mayor a " + limit);
		}
	}

	public static String lessThan(Integer value, ValidationMetadata<?> metadata, String arg) {
		int limit = Integer.parseInt(arg);
		if (value == null || value < limit) {
			return null;
		} else {
			return formatMessage(metadata, "debe ser menor a " + limit);
		}
	}

	public static String minLength(String value, ValidationMetadata<?> metadata, String arg) {
		int length = Integer.parseInt(arg);
		if (value == null || value.length() >= length) {
			return null;
		} else {
			return formatMessage(metadata, "debe contener al menos " + length + " caracteres");
		}
	}

	public static String notInFuture(LocalDate value, ValidationMetadata<?> metadata) {
		if (value == null || value.compareTo(LocalDate.now()) <= 0) {
			return null;
		} else {
			return formatMessage(metadata, "no debe ser posterior a la fecha actual");
		}
	}

	public static String compareDates(DynamicDBean bean, ValidationMetadata<?> metadata, String startDateCol, String endDateCol) {
		//Por simplicidad se ignora el argumento
		//El validador debería extraer los valores de interés a partir del parámetro delconstraint
		if (bean.getColDate(startDateCol) ==null || bean.getColDate(endDateCol)==null || bean.getColDate(startDateCol).compareTo(bean.getColDate(endDateCol)) < 0) {
			return null;
		} else {
			return "La fecha de inicio debe ser anterior a la fecha de finalizacion";
		}
	}

	public static String valueInList(String value, ValidationMetadata<?> metadata, String arg) {
		if (value == null || Arrays.asList(arg.split(",")).contains(value)) {
			return null;
		} else {
			return "El valor no se encuentra en la lista";
		}
	}

}
