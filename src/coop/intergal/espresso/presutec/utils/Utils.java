package coop.intergal.espresso.presutec.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Utils {

	public static String determineOperator(String value) {
		if (value == null)
			return null;
		if (value.indexOf("%20like%20") > 0) // the filter can comes ready to be used then any operation is necessary
 			return value;
		value = value.trim(); //deletes extra blanks pre and post
		if (value.indexOf("%") >= 0 )
			{
			value = value.replaceAll("%", "%25") ;
			if (value.indexOf(" ") >= 0 )
				value = value.replaceAll(" ", "%20") ;
			if (value.indexOf("like") >=0)
				return value;
			return "%20like('"+value+"')";
			}
		if (value.indexOf("<") >= 0)
			{
			return value.replaceAll("<","%3C");
			}
		if (value.indexOf(">") >= 0)
			{
			return value.replaceAll(">","%3E");
			}
		if (value.indexOf("=") > 0)
			return value.substring(0 ,value.indexOf("=")+1) + "'" + value.substring(value.indexOf("=")+1)+ "'";
		return "='"+value+"'";
	}
	public static String componeDateFilter(String field, String value) {
		if (value == null)
			return null;
		// posible formats ( DD-MM-AAAA // DD-MM-AAAA hh:mm // DD-MM-AAAA:DD-MM-AAAA // DD-MM-AAAA hh:mm:DD-MM-AAAA hh:mm // >DD-MM-AAAA hh:mm..... )
		// DD-MM-AAAA
		if (value.startsWith("<") || value.startsWith(">") )
			{
			String op = "%3E";
			if (value.startsWith("<"))
				op = "%3C";
			String value1 = changeFromAAAAMMDDtoDDMMAAAA(value.substring(1));
			return field+op+"'"+value1.replaceAll(" ", "%20")+"'";
			}
		// DD-MM-AAAA HH:MM
		if (value.length() < 17)
			{
			String value1 = changeFromAAAAMMDDtoDDMMAAAA(value);
			String value2 = changeFromAAAAMMDDtoDDMMAAAANextDay(value);
			return field+"%3E='"+value1.replaceAll(" ", "%20")+"'%20AND%20"+field+"%3C'"+value2.replaceAll(" ", "%20")+"'";
			}
		else // a date range DD-MM-AAAA::DD-MM-AAAA or  DD-MM-AAAA hh:mm::DD-MM-AAAA hh:mm
		{
			String [] tokens = value.split("::");  // the :: is use to avoid conflict with : of HH:mm
			String date1 = changeFromAAAAMMDDtoDDMMAAAA(tokens[0]); 
			String date2 = changeFromAAAAMMDDtoDDMMAAAA(tokens[1]); 
			if (date2.indexOf(":") ==-1) // is time is not included then 23:59 is add to consider the last date inclusive
				date2=date2+" 23:59:59";
			else
				date2=date2+":59"; // the last time is complement with 59 seconds to be inclusive
			return field+"%3E='"+date1.replaceAll(" ", "%20")+"'%20AND%20"+field+"%3C='"+date2.replaceAll(" ", "%20")+"'";
			
		}
	}
	private static String changeFromAAAAMMDDtoDDMMAAAA(String value) {
		String vDay, vMonth, vYear;
		StringTokenizer tokens = new StringTokenizer(value,"-"); 
		vDay = tokens.nextToken(); 
		vMonth = tokens.nextToken();
		vYear = tokens.nextToken(); 
		if (vYear.length() > 4) // then it comes in the format  DD-MM-AAAA HH:MM
		{
			vDay =  vDay + vYear.substring(4);
			vYear = vYear.substring(0,4);
			
		}
		return vYear+"-"+vMonth+"-"+vDay; 
	}
	private static String changeFromAAAAMMDDtoDDMMAAAANextDay(String value) {
		value=changeFromAAAAMMDDtoDDMMAAAA(value);
//		String dt = "2008-01-01";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int timeToAdd;
		if (value.length() < 11) // only date not time
			{
			timeToAdd = Calendar.DATE;
			}
		else
			{
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timeToAdd = Calendar.MINUTE;
			}
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(timeToAdd, 1);  // number of days to add
		value = sdf.format(c.getTime());  // dt is now the new date
		return value;
//		String vMonth, vYear, vHour, vDay, vMinute;
//		StringTokenizer tokens = new StringTokenizer(value,"-"); 
//		vDay = tokens.nextToken(); 
//		vMonth = tokens.nextToken();
//		vYear = tokens.nextToken(); 		
//		
//		if (vYear.length() > 4) // then it comes in the format  DD-MM-AAAA HH:MM
//		{
//			StringTokenizer tokensDate = new StringTokenizer(vYear,":"); 
//			vHour = tokensDate.nextToken().substring(5);
//			vMinute =tokensDate.nextToken();
//			vDay =  vDay + vHour + vMinute;
//			vYear = vYear.substring(0,4);
//			
//		}
//		return vYear+"-"+vMonth+"-"+vDay; 
	}
	public static boolean isInVisibleList(String prop, boolean isReadOnly, String fieldLabel, boolean isInserting) { // TODO add this info to doc create a way to configure this list
		if ("idCustomer".equals(prop) && !isReadOnly) // this is use for Callcenter Form (FormChained), in a "especial Pick"
			return false;
		if( (fieldLabel.startsWith("HIDE @ FIELD") && !isInserting))
			return false;
		if ((fieldLabel.startsWith("HIDE @ FIELD") || fieldLabel.startsWith("HIDE @ ADD")) && isInserting)
			return false;

		return true;
	}
}
