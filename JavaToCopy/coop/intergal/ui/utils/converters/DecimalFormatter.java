package coop.intergal.ui.utils.converters;

import java.math.BigInteger;

import com.vaadin.flow.templatemodel.ModelEncoder;

import coop.intergal.ui.dataproviders.DataProviderUtil;
import coop.intergal.ui.utils.FormattingUtils;


public class DecimalFormatter implements ModelEncoder<BigInteger, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String encode(BigInteger modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, FormattingUtils::formatAs2Decimal);
	}

	@Override
	public BigInteger decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}

	public BigInteger getCents(String col, int nDecimals) {
		if (col == null || col.equals("null") == true || col.equals("") == true)
			return null;
		col=col.replace(",","."); // change , for .
		col = col.format("%."+nDecimals+"f",Double.valueOf(col));
		col=col.replace(",",".");
		int idxE = col.indexOf("€"); // take off € sign 
		if (idxE > -1)
			col = col.substring(0,idxE-1 );
		String cents= col;
		int idXOfPoint = col.indexOf(".");
		if (nDecimals == 0)
			return new BigInteger (cents+"00");
		if (idXOfPoint == -1 ) // not decimala -> 1 is converted to 1.00
		{
			cents = cents + "00";
		}
		else if (idXOfPoint == col.length() -2 ) // has only one decimal after point , by example 0,5 is converted to 0,50
		{
			cents = col.substring(0, col.indexOf(".")) +  col.substring(col.indexOf(".")+1) + "0";
		}
		else if (idXOfPoint > -1)
			cents = col.substring(0, col.indexOf(".")) +  col.substring(col.indexOf(".")+1); // take off "."
		String nDecimalsStr = "0";  // trick0001 the number of decimals are add at the end of the number, to be pass inside the value , instead of a external parameter 
		if (nDecimals < 10 )
			nDecimalsStr = nDecimalsStr + nDecimals;
		else
			nDecimalsStr = nDecimals+"";
		cents = cents + nDecimalsStr;
		
		return new BigInteger (cents);

	}
}
