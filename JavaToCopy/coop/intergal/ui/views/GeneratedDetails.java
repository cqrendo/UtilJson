package coop.intergal.ui.views;


import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;

import coop.intergal.AppConst;
import coop.intergal.ui.components.EsDatePicker;
import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.components.detailsdrawer.DetailsDrawer;
import coop.intergal.ui.util.FontSize;
import coop.intergal.ui.util.LumoStyles;
import coop.intergal.ui.util.TextColor;
import coop.intergal.ui.util.UIUtils;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

//@PageTitle("Payments")
//@Route(value = "gridDetails", layout = MainLayout.class)
import static coop.intergal.AppConst.STYLES_CSS;
import static coop.intergal.AppConst.STYLES_FORM_ITEM_CSS;

@CssImport(value = STYLES_CSS, themeFor="dynamic-grid-display")
@CssImport(value = STYLES_FORM_ITEM_CSS, themeFor = "vaadin-form-item")
public class GeneratedDetails extends FormLayout{//ViewFrame implements HasDynamicTitle, BeforeEnterObserver {//, AfterNavigationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid<DynamicDBean> grid;
 //   private ListDataProvider<Payment> dataProvider;
	private DdbDataBackEndProvider dataProvider;
    private DetailsDrawer detailsDrawer;
	private Binder<DynamicDBean> binder;
	private FormLayout form;
	private ArrayList<String[]> rowsColList;
	private ArrayList<String[]> rowsFieldList;
//	private ArrayList<String[]> rowsColListGrid;
	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	private Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param

	private String title;
	private String resource;
	private boolean cache = true; // @@TODO fill whwn call
	private DynamicDBean bean;



    
    public DynamicDBean getBean() {
		return bean;
	}

	public void setBean(DynamicDBean bean) {
		this.bean = bean;
	}

	public DdbDataBackEndProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DdbDataBackEndProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public Binder<DynamicDBean> getBinder() {
		return binder;
	}

	public void setBinder(Binder<DynamicDBean> binder) {
		this.binder = binder;
	}

	public ArrayList<String[]> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String[]> rowsColList) {
		this.rowsColList = rowsColList;
	}

	@Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
 //       initAppBar();
 
 //       setViewContent(createContent());
 //       setViewDetails(createDetailsDrawer());
 //       filter();
    }

//    private void initAppBar() {
//        AppBar appBar = MainLayout.get().getAppBar();
//        appBar.addTabSelectionListener(e -> {
// //           filter();
//            detailsDrawer.hide();
//        });
//        appBar.centerTabs();
//    }

   
  
    private boolean isCurrency(String header, String colType) {
    	if (header.startsWith("C#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
    		return true; 
    	if (colType.equals("3"))
    		return true;
    	return false;
    }


    private boolean isDate(String header, String colType) {
    	if (header.startsWith("D#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
    		return true; 
    	if (colType.equals("1"))
    		return true;
    	return false;
    }
    private boolean isBoolean(String header, String colType) {
    	if (colType.equals("4"))
    		return true;
    	return false;
    }
 
//    private DetailsDrawer createDetailsDrawer() {
//        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);
//
// 
// //       DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader(getPageTitle());//), tabs);
//        detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawerHide());
//        detailsDrawer.setHeader(detailsDrawerHeader);
//        // Footer
//        DetailsDrawerFooter footer = new DetailsDrawerFooter();
//        footer.addSaveListener(e -> {
//        	dataProvider.save(resource, beansToSaveAndRefresh );
//        	detailsDrawerHide();
//            UIUtils.showNotification("Changes saved.");
//        });
//        footer.addCancelListener(e -> detailsDrawerHide());
//        detailsDrawer.setFooter(footer);
//        return detailsDrawer;
//    }

    private Object detailsDrawerHide() {
    	detailsDrawer.setMaxWidth("0px");
    	detailsDrawer.hide();
		return null;
	}

	private void showDetails(DynamicDBean dynamicDBean) {
		beansToSaveAndRefresh.clear();	
		beansToSaveAndRefresh.put(dynamicDBean.getResourceName(), dynamicDBean);
        detailsDrawer.setContent(createDetails());
        detailsDrawer.setWidthFull();
        detailsDrawer.setMaxWidth("1150px");
        detailsDrawer.show();
       
    }
	  public Component createContent() {
	    		
	    FlexBoxLayout content = new FlexBoxLayout(createDetails());
	    //		content.setBoxSizing(BoxSizing.BORDER_BOX);
	    //		content.setHeightFull();
	    //		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
//	    		content.setWidthFull();
//	    		content.setMaxWidth("1200px"); // if we want reposponsive we must uncomment this and previous, comment next
	    content.setWidth(AppConst.DEFAULT_WIDTH_FORM);
	    Div header = new Div(); 
	    header.setClassName("formTitle");
	    header.add(UIUtils.createH3Label(title));
	    Div headearAndForm = new Div(); 
	    headearAndForm.add(header);
	    headearAndForm.add(content);
	    return headearAndForm;
	    }

	private Component createDetails() {
			GeneratedUtil generatedUtil = new GeneratedUtil();
			generatedUtil.setBean(bean);
	//		if (cache == false)
			rowsFieldList = dataProvider.getRowsFieldList();
			String tabs ="";
	   		if (rowsFieldList != null)
			{
				tabs = rowsFieldList.get(0)[17];
			}
//			if (form == null)
//				form = new FormLayout();
			if (tabs.isEmpty()) 
			{
				return generatedUtil.createDetails(rowsFieldList, false, cache,"noTAB");
			}
			else
			{
				return generatedUtil.createTabs(rowsFieldList, false,cache,tabs);
			}
	//		return details;
	}
	private Component createDetailsOLD() {
				
			
			this.binder = new Binder<DynamicDBean>(DynamicDBean.class);
			rowsFieldList = dataProvider.getRowsFieldList();
		    if (bean != null)
				binder.setBean(bean);

			Iterator<String[]> itRowsFieldList = rowsFieldList.iterator();
			if (form == null)
				form = new FormLayout();
			form.removeAll();
			form.setResponsiveSteps(
//				new ResponsiveStep("31em",1),
//				new ResponsiveStep("32em",2),
//				new ResponsiveStep("33em",3),
//				new ResponsiveStep("34em",4),
//				new ResponsiveStep("45em",5),
//				new ResponsiveStep("46em",6),
//				new ResponsiveStep("47em",7),
//				new ResponsiveStep("48em",8),
//				new ResponsiveStep("49em",9),
//				new ResponsiveStep("50em",10),
//				new ResponsiveStep("51em",11),
//				new ResponsiveStep("52em",12),
//				new ResponsiveStep("63em",13),
//				new ResponsiveStep("64em",14),
//				new ResponsiveStep("65em",15),
//				new ResponsiveStep("66em",16));
					calculateResponsiveStep(1),
					calculateResponsiveStep(2),
					calculateResponsiveStep(3),
					calculateResponsiveStep(4),
					calculateResponsiveStep(5),
					calculateResponsiveStep(6),
					calculateResponsiveStep(7),
					calculateResponsiveStep(8),
					calculateResponsiveStep(9),
					calculateResponsiveStep(10),
					calculateResponsiveStep(11),
					calculateResponsiveStep(12),
					calculateResponsiveStep(13),
					calculateResponsiveStep(14),
					calculateResponsiveStep(15),
					calculateResponsiveStep(16),
					calculateResponsiveStep(17),
					calculateResponsiveStep(18),
					calculateResponsiveStep(19),
					calculateResponsiveStep(20),
					calculateResponsiveStep(21),
					calculateResponsiveStep(22),
					calculateResponsiveStep(23),
					calculateResponsiveStep(24),
					calculateResponsiveStep(25),
					calculateResponsiveStep(26),
					calculateResponsiveStep(27),
					calculateResponsiveStep(28),
					calculateResponsiveStep(29),
					calculateResponsiveStep(30),
					calculateResponsiveStep(31),
					calculateResponsiveStep(32),
			calculateResponsiveStep(33),
			calculateResponsiveStep(34),
			calculateResponsiveStep(35),
			calculateResponsiveStep(36),
			calculateResponsiveStep(37),
			calculateResponsiveStep(38),
			calculateResponsiveStep(39),
			calculateResponsiveStep(40));
					
//					new ResponsiveStep("15em",1),
//					new ResponsiveStep("16em",2),
//					new ResponsiveStep("16.5em",3),
//					new ResponsiveStep("17em",4),
//					new ResponsiveStep("17.5em",5),
//					new ResponsiveStep("18em",6),
//					new ResponsiveStep("18.5em",7),
//					new ResponsiveStep("19em",8),
//					new ResponsiveStep("19.5em",9),
//					new ResponsiveStep("20em",10),
//					new ResponsiveStep("20.5em",11),
//					new ResponsiveStep("21em",12),
//					new ResponsiveStep("21.5em",13),
//					new ResponsiveStep("22em",14),
//					new ResponsiveStep("22.5em",15),
//					new ResponsiveStep("23em",16),
//					new ResponsiveStep("23.5em",17),
//					new ResponsiveStep("24em",18),
//					new ResponsiveStep("24.5em",19),
//					new ResponsiveStep("25em",20),
//					new ResponsiveStep("25.5em",21),
//					new ResponsiveStep("26em",22),
//					new ResponsiveStep("26.5em",23),
//					new ResponsiveStep("37em",24),
//					new ResponsiveStep("37.5em",25),
//					new ResponsiveStep("38em",26),
//					new ResponsiveStep("38.5em",27),
//					new ResponsiveStep("58em",28),
//					new ResponsiveStep("62em",19),
//					new ResponsiveStep("66em",30),
//					new ResponsiveStep("70.5em",31),
//					new ResponsiveStep("74em",32));
			
			int i = 0;
			int ii = 0;
			Div div = new Div();
		//	FormLayout.FormItem item = formLayout.addFormItem(phoneLayout, "Phone");
//			form.setColSpan(item, 2);
			int nRow = 0;
			while (itRowsFieldList.hasNext())
			{
//				Label label = new Label(itRowsColList.next()[0]);
//				label.setWidth("500px");
				String[] rowField = itRowsFieldList.next();
				String filedName = rowField[0];
				boolean isReadOnly = isReadOnly( rowField [1]);
				String label = rowField[6];
				String fieldNameInUI = rowField[2];
				String idFieldType = rowField[3];
				String fieldWidth = rowField[7];
				String classNames =  rowField[8];
				String classNamesForm = ""; 
				String classNamesItem = ""; 
				String [] tokens = classNames.split(Pattern.quote("."));
				int iii = 0;
				while (tokens.length > iii)
				{ 
					if (tokens[iii].indexOf("form") > -1 )  // form CSS must include form in his name
						classNamesForm = classNamesForm + "." + tokens[iii];
					else
						classNamesItem = classNamesItem + "." + tokens[iii];
					iii ++;
				}
				if (nRow == 0)
				{
					form.setClassName("");
					form = addClassNames(form,classNamesForm.trim());
					title = rowField[9];
					
				}
		
				nRow ++;
				
				System.out.println("DetailsPreview.createDetails()" +" filedName " + filedName + " "+classNames  );

				if( fieldWidth.isEmpty())
					fieldWidth = "10";
				
				int idxMark = fieldWidth.indexOf("#");
				Integer colspan = 0;
				if (idxMark == -1)
				{
					colspan = new Integer (fieldWidth);
					fieldWidth = (colspan)+1+"em";
				}
				else
				{
					colspan = new Integer (fieldWidth.substring(0,idxMark));
					fieldWidth = fieldWidth.substring(idxMark+1)+"em";
				}
				
				TextField tf = new TextField();//itRowsColList.next()[0]);
				tf.setReadOnly(isReadOnly);
				tf.addThemeVariants(TextFieldVariant.LUMO_SMALL);
				if (filedName.equals("#SPACE#"))
				{
					Span s = new Span();
					FormLayout.FormItem item = form.addFormItem(s, label );
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
				}
				else if (idFieldType.equals("1")) // is Date
				{
					EsDatePicker dp = new EsDatePicker();
					dp.getElement().setAttribute("theme", "small");
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
					binder.forField((EsDatePicker) dp)
					.withConverter(new LocalDateToDateConverter( ZoneId.systemDefault()))
					.bind(d-> d.getColDate(fieldNameInUI), (d,v)-> d.setColDate(v,fieldNameInUI));//DynamicDBean::setCol2Date);	
					FormLayout.FormItem item = form.addFormItem(dp, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					item = addClassNames(item, classNamesItem);
					form.setColspan(item, colspan);
					dp.setWidth(fieldWidth);
				}
				else
				{
					binder.bind(tf, fieldNameInUI);
//				form.add(fi);
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
					FormLayout.FormItem item = form.addFormItem(tf, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					item = addClassNames(item, classNamesItem);
					form.setColspan(item, colspan);
					tf.setWidth(fieldWidth);
				}
				i++;
				
			}
			return form;
	    }
	private ResponsiveStep calculateResponsiveStep(int i) {
		int em = i * 22;
		String strEm =  em +"";
		strEm = strEm.substring(0,strEm.length()-1 ); 
//		System.out.println("GenericGridDetails.calculateResponsiveStep()......"+ strEm + "em");
		return new ResponsiveStep(strEm+"em",i);
	}
	private FormItem addClassNames(FormItem item, String classNames) {
		StringTokenizer tokens = new StringTokenizer(classNames,".");
		while (tokens.hasMoreElements())
		{
			String eachClass = tokens.nextToken();
			item.addClassName(eachClass);
		}
	return item;
}
	private FormLayout addClassNames(FormLayout formLayout, String classNames) {
		StringTokenizer tokens = new StringTokenizer(classNames,".");
		while (tokens.hasMoreElements())
		{
			String eachClass = tokens.nextToken();
			formLayout.addClassName(eachClass);
		}
	return formLayout;
}

	private Div alingLabel(String label) {
		Div l = new Div();
		l.add(label);
		if (label.endsWith("#"))
			{
			l.addClassName("labelright");
			label=label.substring(0,label.length()-1);
			l.setText(label);
			}
		return l;
	}

	private boolean isReadOnly(String params) {
		
		if (params == null)
			return false;
		if (params.indexOf("#CNoEDT#")>-1)
			return true;
		else 
			return false;
			
	}

    private Component createAttachments() {
        Label message = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
        message.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        return message;
    }

    private Component createHistory() {
        Label message = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
        message.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        return message;
    }

//	@Override
//	public String getPageTitle() {
//		// TODO Auto-generated method stub
//		if(title == null)
//			return "SIN TITULO";
//		return title;
//	}
//
//	@Override
//	public void beforeEnter(BeforeEnterEvent event) {
//		System.out.println("GenericGridDetails.beforeEnter()");
//		Location location = event.getLocation();
//	    QueryParameters queryParameters = location
//	            .getQueryParameters();
//
//	    Map<String, List<String>> parametersMap =
//	            queryParameters.getParameters();
//	    System.out.println("beforeEnter.....parametersMap.get(\"resource\")"+parametersMap.get("resource"));
//		
//	       title = parametersMap.get("title").get(0);	
//	       resource = parametersMap.get("resource").get(0);
//	       resource = parametersMap.get("resource").get(0);
////	       if (parametersMap.get("params")  != null)
////	    	   params = parametersMap.get("params").get(0);
//		    if ( parametersMap.get("cache") != null)
//		    {
//			    System.out.println("beforeEnter GenericGridDetails.....parametersMap.get(\"cache\")"+parametersMap.get("cache"));
//		    	if (parametersMap.get("cache").get(0).equalsIgnoreCase("false"))
//		    	{
//		    		cache = false;
//		    	}		    			
//		    }	
//		    else
//		    	cache  =true; 
//	       // @@ TODO get fllter by param and use to ResData.getOneRow 
////	       showDetails(dynamicDBean);
////	       setViewContent(createContent(resource));
////	        setViewDetails(createDetailsDrawer());
//
//		
//	}



//	@Override
//	public void afterNavigation(AfterNavigationEvent event) {
//		Location location = event.getLocation();
//	    QueryParameters queryParameters = location
//	            .getQueryParameters();
//
//	    Map<String, List<String>> parametersMap =
//	            queryParameters.getParameters();
//	    System.out.println("afterNavigation.setParameter()....."+parametersMap.get("resource"));
//		
//
//		
//	}

 
}
