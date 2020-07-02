package org.vaadin.intergal.validation.constrains;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.stereotype.Service;
import org.vaadin.intergal.validation.ValidationConstraint;
import org.vaadin.intergal.validation.ValidationMetadata;
import org.vaadin.intergal.validation.ValidationService;

//@Service
/**
 * Implementación sencilla a modo de ejemplo que recibe como constraint el
 * nombre de un metodo en una clase, y un parámetro adicional.
 *
 * Por ejemplo: parseConstraint("org.vaadin.sr3391.Constraints.dateInPast",
 * LocalDate.class) devuelve una validación implementanda en el método
 * dateInPast(LocalDate, ValidationMetadata) o dateInPast(Object,
 * ValidationMetadata) de la clase org.vaadin.sr3391.Constraints (para
 * simplificar la implementación, si varios métodos son aplicables, utiliza
 * cualquiera de ellos)
 *
 * El método debe retornar String y tener modificadores public y static. El
 * primer parámetro del método debe ser compatible con un valor de tipo
 * valueType.
 *
 * Opcionalmente se admite un tercer parámetro de tipo String, que recibirá el
 * valor de un parametro indicado en el constraint. Por ejemplo
 * "org.vaadin.sr3391.Constraints.lessThan#100" realizará una invocación al
 * método lessThan(value,metadata,"100")
 *
 * @param constraint la regla de validacion
 * @param valueType  el tipo del dato a validar
 */
public class ValidationServiceImpl implements ValidationService {

	@Override
	public <T> ValidationConstraint<? super T> parseConstraint(String constraint, ValidationMetadata<T> metadata) {

		String ss[] = constraint.split("#", 2);
		int i = ss[0].lastIndexOf('.');

		String methodName = ss[0].substring(i+1);
		String className  = ss[0].substring(0,i);
		String arg        = ss.length==1?null:ss[1];

		Method method;
		try {
			method = lookupMethod(className, methodName, metadata.getValueType(), ss.length + 1);
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e);
		}

		return value -> invoke(method, value, metadata, arg);
	}

	private String invoke(Method method, Object value, ValidationMetadata<?> metadata, Object arg) {
		if (value == null) {
			return null;
		}
		Object args[] = arg == null ? new Object[] { value, metadata } : new Object[] { value, metadata, arg };
		try {
			return (String) method.invoke(null, args);
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	private Method lookupMethod(String className, String methodName, Class<?> valueType, int arity)
			throws ClassNotFoundException, NoSuchMethodException {
		for (Method method : Class.forName(className).getMethods()) {
			if (!method.getName().equals(methodName)) {
				continue;
			}
			if (!Modifier.isStatic(method.getModifiers())) {
				continue;
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				continue;
			}
			if (method.getReturnType()!=String.class) {
				continue;
			}
			if (method.getParameterCount()!=arity) {
				continue;
			}
			if (valueType!=null &&!method.getParameterTypes()[0].isAssignableFrom(valueType)) {
				continue;
			}
			if (method.getParameterTypes()[1] != ValidationMetadata.class) {
				continue;
			}
			if (method.getParameterCount() == 3 && method.getParameterTypes()[2] != String.class) {
				continue;
			}
			return method;
		}
		throw new NoSuchMethodException(className+"."+methodName);
	}

}
