package coop.intergal.ui.components;

import java.util.Arrays;
import java.util.Locale;

import com.vaadin.flow.component.datepicker.DatePicker;

public class EsDatePicker extends DatePicker {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EsDatePicker() {
		super();
		setLocale(new Locale("es", "ES"));
		this.setI18n(new DatePicker.DatePickerI18n().setWeek("semana")
		        .setCalendar("calendario").setClear("limpiar")
		        .setToday("hoy").setCancel("cancelar").setFirstDayOfWeek(1)
		        .setMonthNames(Arrays.asList("enero", "febrero", "marzo",
		                "abril", "mayo", "junio", "julio", "agosto",
		                "septiembre", "octubre", "noviembre", "diciembre"))
		        .setWeekdays(Arrays.asList("domingo", "lunes", "martes",
		                "miercoles", "jueves", "viernes", "sábado"))
		        .setWeekdaysShort(Arrays.asList("do", "lu", "ma", "mi", "ju",
		                "vi", "sa")));

	}
	
	

}
