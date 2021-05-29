package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;
import static coop.intergal.AppConst.STYLES_CSS;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
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

import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.vaadin.rest.utils.DynamicDBean;


@Tag("dynamic-display-only")
@JsModule("./src/views/generic/layout/dynamic-display-only.js")
@CssImport(value = STYLES_CSS, themeFor = "dynamic-display-only")
public class DynamicDisplayOnly extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle{//, VaadinServiceInitListener  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	

	public DynamicDisplayOnly() {
		super();
//		displaySplitSubGrid.setOrientation(Orientation.VERTICAL);
	}

	public DynamicDisplayOnly(TemplateParser parser, VaadinService service) {
		super(parser, service);
	}

	public DynamicDisplayOnly(TemplateParser parser) {
		super(parser);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Id("divDisplay")
	private Div divDisplay;
//	@Id("divSubGrid")  
	private Div divSubGrid;

	public Div getDivDisplay() {
		return divDisplay;
	}

	public void setDivDisplay(Div divDisplay) {
		this.divDisplay = divDisplay;
	}


	public Div getDivSubGrid() {
		return divSubGrid;
	}

	public void setDivSubGrid(Div divSubGrid) {
		this.divSubGrid = divSubGrid;
	}

		

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

//	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;


//	@Id("displaySplitSubGrid")
	private SplitLayout displaySplitSubGrid;

	@Id("buttons")
	private FormButtonsBar buttons;
	private String apiname;

	public SplitLayout getDisplaySplitSubGrid() {
		return displaySplitSubGrid;
	}

	public void setDisplaySplitSubGrid(SplitLayout displaySplitSubGrid) {
		this.displaySplitSubGrid = displaySplitSubGrid;
	}


	public Object getDivInDisplay() {
		return divInDisplay;
	}

	public void setDivInDisplay(Object divInDisplay) {
		this.divInDisplay = divInDisplay;
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
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		filter = null; 
		List<String> parFIlter = queryParameters.getParameters().get("filter");
		if (parFIlter != null)
			{
			filter = parFIlter.get(0);
			filter=filter.replace("EEQQ", "=");
			}
		title="..";
		String queryFormClassName = null;
		String displayFormClassName  = null;
//		displaySplitSubGrid.setOrientation(Orientation.VERTICAL);
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
		buttons.setVisible(false);
//		buttons.addSaveListener(e -> grid.saveSelectedRow(apiname));
//		buttons.addCancelListener(e -> grid.undoSelectedRow());
//		buttons.addAddListener(e -> grid.insertANewRow());
//		buttons.addDeleteListener(e -> grid.DeleteARow());
		
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
