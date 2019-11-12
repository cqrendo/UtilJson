package coop.intergal.ui.utils.converters;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Locale;


public class DateTimeFormatter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Format the given local time using the given locale.
	 *
	 * @param dateTime
	 *            the date and time to format
	 * @param locale
	 *            the locale to use to determine the format
	 * @return a formatted string
	 */
	public String format(LocalDateTime dateTime, Locale locale) {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
				.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
		return dateTime.format(formatter);
	}
	public String format(String dateString , Locale locale) { // to send a string in the format yyyy-MM-dd'T'HH:mm:ss, and string in short date
		if (locale == null)
			locale = new Locale("es","ES");
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		int lastIDx = dateString.indexOf("+");
		if (lastIDx == -1 ) // doesn't have +00...
			lastIDx = dateString.length();
		String date = dateString.substring(0,lastIDx);
		LocalDateTime localDate = LocalDateTime.parse(date, formatter);
		return format(localDate, new Locale("es","ES"));
	}

}
