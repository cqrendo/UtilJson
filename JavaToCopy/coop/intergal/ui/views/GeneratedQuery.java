package coop.intergal.ui.views;

import static coop.intergal.AppConst.STYLES_CSS;
import static coop.intergal.AppConst.STYLES_FORM_ITEM_CSS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import coop.intergal.AppConst;
import coop.intergal.ui.components.QueryButtonsBar;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;

//@PageTitle("Payments")
//@Route(value = "gridDetails", layout = MainLayout.class)
@Tag("generated-query")
@JsModule("./src/views/generic/forms/generated-query.js")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@Route(value = "queryGenerated")

@CssImport(value = STYLES_CSS, themeFor="generated-query")
@CssImport(value = STYLES_FORM_ITEM_CSS, themeFor = "vaadin-form-item")
//@CssImport(value = STYLES_FORM_LAYOUT_ITEM_CSS, themeFor = "vaadin-form-item")
public class GeneratedQuery extends GenericDynamicQuery implements HasDynamicTitle, BeforeEnterObserver {//, AfterNavigationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Grid<DynamicDBean> grid;
	private DynamicViewGrid grid;

 //   private ListDataProvider<Payment> dataProvider;
	private DdbDataBackEndProvider dataProvider;
 
//	private DetailsDrawer detailsDrawer;
//	private Binder<DynamicDBean> binder;

//	private ArrayList<String[]> rowsColList;
	private ArrayList<String[]> rowsQueryFieldList;
//	private ArrayList<String[]> rowsColListGrid;
//	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
//	private Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param

	private String title;
//	private String resource;
//	private DynamicDBean bean;
	private Boolean cache = true;

	public boolean isCache() {
		return cache;
	}
	public void setCache(Boolean cache) {
		this.cache = cache;
	}

	@Id("form")
	private FormLayout form;
	@Id("queryButtonsBar")
	private QueryButtonsBar queryButtonsBar;

	public GeneratedQuery() {
		super();
		super.preConfParam = UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM;
		queryButtonsBar.addSearchListener(e -> createFilterFromQryForm());
		queryButtonsBar.addClearSearchListener(e -> cleanQryForm());//System.out.println("PedidoProveedorQuery.beforeEnter() BUSCAR>>>>"));
	}
	private Object cleanQryForm() {
		cleanForm(GeneratedQuery.class, this, dataProvider.getResourceName(), true);
		return null;
	}

	private Object createFilterFromQryForm() {
		stringFilter = "";
		keysFromParent = "";
//		String filter = getFieldsDataForFilter(GeneratedQuery.class, this, dataProvider.getResourceName());

		String filter = getFieldsDataForFilter(GeneratedQuery.class, form, dataProvider.getResourceName());
		System.out.println("GeneratedQuery.createFilter()...." + filter);
		if (grid != null) // when is a query form of a popup doesn't have grid thta is null
			dataProvider = grid.getDataProvider();
		dataProvider.setFilter(filter);
		dataProvider.refreshAll();
		return null;
	}
    
    public DynamicViewGrid getGrid() {
		return grid;
	}

	public void setGrid(DynamicViewGrid grid) {
		this.grid = grid;
	}
	
	   public DdbDataBackEndProvider getDataProvider() {
			return dataProvider;
		}

		public void setDataProvider(DdbDataBackEndProvider dataProvider) {
			this.dataProvider = dataProvider;
		}


//	@Override
//    protected void onAttach(AttachEvent attachEvent) {
//        super.onAttach(attachEvent);
 //       initAppBar();
 
 //       setViewContent(createContent());
 //       setViewDetails(createDetailsDrawer());
 //       filter();
 //   }

//    private void initAppBar() {
//        AppBar appBar = MainLayout.get().getAppBar();
//        appBar.addTabSelectionListener(e -> {
// //           filter();
//            detailsDrawer.hide();
//        });
//        appBar.centerTabs();
//    }

   
  
//    private boolean isCurrency(String header, String colType) {
//    	if (header.startsWith("C#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
//    		return true; 
//    	if (colType.equals("3"))
//    		return true;
//    	return false;
//    }
//
//
//    private boolean isDate(String header, String colType) {
//    	if (header.startsWith("D#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
//    		return true; 
//    	if (colType.equals("1"))
//    		return true;
//    	return false;
//    }
//    private boolean isBoolean(String header, String colType) {
//    	if (colType.equals("4"))
//    		return true;
//    	return false;
//    }
 

	public Component createDetails() {
		GeneratedUtil generatedUtil = new GeneratedUtil();
		rowsQueryFieldList = dataProvider.getRowsQueryFieldList();	
   		String tabs ="";
   		if (rowsQueryFieldList != null)
		{
			tabs = rowsQueryFieldList.get(0)[17];
		}
 		if (tabs.isEmpty()) 
		{
 			Component generatedForm = generatedUtil.createDetails(dataProvider.getResourceName(), rowsQueryFieldList, true, cache,"noTAB");
 			Div content = new Div(generatedForm);
 			form.setMinWidth(AppConst.DEFAULT_WIDTH_FORM);
 			content.setMinWidth(AppConst.DEFAULT_WIDTH_FORM);
 			content.setHeight("100%");
 			form.add(content);
 			if (generatedForm.getId().isPresent())
 				setId(generatedForm.getId().get());
 			return  this;
		}
		else
		{
			Component generatedForm = generatedUtil.createTabs(dataProvider.getResourceName(), rowsQueryFieldList, true,cache,tabs);
			Div content = new Div(generatedForm);
			form.setMinWidth(AppConst.DEFAULT_WIDTH_FORM);
			content.setMinWidth(AppConst.DEFAULT_WIDTH_FORM);
			content.setHeight("100%");
			form.add(content);
			if (generatedForm.getId().isPresent())
				setId(generatedForm.getId().get());
			return this;//form;
		}

//		return generatedUtil.createDetails(rowsQueryFieldList, form, true, cache);
}


//	private Div alingLabel(String label) {
//		Div l = new Div();
//		l.add(label);
//		if (label.endsWith("#"))
//			{
//			l.addClassName("labelright");
//			label=label.substring(0,label.length()-1);
//			l.setText(label);
//			}
//		return l;
//	}
//
//	private boolean isReadOnly(String params) {
//		
//		if (params == null)
//			return false;
//		if (params.indexOf("#CNoEDT#")>-1)
//			return true;
//		else 
//			return false;
//			
//	}

  
	@Override
	public String getPageTitle() {
		// TODO Auto-generated method stub
		if(title == null)
			return "SIN TITULO";
		return title;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		System.out.println("GenericGridDetails.beforeEnter()");
		Location location = event.getLocation();
	    QueryParameters queryParameters = location
	            .getQueryParameters();

	    Map<String, List<String>> parametersMap =
	            queryParameters.getParameters();
	    System.out.println("beforeEnter.....parametersMap.get(\"resource\")"+parametersMap.get("resource"));
		
	       title = parametersMap.get("title").get(0);	
//	       resource = parametersMap.get("resource").get(0);
	       // @@ TODO get fllter by param and use to ResData.getOneRow 
//	       showDetails(dynamicDBean);
//	       setViewContent(createContent(resource));
//	        setViewDetails(createDetailsDrawer());

		
	}



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
