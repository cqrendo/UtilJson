package coop.intergal.vaadin.rest.utils;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.AppConst;



//@Entity
//@Bean
public class DynamicDBean {// implements Serializable {
//	private static final long serialVersionUID = 1L;

	private String preConfParam;
	private String resourceName; 
	private String filter; 
	private JsonNode rowJSon;
	private boolean isReadOnly ;
	private boolean insertNotAllow ;
	private boolean deleteNotAllow ;
	private boolean updateNotAllow ;
	private String params ;
//	private StreamResource streamResource;
	private InputStream inputStream; // is use to keep the blob data read from LAC
	private byte[] bytes;  // is use to save data into LAC read in a upload  
	JsonNode rowColTypeList;
	private ArrayList<String[]> rowsColList = new ArrayList<String[]>();
	private int maxColNumber ;
	private String methodForRowSelected ;
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
	private String col54;
	private String col55;
	private String col56;
	private String col57;
	private String col58;
	private String col59;
	private String col60;
	private String col61;
	private String col62;
	private String col63;
	private String col64;
	private String col65;
	private String col66;
	private String col67;
	private String col68;
	private String col69;
	private String col70;
	private String col71;
	private String col72;
	private String col73;
	private String col74;
	private String col75;
	private String col76;
	private String col77;
	private String col78;
	private String col79;
	private String col80;
	private String col81;
	private String col82;
	private String col83;
	private String col84;
	private String col85;
	private String col86;
	private String col87;
	private String col88;
	private String col89;
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
	private String col100;
	private String col101;
	private String col102;
	private String col103;
	private String col104;
	private String col105;
	private String col106;
	private String col107;
	private String col108;
	private String col109;
	private String col110;
	private String col111;
	private String col112;
	private String col113;
	private String col114;
	private String col115;
	private String col116;
	private String col117;
	private String col118;
	private String col119;
	private String col120;
	private String col121;
	private String col122;
	private String col123;
	private String col124;
	private String col125;
	private String col126;
	private String col127;
	private String col128;
	private String col129;
	private String col130;
	private String col131;
	private String col132;
	private String col133;
	private String col134;
	private String col135;
	private String col136;
	private String col137;
	private String col138;
	private String col139;
	private String col140;
	private String col151;
	private String col152;
	private String col153;
	private String col154;
	private String col155;
	private String col156;
	private String col157;
	private String col158;
	private String col159;
	private String col150;
	
/// RestData Only process until here, if more that 150 fields are need then RestData Must be adapted 
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

//	public StreamResource getStreamResource() {
//		return streamResource;
//	}
//
//	public void setStreamResource(StreamResource streamResource) {
//		this.streamResource = streamResource;
//	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
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
	
    public int getMaxColNumber() {
		return maxColNumber;
	}

	public void setMaxColNumber(int maxColNumber) {
		this.maxColNumber = maxColNumber;
	}

	public String getMethodForRowSelected() {
		return methodForRowSelected;
	}

	public void setMethodForRowSelected(String methodForRowSelected) {
		this.methodForRowSelected = methodForRowSelected;
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
				e.printStackTrace();
			}
		return null;//col3Date;
	}


//	public void setCol2Date(Date col2Date) {
//		this.col2Date = col2Date;
//	}
//	public void setCol10Date(Date col10Date) {
//		this.col10Date = col10Date;
//	}
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


//	public void setCol3Date(Date col3Date) {
//		this.col3Date = col3Date;
//	}
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


//	public void setCol4Date(Date col4Date) {
//		this.col4Date = col4Date;
//	}

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

	public String getCol54() {
		return col54;
	}

	public void setCol54(String col54) {
		this.col54 = col54;
	}

	public String getCol55() {
		return col55;
	}

	public void setCol55(String col55) {
		this.col55 = col55;
	}

	public String getCol56() {
		return col56;
	}

	public void setCol56(String col56) {
		this.col56 = col56;
	}

	public String getCol57() {
		return col57;
	}

	public void setCol57(String col57) {
		this.col57 = col57;
	}

	public String getCol58() {
		return col58;
	}

	public void setCol58(String col58) {
		this.col58 = col58;
	}

	public String getCol59() {
		return col59;
	}

	public void setCol59(String col59) {
		this.col59 = col59;
	}

	public String getCol60() {
		return col60;
	}

	public void setCol60(String col60) {
		this.col60 = col60;
	}

	public String getCol61() {
		return col61;
	}

	public void setCol61(String col61) {
		this.col61 = col61;
	}

	public String getCol62() {
		return col62;
	}

	public void setCol62(String col62) {
		this.col62 = col62;
	}

	public String getCol63() {
		return col63;
	}

	public void setCol63(String col63) {
		this.col63 = col63;
	}

	public String getCol64() {
		return col64;
	}

	public void setCol64(String col64) {
		this.col64 = col64;
	}

	public String getCol65() {
		return col65;
	}

	public void setCol65(String col65) {
		this.col65 = col65;
	}

	public String getCol66() {
		return col66;
	}

	public void setCol66(String col66) {
		this.col66 = col66;
	}

	public String getCol67() {
		return col67;
	}

	public void setCol67(String col67) {
		this.col67 = col67;
	}

	public String getCol68() {
		return col68;
	}

	public void setCol68(String col68) {
		this.col68 = col68;
	}

	public String getCol69() {
		return col69;
	}

	public void setCol69(String col69) {
		this.col69 = col69;
	}

	public String getCol70() {
		return col70;
	}

	public void setCol70(String col70) {
		this.col70 = col70;
	}

	public String getCol71() {
		return col71;
	}

	public void setCol71(String col71) {
		this.col71 = col71;
	}

	public String getCol72() {
		return col72;
	}

	public void setCol72(String col72) {
		this.col72 = col72;
	}

	public String getCol73() {
		return col73;
	}

	public void setCol73(String col73) {
		this.col73 = col73;
	}

	public String getCol74() {
		return col74;
	}

	public void setCol74(String col74) {
		this.col74 = col74;
	}

	public String getCol75() {
		return col75;
	}

	public void setCol75(String col75) {
		this.col75 = col75;
	}

	public String getCol76() {
		return col76;
	}

	public void setCol76(String col76) {
		this.col76 = col76;
	}

	public String getCol77() {
		return col77;
	}

	public void setCol77(String col77) {
		this.col77 = col77;
	}

	public String getCol78() {
		return col78;
	}

	public void setCol78(String col78) {
		this.col78 = col78;
	}

	public String getCol79() {
		return col79;
	}

	public void setCol79(String col79) {
		this.col79 = col79;
	}

	public String getCol80() {
		return col80;
	}

	public void setCol80(String col80) {
		this.col80 = col80;
	}

	public String getCol81() {
		return col81;
	}

	public void setCol81(String col81) {
		this.col81 = col81;
	}

	public String getCol82() {
		return col82;
	}

	public void setCol82(String col82) {
		this.col82 = col82;
	}

	public String getCol83() {
		return col83;
	}

	public void setCol83(String col83) {
		this.col83 = col83;
	}

	public String getCol84() {
		return col84;
	}

	public void setCol84(String col84) {
		this.col84 = col84;
	}

	public String getCol85() {
		return col85;
	}

	public void setCol85(String col85) {
		this.col85 = col85;
	}

	public String getCol86() {
		return col86;
	}

	public void setCol86(String col86) {
		this.col86 = col86;
	}

	public String getCol87() {
		return col87;
	}

	public void setCol87(String col87) {
		this.col87 = col87;
	}

	public String getCol88() {
		return col88;
	}

	public void setCol88(String col88) {
		this.col88 = col88;
	}

	public String getCol89() {
		return col89;
	}

	public void setCol89(String col89) {
		this.col89 = col89;
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

	public String getCol100() {
		return col100;
	}

	public void setCol100(String col100) {
		this.col100 = col100;
	}

	public String getCol101() {
		return col101;
	}

	public void setCol101(String col101) {
		this.col101 = col101;
	}

	public String getCol102() {
		return col102;
	}

	public void setCol102(String col102) {
		this.col102 = col102;
	}

	public String getCol103() {
		return col103;
	}

	public void setCol103(String col103) {
		this.col103 = col103;
	}

	public String getCol104() {
		return col104;
	}

	public void setCol104(String col104) {
		this.col104 = col104;
	}

	public String getCol105() {
		return col105;
	}

	public void setCol105(String col105) {
		this.col105 = col105;
	}

	public String getCol106() {
		return col106;
	}

	public void setCol106(String col106) {
		this.col106 = col106;
	}

	public String getCol107() {
		return col107;
	}

	public void setCol107(String col107) {
		this.col107 = col107;
	}

	public String getCol108() {
		return col108;
	}

	public void setCol108(String col108) {
		this.col108 = col108;
	}

	public String getCol109() {
		return col109;
	}

	public void setCol109(String col109) {
		this.col109 = col109;
	}

	public String getCol110() {
		return col110;
	}

	public void setCol110(String col110) {
		this.col110 = col110;
	}

	public String getCol111() {
		return col111;
	}

	public void setCol111(String col111) {
		this.col111 = col111;
	}

	public String getCol112() {
		return col112;
	}

	public void setCol112(String col112) {
		this.col112 = col112;
	}

	public String getCol113() {
		return col113;
	}

	public void setCol113(String col113) {
		this.col113 = col113;
	}

	public String getCol114() {
		return col114;
	}

	public void setCol114(String col114) {
		this.col114 = col114;
	}

	public String getCol115() {
		return col115;
	}

	public void setCol115(String col115) {
		this.col115 = col115;
	}

	public String getCol116() {
		return col116;
	}

	public void setCol116(String col116) {
		this.col116 = col116;
	}

	public String getCol117() {
		return col117;
	}

	public void setCol117(String col117) {
		this.col117 = col117;
	}

	public String getCol118() {
		return col118;
	}

	public void setCol118(String col118) {
		this.col118 = col118;
	}

	public String getCol119() {
		return col119;
	}

	public void setCol119(String col119) {
		this.col119 = col119;
	}

	public String getCol120() {
		return col120;
	}

	public void setCol120(String col120) {
		this.col120 = col120;
	}

	public String getCol121() {
		return col121;
	}

	public void setCol121(String col121) {
		this.col121 = col121;
	}

	public String getCol122() {
		return col122;
	}

	public void setCol122(String col122) {
		this.col122 = col122;
	}

	public String getCol123() {
		return col123;
	}

	public void setCol123(String col123) {
		this.col123 = col123;
	}

	public String getCol124() {
		return col124;
	}

	public void setCol124(String col124) {
		this.col124 = col124;
	}

	public String getCol125() {
		return col125;
	}

	public void setCol125(String col125) {
		this.col125 = col125;
	}

	public String getCol126() {
		return col126;
	}

	public void setCol126(String col126) {
		this.col126 = col126;
	}

	public String getCol127() {
		return col127;
	}

	public void setCol127(String col127) {
		this.col127 = col127;
	}

	public String getCol128() {
		return col128;
	}

	public void setCol128(String col128) {
		this.col128 = col128;
	}

	public String getCol129() {
		return col129;
	}

	public void setCol129(String col129) {
		this.col129 = col129;
	}

	public String getCol130() {
		return col130;
	}

	public void setCol130(String col130) {
		this.col130 = col130;
	}

	public String getCol131() {
		return col131;
	}

	public void setCol131(String col131) {
		this.col131 = col131;
	}

	public String getCol132() {
		return col132;
	}

	public void setCol132(String col132) {
		this.col132 = col132;
	}

	public String getCol133() {
		return col133;
	}

	public void setCol133(String col133) {
		this.col133 = col133;
	}

	public String getCol134() {
		return col134;
	}

	public void setCol134(String col134) {
		this.col134 = col134;
	}

	public String getCol135() {
		return col135;
	}

	public void setCol135(String col135) {
		this.col135 = col135;
	}

	public String getCol136() {
		return col136;
	}

	public void setCol136(String col136) {
		this.col136 = col136;
	}

	public String getCol137() {
		return col137;
	}

	public void setCol137(String col137) {
		this.col137 = col137;
	}

	public String getCol138() {
		return col138;
	}

	public void setCol138(String col138) {
		this.col138 = col138;
	}

	public String getCol139() {
		return col139;
	}

	public void setCol139(String col139) {
		this.col139 = col139;
	}

	public String getCol140() {
		return col140;
	}

	public void setCol140(String col140) {
		this.col140 = col140;
	}

	public String getCol151() {
		return col151;
	}

	public void setCol151(String col151) {
		this.col151 = col151;
	}

	public String getCol152() {
		return col152;
	}

	public void setCol152(String col152) {
		this.col152 = col152;
	}

	public String getCol153() {
		return col153;
	}

	public void setCol153(String col153) {
		this.col153 = col153;
	}

	public String getCol154() {
		return col154;
	}

	public void setCol154(String col154) {
		this.col154 = col154;
	}

	public String getCol155() {
		return col155;
	}

	public void setCol155(String col155) {
		this.col155 = col155;
	}

	public String getCol156() {
		return col156;
	}

	public void setCol156(String col156) {
		this.col156 = col156;
	}

	public String getCol157() {
		return col157;
	}

	public void setCol157(String col157) {
		this.col157 = col157;
	}

	public String getCol158() {
		return col158;
	}

	public void setCol158(String col158) {
		this.col158 = col158;
	}

	public String getCol159() {
		return col159;
	}

	public void setCol159(String col159) {
		this.col159 = col159;
	}

	public String getCol150() {
		return col150;
	}

	public void setCol150(String col150) {
		this.col150 = col150;
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
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;//col3Date;
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public Double getColDouble(String colName) {
//		System.out.println("DynamicDBean.getCol()" +i);
		Object dbean = this;
		try {
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);
			Method getColX = ((DynamicDBean.class)).getMethod(methodName );
			if ( getColX.invoke(dbean).equals("null"))
				return null;
			return Double.valueOf((String) getColX.invoke(dbean));
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
//		Method getColX = ((DynamicDBean.class)).getMethod("setCol"+i);
//		this.col2Date = col2Date;
	}
	public void setColDate(LocalDate colDate, String colName ) {
		Object dbean = this;
		try {
		
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (colDate != null )
				setColX.invoke(dbean,df.format(java.sql.Date.valueOf(colDate)));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
//		Method getColX = ((DynamicDBean.class)).getMethod("setCol"+i);
//		this.col2Date = col2Date;
// catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public LocalDate getColLocalDate(String colName) {
//		System.out.println("DynamicDBean.getColLocalDate() "+ colName);
//		return LocalDate.now();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		DateFormat formatterShort = new SimpleDateFormat("yyyy-MM-dd");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			Method getColX = ((DynamicDBean.class)).getMethod(methodName);
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() == 10) // 4 is the length of null, and not date is so short
			return formatterShort.parse((String) getColX.invoke(dbean)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
	
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() > 4) // 4 is the length of null, and not date is so short
			return formatter.parse((String) getColX.invoke(dbean)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;//col3Date;
	}

	public Date getColDate(String colName) {
//		System.out.println("DynamicDBean.getColDate().."+colName);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		DateFormat formatterShort = new SimpleDateFormat("yyyy-MM-dd");//.XXX");//("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		Object dbean = this;
		try {
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);

			Method getColX = ((DynamicDBean.class)).getMethod(methodName);
			if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() == 10) // to avoid "null" as date has to be always bugger than 4 
			{
				
					return formatterShort.parse((String) getColX.invoke(dbean));		
			}		
	
		if (getColX.invoke(dbean)!= null && getColX.invoke(dbean).toString().length() > 4) // to avoid "null" as date has to be always bugger than 4 
		{
			
				return formatter.parse((String) getColX.invoke(dbean));		

	//			return formatter.parse(col2).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	//			return formatter.parse(col2);
		
		}
		}
			 catch (ParseException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
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
				if ( colValue.equals("null"))
					return null;
				return new Integer (colValue);		
		
		}
		}
			 catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		return null;
	}

	public void setColInteger(Integer v, String colName) {		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v+"");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
				if (colValue.equals("true") || (colValue.equals("1")))
					return true;
				else
					return false;
		
		}
		}
			 catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		return false;
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void setColDouble(Double v, String colName) {
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v+"");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public BigDecimal getColBigDecimal(String colName) {
		Object dbean = this;
		try {
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);
			Method getColX = ((DynamicDBean.class)).getMethod(methodName );
			if ( getColX.invoke(dbean).equals("null"))
				return null;
			return new BigDecimal ((String) getColX.invoke(dbean));
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
	public void setColBigDecimal(BigDecimal v, String colName) {
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v+"");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public Object setColBoolean(Boolean v, String colName) {
		Object dbean = this;
		String value = AppConst.VALUE_FALSE_FOR_BOOLEANS;
		if (v!=null && v)
			value = AppConst.VALUE_TRUE_FOR_BOOLEANS;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,value);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Object setColBoolean(Boolean v) {
		return true;
		
	}
	public Boolean isXX()
	{
		return true;
	}

	public String getColDecimalPoint(String colName, int nDecimals) { 
//		System.out.println("DynamicDBean.getCol()" +i);
		Object dbean = this;
		try {
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			String methodName = "getCol" + colName;
			if (colName.startsWith("col"))
				methodName= "getC" + colName.substring(1);
			Method getColX = ((DynamicDBean.class)).getMethod(methodName );
			String value = ((String) getColX.invoke(dbean));
			if (value == null || value.isEmpty())
				return null;
			value = String.format("%."+nDecimals+"f",Double.valueOf(value));
			return value.replace(".",",");
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;//col3Date;
	}

	public void setColDecimalPoint(String v, String colName) {
		System.out.println("DynamicDBean.setColDecimalPoint() VALUE "+ v);
		v = v.replace(("."), "");
		v = v.replace((","), ".");
		System.out.println("DynamicDBean.setColDecimalPoint() VALUE "+ v);
		Object dbean = this;
		try {
			String methodName = "setCol" + colName;
			if (colName.startsWith("col"))
				methodName= "setC" + colName.substring(1);
			Method setColX = ((DynamicDBean.class)).getMethod(methodName, new Class[] {java.lang.String.class} );
			setColX.invoke(dbean,v+"");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public String getValueAfterMark(int i, String mark) {
//		System.out.println("DynamicDBean.getCol()" +i);
		
		Object dbean = this;
//		if (getCol0().equals("18:00"))
//		{
//			System.out.println("DynamicDBean.getValueAfterMark() TO STOP DEBUG");
//		}	
		try
		{
//			Method setColXDate = ((DynamicDBean.class)).getMethod("setCol"+i+"Date", new Class[] {java.util.Date.class} );
			Method getColX = ((DynamicDBean.class)).getMethod("getCol"+i);
			String value =  (String) getColX.invoke(dbean);
			int idxMark = 0;
			if(value !=null)
				idxMark = value.indexOf(mark)+1;
			if (value == null)
				return "";
			return value.substring(idxMark);
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

	public boolean isInsertNotAllow() {
		return insertNotAllow;
	}

	public void setInsertNotAllow(boolean insertNotAllow) {
		this.insertNotAllow = insertNotAllow;
	}

	public boolean isDeleteNotAllow() {
		return deleteNotAllow;
	}

	public void setDeleteNotAllow(boolean deleteNotAllow) {
		this.deleteNotAllow = deleteNotAllow;
	}

	public boolean isUpdateNotAllow() {
		return updateNotAllow;
	}

	public void setUpdateNotAllow(boolean updateNotAllow) {
		this.updateNotAllow = updateNotAllow;
	}


	
}
