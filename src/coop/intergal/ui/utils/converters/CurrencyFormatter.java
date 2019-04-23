package coop.intergal.ui.utils.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;

import coop.intergal.ui.dataproviders.DataProviderUtil;
import coop.intergal.ui.utils.FormattingUtils;


public class CurrencyFormatter implements ModelEncoder<Integer, String> {

	@Override
	public String encode(Integer modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, FormattingUtils::formatAsCurrency);
	}

	@Override
	public Integer decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}

	public Integer getCents(String col) {
		String cents= col;
		int idXOfPoint = col.indexOf(".");
		if (idXOfPoint == -1) // not decimala -> 1 is converted to 1.00
		{
			cents = cents + "00";
		}
		else if (idXOfPoint == col.length() -2 ) // has only one decimal after point , by example 0,5 is converted to 0,50
		{
			cents = col.substring(0, col.indexOf(".")) +  col.substring(col.indexOf(".")+1) + "0";
		}
		else if (idXOfPoint > -1)
			cents = col.substring(0, col.indexOf(".")) +  col.substring(col.indexOf(".")+1); // take off "."
		return new Integer (cents);

	}
}
