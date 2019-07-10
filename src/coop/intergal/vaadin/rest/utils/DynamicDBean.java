package coop.intergal.vaadin.rest.utils;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
//@Entity
//@Component
import com.vaadin.flow.server.StreamResource;

//@Entity
//@Bean
public class DynamicDBean {// extends AbstractEntity{/**
	private static final long serialVersionUID = 1L;
// implements Serializable {
//	private static final long serialVersionUID = 1L;

	private String preConfParam;
	private String resourceName; 
	private String filter; 
	private JsonNode rowJSon;
	private boolean isReadOnly ;
	private StreamResource streamResource;
	private InputStream inputStream;
	JsonNode rowColTypeList;
	private ArrayList<String[]> rowsColList = new ArrayList<String[]>();
	private String col0;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String col6;
	private String col7;
	private String col8;
	private String col9;
	private String col10;
	private String col11;
	private String col12;
	private String col13;
	private String col14;
	private String col15;
	private String col16;
	private String col17;
	private String col18;
	private String col19;
	private String col20;
	private String col21;
	private String col22;
	private String col23;
	private String col24;
	private String col25;
	private String col26;
	private String col27;
	private String col28;
	private String col29;
	private String col30;
	private String col31;
	private String col32;
	private String col33;
	private String col34;
	private String col35;
	private String col36;
	private String col37;
	private String col38;
	private String col39;
	private String col40;
	private String col41;
	private String col42;
	private String col43;
	private String col44;
	private String col45;
	private String col46;
	private String col47;
	private String col48;
	private String col49;
	private String col50;
	private String col51;
	private String col52;
	private String col53;

	private String col90;
	private String col91;
	private String col92;
	private String col93;
	private String col94;
	private String col95;
	private String col96;
	private String col97;
	private String col98;
	private String col99;
/// RestData Only process until here, if more that 100 fields are need then RestData Must be adapted 
	private Date col2Date;
	private Date col3Date;
	private Date col4Date;
	private Date col10Date;
	public boolean isNew() {
		return col0 == null;
	}

	public String getPreConfParam() {
		return preConfParam;
	}


	public void setPreConfParam(String preConfParam) {
		this.preConfParam = preConfParam;
	}

	public String getResourceName() {
		return resourceName;
	}



	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}



	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public JsonNode getRowJSon() {
		return rowJSon;
	}



	public void setRowJSon(JsonNode rowJSon) {
		this.rowJSon = rowJSon;
	}



	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public StreamResource getStreamResource() {
		return streamResource;
	}

	public void setStreamResource(StreamResource streamResource) {
		this.streamResource = streamResource;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public JsonNode getRowColTypeList() {
		return rowColTypeList;
	}



	public void setRowColTypeList(JsonNode rowColTypeList) {
		this.rowColTypeList = rowColTypeList;
	}



	public ArrayList<String[]> getRowsColList() {
		return rowsColList;
	}



	public void setRowsColList(ArrayList<String[]> rowsColList2) {
		this.rowsColList = rowsColList2;
	}



	public DynamicDBean() {
	}
	
    public String getCol1() {
		return col1;
	}

    

	public void setCol1(String col1) {
		this.col1 = col1;
	}



	public String getCol0() {
		return col0;
	}



	public void setCol0(String col0) {
		this.col0 = col0;
	}



	public String getCol3() {
		return col3;
	}



	public void setCol3(String col3) {
		this.col3 = col3;
	}



	public String getCol4() {
		return col4;
	}



	public void setCol4(String col4) {
		this.col4 = col4;
	}



	public String getCol2() {
		return col2;
	}



	public void setCol2(String col2) {
		this.col2 = col2;
	}


	public Date getCol2Date() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		if (col2 != null)
			try {
	//			return formatter.parse(col2).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				return formatter.parse(col2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;//col3Date;
	}


	public void setCol2Date(Date col2Date) {
		this.col2Date = col2Date;
	}
	public void setCol10Date(Date col10Date) {
		this.col10Date = col10Date;
	}
	public Date getCol3Date() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		if (col3 != null)
			try {
				return formatter.parse(col3);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;//col3Date;
	}


	public void setCol3Date(Date col3Date) {
		this.col3Date = col3Date;
	}
	public Date getCol4Date() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		if (col4 != null)
			try {
				return formatter.parse(col4);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;//col3Date;
	}


	public void setCol4Date(Date col4Date) {
		this.col4Date = col4Date;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}

	public String getCol21() {
		return col21;
	}

	public void setCol21(String col21) {
		this.col21 = col21;
	}

	public String getCol22() {
		return col22;
	}

	public void setCol22(String col22) {
		this.col22 = col22;
	}

	public String getCol23() {
		return col23;
	}

	public void setCol23(String col23) {
		this.col23 = col23;
	}

	public String getCol24() {
		return col24;
	}

	public void setCol24(String col24) {
		this.col24 = col24;
	}

	public String getCol25() {
		return col25;
	}

	public void setCol25(String col25) {
		this.col25 = col25;
	}

	public String getCol26() {
		return col26;
	}

	public void setCol26(String col26) {
		this.col26 = col26;
	}

	public String getCol27() {
		return col27;
	}

	public void setCol27(String col27) {
		this.col27 = col27;
	}

	public String getCol28() {
		return col28;
	}

	public void setCol28(String col28) {
		this.col28 = col28;
	}

	public String getCol29() {
		return col29;
	}

	public void setCol29(String col29) {
		this.col29 = col29;
	}

	public String getCol30() {
		return col30;
	}

	public void setCol30(String col30) {
		this.col30 = col30;
	}

	public String getCol31() {
		return col31;
	}

	public void setCol31(String col31) {
		this.col31 = col31;
	}

	public String getCol32() {
		return col32;
	}

	public void setCol32(String col32) {
		this.col32 = col32;
	}

	public String getCol33() {
		return col33;
	}

	public void setCol33(String col33) {
		this.col33 = col33;
	}

	public String getCol34() {
		return col34;
	}

	public void setCol34(String col34) {
		this.col34 = col34;
	}

	public String getCol35() {
		return col35;
	}

	public void setCol35(String col35) {
		this.col35 = col35;
	}

	public String getCol36() {
		return col36;
	}

	public void setCol36(String col36) {
		this.col36 = col36;
	}

	public String getCol37() {
		return col37;
	}

	public void setCol37(String col37) {
		this.col37 = col37;
	}

	public String getCol38() {
		return col38;
	}

	public void setCol38(String col38) {
		this.col38 = col38;
	}

	public String getCol39() {
		return col39;
	}

	public void setCol39(String col39) {
		this.col39 = col39;
	}

	public String getCol40() {
		return col40;
	}

	public void setCol40(String col40) {
		this.col40 = col40;
	}

	public String getCol41() {
		return col41;
	}

	public void setCol41(String col41) {
		this.col41 = col41;
	}

	public String getCol42() {
		return col42;
	}

	public void setCol42(String col42) {
		this.col42 = col42;
	}

	public String getCol43() {
		return col43;
	}

	public void setCol43(String col43) {
		this.col43 = col43;
	}

	public String getCol44() {
		return col44;
	}

	public void setCol44(String col44) {
		this.col44 = col44;
	}

	public String getCol45() {
		return col45;
	}

	public void setCol45(String col45) {
		this.col45 = col45;
	}

	public String getCol46() {
		return col46;
	}

	public void setCol46(String col46) {
		this.col46 = col46;
	}

	public String getCol47() {
		return col47;
	}

	public void setCol47(String col47) {
		this.col47 = col47;
	}

	public String getCol48() {
		return col48;
	}

	public void setCol48(String col48) {
		this.col48 = col48;
	}

	public String getCol49() {
		return col49;
	}

	public void setCol49(String col49) {
		this.col49 = col49;
	}

	public String getCol50() {
		return col50;
	}

	public void setCol50(String col50) {
		this.col50 = col50;
	}

	public String getCol51() {
		return col51;
	}

	public void setCol51(String col51) {
		this.col51 = col51;
	}

	public String getCol52() {
		return col52;
	}

	public void setCol52(String col52) {
		this.col52 = col52;
	}

	public String getCol90() {
		return col90;
	}

	public void setCol90(String col90) {
		this.col90 = col90;
	}

	public String getCol91() {
		return col91;
	}

	public void setCol91(String col91) {
		this.col91 = col91;
	}

	public String getCol92() {
		return col92;
	}

	public void setCol92(String col92) {
		this.col92 = col92;
	}

	public String getCol93() {
		return col93;
	}

	public void setCol93(String col93) {
		this.col93 = col93;
	}

	public String getCol94() {
		return col94;
	}

	public void setCol94(String col94) {
		this.col94 = col94;
	}

	public String getCol95() {
		return col95;
	}

	public void setCol95(String col95) {
		this.col95 = col95;
	}

	public String getCol96() {
		return col96;
	}

	public void setCol96(String col96) {
		this.col96 = col96;
	}

	public String getCol97() {
		return col97;
	}

	public void setCol97(String col97) {
		this.col97 = col97;
	}

	public String getCol98() {
		return col98;
	}

	public void setCol98(String col98) {
		this.col98 = col98;
	}

	public String getCol99() {
		return col99;
	}

	public void setCol99(String col99) {
		this.col99 = col99;
	}

	public String getCol53() {
		return col53;
	}

	public void setCol53(String col53) {
		this.col53 = col53;
	}

//	public Object getColDate(int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public String getCol(String colName) {
//		System.out.println("DynamicDBean.getCol()" +i);
		Object dbean = this;
		try {
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);
			Method getColX = ((DynamicDBean.class)).getMethod(methodName );
			return (String) getColX.invoke(dbean);
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;//col3Date;
	}
	public void setColDate(Date colDate, String colName ) {
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			setColX.invoke(dbean,df.format(colDate));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Method getColX = ((DynamicDBean.class)).getMethod("setCol"+i);
//		this.col2Date = col2Date;
	}
	public LocalDate getColLocalDate(String colName) {
//		System.out.println("DynamicDBean.getColLocalDate() "+ colName);
//		return LocalDate.now();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			Method getColX = ((DynamicDBean.class)).getMethod(methodName);
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() > 4) // 4 is the length of null, and not date is so short
			return formatter.parse((String) getColX.invoke(dbean)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;//col3Date;
	}

	public Date getColDate(String colName) {
//		System.out.println("DynamicDBean.getColDate().."+colName);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Object dbean = this;
		try {
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);

			Method getColX = ((DynamicDBean.class)).getMethod(methodName);
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() > 4) // to avoid "null" as date has to be always bugger than 4 
		{
			
				return formatter.parse((String) getColX.invoke(dbean));		

	//			return formatter.parse(col2).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	//			return formatter.parse(col2);
		
		}
		}
			 catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;//col3Date;
	}

	public Integer getColInteger(String colName) {
		Object dbean = this;
		try {
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);

			Method getColX = ((DynamicDBean.class)).getMethod(methodName);
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() > 0)
		{
			
				String colValue = (String)getColX.invoke(dbean);
				int postPoint = colValue.indexOf(".");
				if (postPoint > -1)
					colValue = colValue.substring(0,postPoint ) + colValue.substring(postPoint + 1);
				return new Integer (colValue);		
		
		}
		}
			 catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	public void setColInteger(Integer v, String colName) {
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v+"");
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Object setColInteger(String v, String colName) {
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Boolean getColBoolean(String colName) {  // TODO  !!!!!!! adjust to values the returns LAC for booleans
		Object dbean = this;
		try {
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);

			Method getColX = ((DynamicDBean.class)).getMethod(methodName);
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() > 0)
		{
			
				String colValue = (String)getColX.invoke(dbean);
				if (colValue.equals("true"))
					return true;
				else
					return false;
		
		}
		}
			 catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	public Object setColBoolean(String v, String colName) {   // TODO @@CQR Adjust for booleans
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setCol(String v, String colName) {
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v+"");
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
