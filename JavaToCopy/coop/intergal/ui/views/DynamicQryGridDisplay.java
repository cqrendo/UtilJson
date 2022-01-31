package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;
import static coop.intergal.AppConst.STYLES_CSS;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.SynchronizedRequestHandler;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;


@Tag("dynamic-qry-grid-display")
@JsModule("./src/views/generic/layout/dynamic-qry-grid-display.js")
//@CssImport(value = "./styles/dialog-overlay.css", themeFor = "vaadin-dialog-overlay")
@CssImport(value = STYLES_CSS, themeFor = "dynamic-qry-grid-display")
public class DynamicQryGridDisplay extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle{//, PageConfigurator{//, VaadinServiceInitListener  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	
	private final Div thisIdText = new Div();
	private final Div log = new Div();
	
	private Hashtable<String, DynamicViewGrid> dvgIntheForm = new Hashtable<String, DynamicViewGrid>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param


	public Hashtable<String, DynamicViewGrid> getDvgIntheForm() {
		return dvgIntheForm;
	}

	public void setDvgIntheForm(Hashtable<String, DynamicViewGrid> dvgIntheForm) {
		this.dvgIntheForm = dvgIntheForm;
	}

	public DynamicQryGridDisplay() {
		super();
		setId("DQGD");
//		Date now = new Date();
//		String id = "QRDView";// + now.getTime();
//		 setId(id);
//		 UI.getCurrent().getPage().executeJs("window.onbeforeunload = function (e) { var e = e || window.event; document.getElementById(\""+id+"\").$server.browserIsLeaving(); return; };");
//***** ACTIVATE FOR DEBUG
//		getDivDisplay().add(thisIdText, log);
//
//      log.getStyle().set("white-space", "pre");
//
//      refreshLog();

		
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
	private Div querySplitGrid;

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

	public Div getQuerySplitGrid() {
		return querySplitGrid;
	}

	public void setQuerySplitGrid(Div querySplitGrid) {
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

	private boolean cache = UtilSessionData.getCache();
	private Object divInDisplay;

	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}
	
	private String idMenu =  null;
	public String getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}

	protected Binder<DynamicDBean> getBinder() {
		return binder;
	}

	public Button showButtonClickedMessage()
	{
		return null;
		
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

	@Override
	public void beforeEnter(BeforeEnterEvent event) {  // when is call from a navigation
//		buttons.setVisible(false);

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
		String addFormClassName  = null;
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
			List<String> parIdMenu = queryParameters.getParameters().get("idMenu");
			if (parIdMenu != null)
			{
				idMenu = parIdMenu.get(0);
			}
			//*** PACKAGE_VIEWS is used when the class is no generic for several projects. and corresponds a particular class for the form
			queryFormClassName = queryParameters.getParameters().get("queryFormClassName").get(0);
			displayFormClassName= queryParameters.getParameters().get("displayFormClassName").get(0);
			
			if (queryParameters.getParameters().get("addFormClassName") != null)
				{
				addFormClassName= queryParameters.getParameters().get("addFormClassName").get(0);
				if (addFormClassName.startsWith("coop.intergal") == false)
					addFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("addFormClassName").get(0);
				}
			if (displayFormClassName.startsWith("coop.intergal") == false)
				displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
			if (queryFormClassName.startsWith("coop.intergal") == false)
				queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);


		}
		prepareLayout(queryFormClassName, displayFormClassName, addFormClassName);
	}
		public void prepareLayout(String queryFormClassName, String displayFormClassName, String addFormClassName)
		{
//			querySplitGrid.setOrientation(Orientation.VERTICAL);
			gridSplitDisplay.setOrientation(Orientation.HORIZONTAL);
//			gridSplitDisplay.getStyle().set("height", "83vh"); 
			displaySplitSubGrid.setOrientation(Orientation.VERTICAL);

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
		grid.setiAmRootGrid(true);
		grid.setCache(cache);
		grid.setupGrid(false, true, true);
		grid.setAddFormClassName(addFormClassName);

		buttons.setVisible(false);
		buttons.addSaveListener(e -> grid.saveSelectedRow(apiname));
		buttons.addCancelListener(e -> grid.undoSelectedRow());
		buttons.addAddListener(e -> grid.insertANewRow(addFormClassName));
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

//	@Override
//	public void configurePage(InitialPageSettings settings) {
//        String script = "window.onbeforeunload = function (e) { var e = e || window.event; document.getElementById(\"SomeView\").$server.browserIsLeaving(); return; };";		
//        settings.addInlineWithContents(InitialPageSettings.Position.PREPEND, script, InitialPageSettings.WrapMode.JAVASCRIPT);
//    }
//	@ClientCallable
//	public void browserIsLeaving() {
//	        System.out.println("Called browserIsLeaving");
//	}
	@ClientCallable
	public void browserIsLeaving() {
			getUI().ifPresent(ui -> closeUi(ui));//;ui.close());
	}

	private Object closeUi(UI ui) {
		System.out.println("Called browserIsLeaving ->" + ui.getId());
		ui.close();
		return null;
	}
	public static class BeaconEvent extends ComponentEvent<UI> {

        public BeaconEvent(UI source, boolean fromClient) {
            super(source, fromClient);
        }
    }

    public static class BeaconHandler extends SynchronizedRequestHandler {
        private final UI ui;
        private final String beaconPath = "/beacon/" + UUID.randomUUID().toString();

        public BeaconHandler(UI ui) {
            this.ui = ui;
        }

        @Override
        protected boolean canHandleRequest(VaadinRequest request) {
            return beaconPath.equals(request.getPathInfo());
        }

        @Override
        public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response)
            throws IOException {
            ComponentUtil.fireEvent(ui, new BeaconEvent(ui, true));
            return true;
        }

        public static Registration addBeaconListener(UI ui, ComponentEventListener<BeaconEvent> listener) {
            ensureInstalledForUi(ui);
            return ComponentUtil.addListener(ui, BeaconEvent.class, listener);
        }

        private static void ensureInstalledForUi(UI ui) {
            if (ComponentUtil.getData(ui, BeaconHandler.class) != null) {
                // Already installed, nothing to do
                return;
            }

            BeaconHandler beaconHandler = new BeaconHandler(ui);

            // ./beacon/<random uuid>
            String relativeBeaconPath = "." + beaconHandler.beaconPath;

            ui
                .getElement()
                .executeJs(
                    "window.addEventListener('unload', function() {navigator.sendBeacon && navigator.sendBeacon($0)})",
                    relativeBeaconPath
                );

            VaadinSession session = ui.getSession();
            session.addRequestHandler(beaconHandler);
            ui.addDetachListener(detachEvent -> session.removeRequestHandler(beaconHandler));

            ComponentUtil.setData(ui, BeaconHandler.class, beaconHandler);
        }
    }

//    private final Div thisIdText = new Div();
//    private final Div log = new Div();

//    public NoticeClosed() {
//        add(thisIdText, log);
//
//        log.getStyle().set("white-space", "pre");
//
//        refreshLog();
//    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UI ui = attachEvent.getUI();
        int uiId = ui.getUIId();

        thisIdText.setText("This UI has id " + uiId);

        addLogMessage("Attached " + uiId);

        Registration beaconRegistration = BeaconHandler.addBeaconListener(
            ui,
            beaconEvent -> {
                addLogMessage("Browser close event for " + uiId);
                ui.close();
            }
        );

        // Polling only needed for the demo
//        ui.setPollInterval(1000);
//        Registration pollRegistration = ui.addPollListener(
//            pollEvent -> {
//                refreshLog();
//            }
//        );

        addDetachListener(
            detachEvent -> {
                detachEvent.unregisterListener();
                beaconRegistration.remove();

                // Polling only needed for the demo
//                ui.setPollInterval(-1);
//                pollRegistration.remove();
            }
        );
    }

    private void addLogMessage(String message) {
        VaadinSession.getCurrent().setAttribute("log", getLogValue() + "\n" + LocalTime.now() + " " + message);

        refreshLog();
    }

    private void refreshLog() {
        log.setText(getLogValue());
    }

    private static String getLogValue() {
        return Objects.toString(VaadinSession.getCurrent().getAttribute("log"), "");
    }
}


