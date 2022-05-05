package coop.intergal.ui.views;


import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.components.EsDatePicker;
import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.components.detailsdrawer.DetailsDrawer;
import coop.intergal.ui.util.FontSize;
import coop.intergal.ui.util.GenericClassForMethods;
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
import static coop.intergal.AppConst.STYLES_FORM_LAYOUT_ITEM_CSS;

@Tag("generated-details")
@CssImport(value = STYLES_CSS, themeFor="dynamic-grid-display")
@CssImport(value = STYLES_FORM_ITEM_CSS, themeFor = "vaadin-form-item")
@CssImport(value = STYLES_FORM_LAYOUT_ITEM_CSS, themeFor = "vaadin-form-layout")
@JsModule("./src/views/generic/forms/generated-details.js")
public class GeneratedDetails extends PolymerTemplate<TemplateModel> {//extends FormLayout{//ViewFrame implements HasDynamicTitle, BeforeEnterObserver {//, AfterNavigationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid<DynamicDBean> grid;
	private DynamicViewGrid dVGrid;

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
	private Div divSubGrid; 
	
	GeneratedUtil generatedUtil = new GeneratedUtil();
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
	private FormButtonsBar buttonsForm;
	private GenericClassForMethods genericClassForMethods;
	public FormButtonsBar getButtonsForm() {
		return buttonsForm;
	}

	public void setButtonsForm(FormButtonsBar buttonsForm) {
		this.buttonsForm = buttonsForm;
	}
	public GeneratedDetails() {
		super();
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

	public DynamicViewGrid getDVGrid() {
		return dVGrid;
	}

	public void setDVGrid(DynamicViewGrid dVGrid) {
		this.dVGrid = dVGrid;
	}

  
    public Div getDivSubGrid() {
		return divSubGrid;
	}

	public void setDivSubGrid(Div divSubGrid) {
		this.divSubGrid = divSubGrid;
		generatedUtil.setDivSubGrid(divSubGrid);
	}

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
	public Component createContent(FormButtonsBar buttonsForm) {
		return createContent(buttonsForm, null);
	
	}
	  public Component createContent(FormButtonsBar buttonsForm, GenericClassForMethods genericClassForMethods) {
		  this.setId("GeneratedDetails");
		  this.buttonsForm = buttonsForm;
		  if (genericClassForMethods != null)
			  this.genericClassForMethods = genericClassForMethods;		
	    FlexBoxLayout content = new FlexBoxLayout(createDetails());
	    //		content.setBoxSizing(BoxSizing.BORDER_BOX);
	    //		content.setHeightFull();
	    //		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
//	    		content.setWidthFull();
//	    		content.setMaxWidth("1200px"); // if we want reposponsive we must uncomment this and previous, comment next
//	    content.setWidth(AppConst.DEFAULT_WIDTH_FORM);
	    Div header = new Div(); 
	    header.setClassName("formTitle");
	    header.add(UIUtils.createH3Label(title));
	    Div headearAndForm = new Div(); 
	    headearAndForm.add(header);
	    headearAndForm.add(content);
	    return headearAndForm;
	    }

	private Component createDetails() {
//			GeneratedUtil generatedUtil = new GeneratedUtil();
			if (binder == null)
				this.binder = new Binder<DynamicDBean>(DynamicDBean.class);
			generatedUtil.setGrid(dVGrid);
			generatedUtil.setBinder(binder);
			generatedUtil.setBean(bean);
			generatedUtil.setButtonsForm(buttonsForm);
			generatedUtil.setGenericClassForMethods(genericClassForMethods);
//			generatedUtil.setDivSubGrid(divSubGrid);
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
				return generatedUtil.createDetails(bean.getResourceName(),rowsFieldList, false, cache,"noTAB");
			}
			else
			{
				return generatedUtil.createTabs(bean.getResourceName(), rowsFieldList, false,cache,tabs);
			}
	//		return details;
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
