package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;
import static coop.intergal.AppConst.STYLES_CSS;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;


@Tag("dynamic-qry-grid-display")
@JsModule("./src/views/generic/layout/dynamic-qry-grid-display.js")
@CssImport(value = STYLES_CSS, themeFor = "dynamic-qry-grid-display")
public class DynamicQryGridDisplay extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle{//, VaadinServiceInitListener  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	

	public DynamicQryGridDisplay() {
		super();
		
	}

	public DynamicQryGridDisplay(TemplateParser parser, VaadinService service) {
		super(parser, service);
	}

	public DynamicQryGridDisplay(TemplateParser parser) {
		super(parser);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id("grid")
	private DynamicViewGrid grid;
	
	@Id("divDisplay")
	private Div divDisplay;
	public Div getDivDisplay() {
		return divDisplay;
	}

	public void setDivDisplay(Div divDisplay) {
		this.divDisplay = divDisplay;
	}

	public DynamicViewGrid getGrid() {
		return grid;
	}

	public void setGrid(DynamicViewGrid grid) {
		this.grid = grid;
	}

	@Id("divSubGrid")  
	private Div divSubGrid;
	

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

//	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;

	@Id("querySplitGrid")
	private SplitLayout querySplitGrid;

	@Id("gridSplitDisplay")
	private SplitLayout gridSplitDisplay;

	@Id("displaySplitSubGrid")
	private SplitLayout displaySplitSubGrid;

	@Id("divQuery")
	private Div divQuery;
	@Id("buttons")
	private FormButtonsBar buttons;
	private String apiname;
	public Div getDivQuery() {
		return divQuery;
	}

	public SplitLayout getDisplaySplitSubGrid() {
		return displaySplitSubGrid;
	}

	public void setDisplaySplitSubGrid(SplitLayout displaySplitSubGrid) {
		this.displaySplitSubGrid = displaySplitSubGrid;
	}

	public SplitLayout getQuerySplitGrid() {
		return querySplitGrid;
	}

	public void setQuerySplitGrid(SplitLayout querySplitGrid) {
		this.querySplitGrid = querySplitGrid;
	}

	public SplitLayout getGridSplitDisplay() {
		return gridSplitDisplay;
	}

	public void setGridSplitDisplay(SplitLayout gridSplitDisplay) {
		this.gridSplitDisplay = gridSplitDisplay;
	}

	public Object getDivInDisplay() {
		return divInDisplay;
	}

	public void setDivInDisplay(Object divInDisplay) {
		this.divInDisplay = divInDisplay;
	}

	public void setDivQuery(Div divQuery) {
		this.divQuery = divQuery;
	}

	private boolean cache = true;
	private Object divInDisplay;
	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

	protected Binder<DynamicDBean> getBinder() {
		return binder;
	}

	public Button showButtonClickedMessage()
	{
		return null;
		
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {  // when is call from a navigation
		buttons.setVisible(false);
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		filter = null; 
		List<String> parFIlter = queryParameters.getParameters().get("filter");
		if (parFIlter != null)
			{
			filter = parFIlter.get(0);
			filter=filter.replace("EEQQ", "=");
			filter=filter.replace("GGTT", "%3E"); // ">"
			filter=filter.replace("LLTT", "%3C"); // "<"
			}
		title="..";
		String queryFormClassName = null;
		String displayFormClassName  = null;
		querySplitGrid.setOrientation(Orientation.VERTICAL);
		gridSplitDisplay.setOrientation(Orientation.HORIZONTAL);
		gridSplitDisplay.getStyle().set("height", "83vh"); 
		displaySplitSubGrid.setOrientation(Orientation.VERTICAL);
		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
		{
			title=queryParameters.getParameters().get("title").get(0);
			resourceName = queryParameters.getParameters().get("resourceName").get(0);
			if (queryParameters.getParameters().get("apiname") != null)
				apiname = queryParameters.getParameters().get("apiname").get(0);
			if (queryParameters.getParameters().get("cache") != null)
				{
				String cacheStr = queryParameters.getParameters().get("cache").get(0);
				if (cacheStr.equals("false"))
					cache = false;
				else
					cache = true;
				}
			//*** PACKAGE_VIEWS is used when the class is no generic for several projects. and corresponds a particular class for the form
			queryFormClassName = queryParameters.getParameters().get("queryFormClassName").get(0);
			displayFormClassName= queryParameters.getParameters().get("displayFormClassName").get(0);
			if (queryFormClassName.startsWith("coop.intergal.ui.views") == false)
				queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
			if (displayFormClassName.startsWith("coop.intergal.ui.views") == false)
				displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
			
		}
		try {
			Class<?> dynamicQuery = Class.forName(queryFormClassName);
			Object queryForm = dynamicQuery.newInstance();
			Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );
			setGrid.invoke(queryForm,grid);
			divQuery.removeAll();
//			String[] rowCol = rowsColList.iterator().next();
//			divQuery.add(new H2("TITULO"));
			if (queryFormClassName.indexOf("Generated") > -1)
			{
				
				DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
				dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
				dataProvider.setResourceName(resourceName);
				Method setDataProvider= dynamicQuery.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
				Method createDetails= dynamicQuery.getMethod("createDetails");
				Method setRowsColList = dynamicQuery.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
				setDataProvider.invoke(queryForm,dataProvider );
				setRowsColList.invoke(queryForm,rowsColList);
				divInDisplay =createDetails.invoke(queryForm);
				if (((GeneratedQuery) divInDisplay).getId().isPresent())
					{
					String titleByID = ((GeneratedQuery) divInDisplay).getId().get();
					if (titleByID != null && titleByID.length() > 2)
						divQuery.add(new H3(titleByID));
					}
				divQuery.add((Component)divInDisplay);
			}
			else 
			{				
				divQuery.add((Component)queryForm);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		grid.setDisplayFormClassName(displayFormClassName);
		grid.setDisplay(divDisplay);
		grid.setDivSubGrid(divSubGrid);
		grid.setButtonsForm(buttons);
		grid.setLayout(this);
		grid.setResourceName(resourceName);
		if ((apiname == null || apiname.length() == 0) == false)
		{
			if (filter != null  && filter.length() > 0)
			{
				filter = filter + "%20%AND%20APIname='"+apiname+"'";
			}
			else
			{
			filter = "APIname='"+apiname+"'";
			}
		}
		grid.setFilter(filter);
		System.out.println("DynamicQryGridDisplay.beforeEnter() CACHE "+ cache);
		grid.setCache(cache);
		grid.setupGrid(false, true);
		buttons.setVisible(false);
		buttons.addSaveListener(e -> grid.saveSelectedRow(apiname));
		buttons.addCancelListener(e -> grid.undoSelectedRow());
		buttons.addAddListener(e -> grid.insertANewRow());
		buttons.addDeleteListener(e -> grid.DeleteARow());
		buttons.addPrintListener(e -> grid.PrintARow());
		
	}

	@Override
	public String getPageTitle() {
		try {
			title = java.net.URLDecoder.decode(title, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return UtilSessionData.addCompanyToTitle(title);
	}

}
