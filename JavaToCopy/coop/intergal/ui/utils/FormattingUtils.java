package coop.intergal.ui.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;


import coop.intergal.AppConstGeneric;

public class FormattingUtils {

	public static final String DECIMAL_ZERO = "0.00";

	/**
	 * 3 letter month name + day number E.g: Nov 20
	 */
	public static final DateTimeFormatter MONTH_AND_DAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d",AppConstGeneric.APP_LOCALE);

	/**
	 * Full day name. E.g: Monday.
	 */
	public static final DateTimeFormatter WEEKDAY_FULLNAME_FORMATTER = DateTimeFormatter.ofPattern("EEEE",
			AppConstGeneric.APP_LOCALE);

	/**
	 * For getting the week of the year from the local date.
	 */
	public static final TemporalField WEEK_OF_YEAR_FIELD = WeekFields.of(AppConstGeneric.APP_LOCALE).weekOfWeekBasedYear();

	/**
	 * 3 letter day of the week + day number. E.g: Mon 20
	 */
	public static final DateTimeFormatter SHORT_DAY_FORMATTER = DateTimeFormatter.ofPattern("E d",
			AppConstGeneric.APP_LOCALE);

	/**
	 * Full date format. E.g: 03.03.2001
	 */
	public static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter
			.ofPattern("dd.MM.yyyy", AppConstGeneric.APP_LOCALE);

	/**
	 * Formats hours with am/pm. E.g: 2:00 PM
	 */
	public static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter
			.ofPattern("h:mm a", AppConstGeneric.APP_LOCALE);

	/**
	 * Returns the month name of the date, according to the application locale. 
	 * @param date {@link LocalDate}
	 * @return The full month name. E.g: November
	 */
	public static String getFullMonthName(LocalDate date) {
		return date.getMonth().getDisplayName(TextStyle.FULL, AppConstGeneric.APP_LOCALE);
	}

	public static String formatAsCurrency(int valueInCents) {
		return NumberFormat.getCurrencyInstance(AppConstGeneric.APP_LOCALE).format(BigDecimal.valueOf(valueInCents, 2));
	}
	public static String formatAs2Decimal(BigInteger valueInCents) {
		
		// trick0001 the number of decimals are add at the end of the number, to be pass inside the value , instead of a external parameter
		String value = valueInCents + "";
		int length = value.length();
		if (length <2)
		{
//			System.out.println("FormattingUtils.formatAs2Decimal()");
			return "0";
		}
		int nDecimals = new Integer(value.substring(length -2));
		value = value.substring(0,length -2);
		int posiComma = value.length() -nDecimals;
		if (posiComma ==0)
		{
			return "0,"+value;
		}
		if (posiComma <0)
		{
			String nCeros = new String(new char[posiComma * -1]).replace("\0", "0");
			return "0,"+nCeros+value;
		}

		
		String afterComma = value.substring(posiComma);
		String beforeComma = value.substring(0,posiComma);	
		NumberFormat nf = NumberFormat.getNumberInstance(AppConstGeneric.APP_LOCALE);
		DecimalFormat df = (DecimalFormat)nf;
		String nCeros = new String(new char[nDecimals]).replace("\0", "0");
		df.applyPattern("###,###."+nCeros);
		String output = df.format(Double.valueOf(beforeComma+ "."+afterComma));
		return output;
//		if (length > 5 && length < 9 )
//		{
//			int posPoint1 = length -5;
//			beforeComma = beforeComma.substring(0,posPoint1) + "." + beforeComma.substring (posPoint1);
//		}
//		if (length > 8  && length < 12 )
//		{
//			int posPoint1 = length -5;
//			int posPoint2 = length -8;
//			beforeComma = beforeComma.substring(0,posPoint2) + "."+ beforeComma.substring(posPoint2,posPoint1 ) + "." + beforeComma.substring (posPoint1);
//		}
//		if (length > 11  && length < 15 )
//		{
//			int posPoint1 = length -5;
//			int posPoint2 = length -8;
//			int posPoint3 = length -11;
//			beforeComma = beforeComma.substring(0,posPoint3) + "."+ beforeComma.substring(posPoint3,posPoint2 ) + "."+ beforeComma.substring(posPoint2,posPoint1 ) 
//			+ "." + beforeComma.substring (posPoint1);
//		}
//		if (length > 14  && length < 18 )
//		{
//			int posPoint1 = length -5;
//			int posPoint2 = length -8;
//			int posPoint3 = length -11;
//			int posPoint4 = length -14;
//			beforeComma = beforeComma.substring(0,posPoint4) + "."+ beforeComma.substring(posPoint4,posPoint3 ) + "."+ beforeComma.substring(posPoint3,posPoint2 ) + "."+ beforeComma.substring(posPoint2,posPoint1 ) 
//			+ "." + beforeComma.substring (posPoint1);
//		}
//
//
//		return beforeComma + "," +afterComma;
	}

	public static DecimalFormat getUiPriceFormatter() {
		DecimalFormat formatter = new DecimalFormat("#" + DECIMAL_ZERO,
				DecimalFormatSymbols.getInstance(AppConstGeneric.APP_LOCALE));
		formatter.setGroupingUsed(false);
		return formatter;
	}
}
