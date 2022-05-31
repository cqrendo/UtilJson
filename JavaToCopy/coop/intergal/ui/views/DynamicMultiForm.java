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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
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
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.GenericClassForMethods;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.ui.utils.ProcessParams;
import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;


public class DynamicMultiForm extends HorizontalLayout implements BeforeEnterObserver, HasDynamicTitle{//, PageConfigurator{//, VaadinServiceInitListener  {

	private ArrayList<String[]> rowsColListGrid;//=new ArrayListString[]();

	public ArrayList<String[]> getRowsColListGrid() {
		return rowsColListGrid;
	}

	public void setRowsColListGrid(ArrayList<String[]> rowsColListGrid) {
		this.rowsColListGrid = rowsColListGrid;
	}
	private ArrayList <String[]> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	public ArrayList<String[]> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String[]> rowsColList) {
		this.rowsColList = rowsColList;
	}	
	private Hashtable<String, String[]> resourceAndSubresources = new Hashtable<String, String[]>(); 
	private final Div thisIdText = new Div();
	private final Div log = new Div();
	
	private Hashtable<String, DynamicViewGrid> dvgIntheForm = new Hashtable<String, DynamicViewGrid>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param


	public Hashtable<String, DynamicViewGrid> getDvgIntheForm() {
		return dvgIntheForm;
	}

	public void setDvgIntheForm(Hashtable<String, DynamicViewGrid> dvgIntheForm) {
		this.dvgIntheForm = dvgIntheForm;
	}

	public DynamicMultiForm() {
		super();
		setId("DMF");

		
	}

//	public DynamicMultiForm(TemplateParser parser, VaadinService service) {
//		super(parser, service);
//	}
//
//	public DynamicMultiForm(TemplateParser parser) {
//		super(parser);
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
//	@Id("grid")
//	private DynamicViewGrid grid;
//	
//
//	public DynamicViewGrid getGrid() {
//		return grid;
//	}
//
//	public void setGrid(DynamicViewGrid grid) {
//		this.grid = grid;
//	}
//
//	@Id("divGrid")  
//	private Div divGrid;

	

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

//	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;


//	@Id("buttons")
//	private FormButtonsBar buttons;
	private String apiname;





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
	private String queryFormClassName;
	private DynamicDBean selectedRow;
	private boolean isResourceReadOnly;
	private String tagsForVisibility;
	public String getTagsForVisibility() {
		return tagsForVisibility;
	}

	public void setTagsForVisibility(String tagsForVisibility) {
		this.tagsForVisibility = tagsForVisibility;
	}


	public String getQueryFormClassName() {
		return queryFormClassName;
	}

	public void setQueryFormClassName(String queryFormClassName) {
		this.queryFormClassName = queryFormClassName;
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
		queryFormClassName = null;
//		displayFormClassName  = null;
//		addFormClassName  = null;
//		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
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
//			displayFormClassName= queryParameters.getParameters().get("displayFormClassName").get(0);
//			
//			if (queryParameters.getParameters().get("addFormClassName") != null)
//				{
//				addFormClassName= queryParameters.getParameters().get("addFormClassName").get(0);
//				if (addFormClassName.startsWith("coop.intergal") == false)
//					addFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("addFormClassName").get(0);
//				}
//			if (displayFormClassName.startsWith("coop.intergal") == false)
//				displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
			if (queryFormClassName.startsWith("coop.intergal") == false)
				queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);


		}
		createContent(null) ;
	}

		public Component createContent(DynamicDBean bean) 
		{

			Div divSubGrid = new Div();
			try {
				System.out.println("DynamicMultiForm.createContent()");
				
				selectedRow = bean;
					
				String resourceSubGrid = extractResourceSubGrid(bean,0);
				
				String formsList = rowsColList.get(0)[12];
				if (resourceSubGrid != null && (formsList == null || formsList.length() == 0)) // there only one tab
				{
				//	divSubGrid.add(componSubgrid(bean, resourceSubGrid));
					System.out.println("DynamicViewGrid.showBean() ADD SUBGRID");
					Div content0=new Div(); 
					divSubGrid.add(fillContent("",content0, 0 , bean));	
				}
				else if (resourceSubGrid != null) //123456789
				{
//					createTabs(DynamicDBean bean)
					divSubGrid.removeAll();
					divSubGrid.add(createSubForms(bean, formsList));

				}
				

				} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return divSubGrid;

//			display.beforeEnter(null);
//			gridSplitDisplay.getElement().removeAllChildren();//removeChild(display.getElement());
//			gridSplitDisplay.getElement().appendChild(grid.getElement());
//			gridSplitDisplay.getElement().appendChild(display.getElement());

//		UI.getCurrent().navigate("dymanic");
	}
		
	
private String extractResourceSubGrid(DynamicDBean bean, int idx) {
	if (resourceAndSubresources.get(bean.getResourceName()+idx)!= null && resourceAndSubresources.get(bean.getResourceName()+idx)[0] != null)
	{
	String[] subResourcesOfResource = resourceAndSubresources.get(bean.getResourceName()+idx);
	if (subResourcesOfResource.length > idx)
		{
		if (subResourcesOfResource[idx].isEmpty() == false)
			return subResourcesOfResource[idx];
		}
	}
	String rowJson = bean.getRowJSon().toString();
	if (bean.getRowJSon().get("tableName") != null)
		{
		if (bean.getRowJSon().get("tableName").asText().equals("CR-FormTemplate.List-FieldTemplate")) // // the resource is the formTemplate or FieldTemplate, to configure themselves, then there is a conflict with the names 
			return bean.getRowJSon().get("tableName").asText();
		}

	Enumeration<String> keysHt = JSonClient.getHt().keys();
	String parentResourceName = bean.getResourceName();
	Hashtable<String, String> htChildren = new Hashtable<String, String>();

	while (keysHt.hasMoreElements())
	{
		String keyHt = keysHt.nextElement();
		if (keyHt.startsWith(parentResourceName+"."))
		{
			String child = keyHt.substring(keyHt.lastIndexOf(".")+1);
			if (child.indexOf("List-") > -1) // to process only subresources List , not FK 
				if (htChildren.get(keyHt) == null) // to avoid duplicates
					htChildren.put(keyHt, child);
		}	
		
	}
	String pathSubreourceName = getSubResourcePathByOrderPosition(htChildren, idx); // sorts the HT and gets the value by idx position
	keepSubResourcesNames(bean.getResourceName()+idx,pathSubreourceName);
	return pathSubreourceName;
	

}
private String getSubResourcePathByOrderPosition(Hashtable<String, String> htChildren, int idx) {
	List<String> tmp = Collections.list(htChildren.keys());
	Collections.sort(tmp);
	Iterator<String> it = tmp.iterator();
	int i = 0;
	while(it.hasNext()){
	    String element =it.next();
	    if (i==idx)
	    	return element;
	    i++;
	}
	return null;
}

private void keepSubResourcesNames(String resourceName2, String pathSubreourceName) {
	String[] subResourcesOfResource = null;
	if (resourceAndSubresources.contains(resourceName2)==true)
	{
		subResourcesOfResource = resourceAndSubresources.get(resourceName2);
		List<String> arrlist = new ArrayList<String>( 
        Arrays.asList(subResourcesOfResource)); 
		arrlist.add(pathSubreourceName); 
		subResourcesOfResource = arrlist.toArray(subResourcesOfResource);
		resourceAndSubresources.remove(resourceName2);
		resourceAndSubresources.put(resourceName2,subResourcesOfResource);
	}
	else
	{
		subResourcesOfResource = new String[1]; 
		subResourcesOfResource [0] = pathSubreourceName;
		resourceAndSubresources.put(resourceName2,subResourcesOfResource);
	}
	
}
public Component createSubForms(DynamicDBean bean, String formsList) {//ArrayList<String[]> rowsFieldList, Boolean isQuery, Boolean cache,String tabsLabels) {
//	String tabsLabels="1,2,3,4,5";
	Board allForms = new Board();
	Row row = new Row();
	String [] tokens = formsList.split(Pattern.quote(","));
	Div content0=new Div(); 
	Div content1=new Div();
	Div content2=new Div(); 
	Div content3=new Div();
	Div content4=new Div(); 
//	Div content5=new Div(); 
//	Div content6=new Div(); 
//	Div content7=new Div(); 
//	Div content8=new Div(); 
//	Div content9=new Div();
//	Div content10=new Div(); 
//	Div content11=new Div();
//	Div content12=new Div(); 
//	Div content13=new Div(); 
//	Div content14=new Div(); 
//	Div content15=new Div(); 

//	FlexBoxLayout content7=null; 
//	boolean visibleByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForVisibility);
   	int nForms = tokens.length;
   	String formTitle;
//	while (tokens.length > i)
//	{ 
		if (nForms > 0 )
		{
			formTitle = tokens[0];
			content0 = fillContent(formTitle,content0, 0, bean);	
			if (content0 != null)
				{
				content0.setId("0");
				row.add(content0);
				}
			
		}
		if (nForms > 1)
		{
			formTitle = tokens[1];
			content1 = fillContent(formTitle,content1, 1, bean);
			if (content1 != null)
				{
				content1.setId("1");
				row.add(content1);
				}
//			content1.setVisible(false);
			
		}
		if (nForms > 2)
		{
			formTitle = tokens[2];
			content2 = fillContent(formTitle,content2, 2, bean);	
			if (content2 != null)
				{
				content2.setId("2");
				row.add(content2);
				}
			
		}
		if (nForms > 3)
		{
			formTitle = tokens[3];
			content3 = fillContent(formTitle,content3, 3, bean);	
			if (content3 != null)
				{
				content3.setId("3");
				row.add(content3);
				}			
		}
		if (nForms > 4)
		{
			DataService.get().showError("Máximo de subforms 4");
			
		}
				
		allForms.add(row);
		return allForms;


		}



private Div fillContent(String formTitle, Div content, int i, DynamicDBean bean) {
	String resourceSubGrid = extractResourceSubGrid(bean,i);
	int idxLastResource = resourceSubGrid.lastIndexOf(".");
	int idxFormExt = resourceSubGrid.substring(idxLastResource+1).indexOf("FormExt"); // to avoid false identification as formExt in the parents
	if ( idxFormExt> -1)
		{
		Component subform = componSubForm(bean, resourceSubGrid);
		if (subform != null)
		{
			H4 title = new H4(formTitle);
			title.getStyle().set("margin-left", "20px");
			title.getStyle().set("margin-bottom", "-15px");
			title.getStyle().set("margin-top", "0px");
			content = new Div(title,subform);
		}
		else 
			return null;
		}
	else
		{
		content = new Div(new H3(formTitle),componSubgrid(bean, resourceSubGrid));
		}
	content.setWidthFull();
	content.getStyle().set("height", "100%");
	content.getStyle().set("border-right", "solid 5px lightgrey");
	return content;
}
private Component componSubForm(DynamicDBean bean, String resourceSubGrid0) {
	Div divSubForm = new Div();
//	divSubForm.add(new Label(" FORMULARIO "));
	JsonNode extProp;
	Component component = null;
	String styleForForm = null;
	try {
		extProp = JSonClient.get("JS_ExtProp", resourceSubGrid0, cache, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		String displaySubFormClassName = null;
		String querySubFormClassName = null;
		String subLayoutClassName =  "NO LAYOUT";
		String extraFilterToSelect = null;
		
		if (extProp.get("subLayoutClassName") != null)
		{
			subLayoutClassName = extProp.get("subLayoutClassName").asText();
		}
		if (extProp.get("displaySubFormClassName") != null)
		{
			displaySubFormClassName = extProp.get("displaySubFormClassName").asText();
		}
		if (extProp.get("querySubFormClassName") != null)
		{
			querySubFormClassName = extProp.get("querySubFormClassName").asText();
		}
		else if (extProp.get("displaySubFormClassName") == null  && (extProp.get("querySubFormClassName") == null) && subLayoutClassName.indexOf("DynamicGridOnly") == -1)
		{
			DataService.get().showError("sin definir displaySubFormClassName y/o querySubFormClassName en extended properties ");
		}
		if (extProp.get("extraFilterToSelect") != null )
		{
			extraFilterToSelect = extProp.get("extraFilterToSelect").asText();
		}
		if (extProp.get("styleForForm") != null )
		{
			styleForForm = extProp.get("styleForForm").asText();
		}
		if (extProp.get("tagsForVisibility") != null )
		{
			tagsForVisibility = extProp.get("tagsForVisibility").asText();
		}

		boolean visibleByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForVisibility);
		if (visibleByTag == false)
			return null;  

		String subFormClassName = displaySubFormClassName;
		String subFormFilter = componFKFilter(bean, resourceSubGrid0);
		String subFormResource = resourceSubGrid0;
		DdbDataBackEndProvider dataProviderSub = new DdbDataBackEndProvider();
		dataProviderSub.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProviderSub.setResourceName(subFormResource);
		dataProviderSub.setFilter(subFormFilter);
		
		
		if (subLayoutClassName.indexOf("DynamicGridDisplay") > -1)
		{
			if (extraFilterToSelect != null)
			{			 
				component = componDynamicGridDisplay (subLayoutClassName, subFormResource, subFormFilter, subFormClassName, divSubForm, true, extraFilterToSelect );
				
			}
			else
			{
				component = componDynamicGridDisplay (subLayoutClassName, subFormResource, subFormFilter, subFormClassName, divSubForm, true );
			}
		}
		else if (subLayoutClassName.indexOf("DynamicQryGrid") > -1)
		{
			component = componDynamicQryGrid (subLayoutClassName, subFormResource, subFormFilter, querySubFormClassName, divSubForm, true );
		}
		else if (subLayoutClassName.indexOf("DynamicGridOnly") > -1)
		{
			component =  componDynamicGridOnly (subLayoutClassName, subFormResource, subFormFilter, divSubForm, true );
		}

		else if (subLayoutClassName.indexOf("DynamicDisplayOnly") > -1)
		{
			DynamicDBean subBean = RestData.getOneRow(subFormResource, subFormFilter, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
			component = componExternalForm(subBean, subLayoutClassName, subFormResource, subFormFilter, subFormClassName, divSubForm, null ); // for now not DynamicDisplayForAskData form is create here, then is null
		}

		else
		{	
			
//			DynamicDBean subBean = RestData.getOneRow(subFormResource, subFormFilter, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
//			if (subBean != null)
//				return componForm (dataProviderSub, subBean, subFormClassName, divSubForm, true );
//			else
				return new Label ("Sin datos");
	}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (component == null)
		return new Label ("Sin datos error en la carga de Propiedades extendidas");
	else
	{
		component = putStyles(styleForForm, component);
		return component;
	}
}
private Component putStyles(String styleForForm, Component component) {
	if (styleForForm == null)
		return component;
	String [] tokens = styleForForm.split(Pattern.quote(","));
	int nStyles = tokens.length;
	int i = 0;
	while (nStyles > i)
	{
		String style = tokens[i];
		int idxColon = style.indexOf(":");
		String key = style.substring(0,idxColon);
		String value = style.substring(idxColon+1);
		component.getElement().getStyle().set(key,value);
		i ++;
		
	}
	return component;
}

//private Component componForm (DdbDataBackEndProvider dataProviderForm, DynamicDBean bean2, String subFormClassName, Div divSubForm, boolean isSub )
//{
//	try {
//		ArrayList<String[]> rowsColList = bean2.getRowsColList();
//		if (bean2.isReadOnly()) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC 
//			buttonsForm.setVisible(false);
//		if (isSub == false)
//			selectedRow = bean2;
//		Class<?> dynamicForm = Class.forName(subFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
//		display = dynamicForm.newInstance();
//		Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
//		Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
//		Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
//		
//		setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
//		setRowsColList.invoke(display,rowsColList);//rowsColListGrid);
//		setBinder.invoke(display,binder);
//		
//		setBean.invoke(display,bean2);
//		setDataProvider.invoke(display, dataProviderForm);
//		divSubForm.removeAll();
//		if (subFormClassName.indexOf("Generated") > -1)
//		{
//			Method createContent= dynamicForm.getMethod("createContent",new Class[] { FormButtonsBar.class});
//			Object divInSubDisplay = createContent.invoke(display, buttonsForm);
//			divSubForm.add((Component)divInSubDisplay);
//		}
//		else
//		{
//			divSubForm.add((Component)display);
//		}
////ADDING SUB GRIDS			
//		String resourceSubGrid = extractResourceSubGrid(bean2,0);//"CR-ped_proveed_cab.List-ped_proveed_lin"; // TODO adapt to use more than one subresource , use a variable instead of 9
//		Div divSubFormSubGrid = new Div(); 
//		String tabsList = rowsColList.get(0)[12];
//		if (resourceSubGrid != null && (tabsList == null || tabsList.length() == 0)) // there only one tab
//		{
//			divSubFormSubGrid.add(componSubgrid(bean2, resourceSubGrid));
//		}
//		else
//		{
//			divSubFormSubGrid.removeAll();
//			divSubFormSubGrid.add(createSubTabs(bean2, tabsList));
//		}
//		divSubForm.add(divSubFormSubGrid);
//	} catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (InstantiationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IllegalAccessException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (NoSuchMethodException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (SecurityException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IllegalArgumentException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (InvocationTargetException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//	return divSubForm;
//}
private Div componSubgrid(DynamicDBean bean, String resourceSubGrid2) {
	DynamicViewGrid subDynamicViewGrid = new DynamicViewGrid();
	subDynamicViewGrid.setCache(cache);
	subDynamicViewGrid.setButtonsRowVisible(isSubResourceReadOnly(resourceSubGrid2)== false);
//		subDynamicViewGrid.getElement().getStyle().set("height","100%");
	subDynamicViewGrid.setResourceName(resourceSubGrid2);
	if (resourceSubGrid2.indexOf(".")> -1)
		subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid2));
	subDynamicViewGrid.setupGrid(true,true);
	subDynamicViewGrid.setParentRow(selectedRow);
//	subDynamicViewGrid.setDisplayParent(display);
//	subDynamicViewGrid.setBeanParent(setBean);
//	subDynamicViewGrid.setParentGrid(this);
//	subDynamicViewGrid.getGrid().select(getKeepSelectedChild());
//	divSubGrid.add(subDynamicViewGrid );
//	keepSubGridToBeAlterExternally(subDynamicViewGrid);
	Div divTab = new Div();
	divTab.getStyle().set("height","100%");
	divTab.add(subDynamicViewGrid );
	return divTab;
	
}
public String componFKFilter(DynamicDBean bean, String resourceSubGrid) {
	String fKfilter = JSonClient.getHt().get(resourceSubGrid);
//	"FASE_CABEZERA" = ["FASE"]
//			 and "CLAVE_ALMACEN" = ["CLAVE_ALMACEN"]
//			 and "N_PEDIDO" = ["N_PEDIDO"]}
	int step = 0;
	String componFilter = "";
	int lengthFKfilter = 0; 
	if (fKfilter != null)
		lengthFKfilter = fKfilter.length();
	else
		{
		System.err.println("ERROR FK NO CARGADA -------"+ resourceSubGrid );
		JSonClient.keepFKinHT(resourceSubGrid, null, cache, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		fKfilter = JSonClient.getHt().get(resourceSubGrid);
		}
//	int leftLength = lengthFKfilter;
	while (lengthFKfilter > 0 || fKfilter.length()  > 0)
	{
		int idXEqual = fKfilter.indexOf("=");
		if (idXEqual == -1)
			break;
		int idXMark = fKfilter.indexOf("]");
		if ((fKfilter.startsWith("\n and"))|| 
		    (fKfilter.startsWith("\n AND"))) 
			step = 6;
		else if (((fKfilter.indexOf("and") > -1 && fKfilter.indexOf("and") < 5)) ||
			     ((fKfilter.indexOf("AND") > -1 && fKfilter.indexOf("AND") < 5)))
			     
		{
			step = fKfilter.indexOf("AND") + 4;
		}
		else
			step = 0;
		String fKfieldName = fKfilter.substring(step+1, idXEqual - 2  );
		String parentfieldName = fKfilter.substring(4+idXEqual, idXMark - 1  );
		String parentValue = bean.getRowJSon().get(parentfieldName).asText();
		if (isADate(parentValue) && AppConst.FORMAT_FOR_DATETIME_FOR_JOIN.length() > 0) {
			parentValue = "=" +AppConst.FORMAT_FOR_DATETIME_FOR_JOIN.replace("#value#",parentValue ) + "%20and%20";
			}
		else 
		{
			parentValue = "='" +parentValue+ "'%20AND%20";
		}
		componFilter = componFilter + fKfieldName + parentValue;
		lengthFKfilter = lengthFKfilter - idXMark;
		fKfilter = fKfilter.substring(idXMark+1);
		
	}
	if (componFilter.length()>9)
		componFilter = componFilter.substring(0, componFilter.length()-9); // to delete last and
	if (componFilter.indexOf("'null'") >-1)
		componFilter = componFilter.replaceAll("'null'", "null");
	return componFilter;
}
private Component componDynamicGridDisplay(String subLayoutClassName, String subFormResource, String subFormFilter,
		String subFormClassName, Div divSubForm, boolean b) {
	return componDynamicGridDisplay(subLayoutClassName, subFormResource, subFormFilter,
			subFormClassName, divSubForm, b, null) ;
}	
private Component componDynamicGridDisplay(String subLayoutClassName, String subFormResource, String subFormFilter,
		String subFormClassName, Div divSubForm, boolean b, String extraFilterToSelect) {// DynamicDBean preSelectRow) {
	try {
//		setVisibleRowData(true);
//		ArrayList<String[]> rowsColList = bean2.getRowsColList();
//		if (bean2.isReadOnly()) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC 
//			buttonsForm.setVisible(false);
//		if (isSub == false)
//			selectedRow = bean2;
//		keepRowBeforChanges = new DynamicDBean(); 
//		keepRowBeforChanges = RestData.copyDatabean(bean);
//		Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
		Class<?> dynamicForm = Class.forName(subLayoutClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
		Object oDynamicForm = dynamicForm.newInstance();
//		Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
//		Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
//		Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
		Method setDisplayFormClassName = dynamicForm.getMethod("setDisplayFormClassName", new Class[] {String.class} );
//		Method setQueryFormClassName = dynamicForm.getMethod("setQueryFormClassName", new Class[] {String.class} );
		Method setResourceName = dynamicForm.getMethod("setResourceName", new Class[] {String.class} );
		Method setFilter = dynamicForm.getMethod("setFilter", new Class[] {String.class} );
		
		setDisplayFormClassName.invoke(oDynamicForm, subFormClassName);
		setResourceName.invoke(oDynamicForm, subFormResource);
		setFilter.invoke(oDynamicForm, subFormFilter);
//		setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
//		setRowsColList.invoke(display,rowsColList);//rowsColListGrid);
//		setBinder.invoke(display,binder);
		
//		setBean.invoke(display,bean2);
//		setDataProvider.invoke(display, dataProviderForm);
		divSubForm.removeAll();
		if (extraFilterToSelect != null)
		{
			DynamicDBean beanPreSelect = RestData.getOneRow(subFormResource, componFilterRowPreseleted(subFormFilter,extraFilterToSelect), UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
			Method createContent= dynamicForm.getMethod("createContent", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class});
			Method setExtraFilterToSelect= dynamicForm.getMethod("setExtraFilterToSelect", new Class[] {String.class});
			setExtraFilterToSelect.invoke(oDynamicForm, extraFilterToSelect );
			Object divInSubDisplay = createContent.invoke(oDynamicForm, beanPreSelect );
			divSubForm.add((Component)divInSubDisplay);
		}
		else
		{
			Method createContent= dynamicForm.getMethod("createContent");
			Object divInSubDisplay = createContent.invoke(oDynamicForm);
			divSubForm.add((Component)divInSubDisplay);
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

//	display.beforeEnter(null);
//	gridSplitDisplay.getElement().removeAllChildren();//removeChild(display.getElement());
//	gridSplitDisplay.getElement().appendChild(grid.getElement());
//	gridSplitDisplay.getElement().appendChild(display.getElement());

//UI.getCurrent().navigate("dymanic");
	
	
	return divSubForm;
}

private Component componDynamicQryGrid(String subLayoutClassName, String subFormResource, String subFormFilter,
		String querySubFormClassName, Div divSubForm, boolean b) {
	try {
		Class<?> dynamicForm = Class.forName(subLayoutClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
		Object oDynamicForm = dynamicForm.newInstance();
//		Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
//		Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
//		Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
		Method setQueryFormClassName = dynamicForm.getMethod("setQueryFormClassName", new Class[] {String.class} );
		Method setResourceName = dynamicForm.getMethod("setResourceName", new Class[] {String.class} );
		Method setFilter = dynamicForm.getMethod("setFilter", new Class[] {String.class} );
		
		setQueryFormClassName.invoke(oDynamicForm, querySubFormClassName);
		setResourceName.invoke(oDynamicForm, subFormResource);
		setFilter.invoke(oDynamicForm, subFormFilter);
//		setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
//		setRowsColList.invoke(display,rowsColList);//rowsColListGrid);
//		setBinder.invoke(display,binder);
		
//		setBean.invoke(display,bean2);
//		setDataProvider.invoke(display, dataProviderForm);
		divSubForm.removeAll();
		Method createContent= dynamicForm.getMethod("createContent");
		Object divInSubDisplay = createContent.invoke(oDynamicForm);
		divSubForm.add((Component)divInSubDisplay);
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

//	display.beforeEnter(null);
//	gridSplitDisplay.getElement().removeAllChildren();//removeChild(display.getElement());
//	gridSplitDisplay.getElement().appendChild(grid.getElement());
//	gridSplitDisplay.getElement().appendChild(display.getElement());

//UI.getCurrent().navigate("dymanic");
	
	
	return divSubForm;
}
private Component componDynamicGridOnly(String subLayoutClassName, String subFormResource, String subFormFilter,
		Div divSubForm, boolean b) {
	try {
		Class<?> dynamicForm = Class.forName(subLayoutClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
		Object oDynamicForm = dynamicForm.newInstance();
//		Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
//		Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
//		Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
//		Method setQueryFormClassName = dynamicForm.getMethod("setQueryFormClassName", new Class[] {String.class} );
		Method setResourceName = dynamicForm.getMethod("setResourceName", new Class[] {String.class} );
		Method setFilter = dynamicForm.getMethod("setFilter", new Class[] {String.class} );
		
//		setQueryFormClassName.invoke(oDynamicForm, querySubFormClassName);
		setResourceName.invoke(oDynamicForm, subFormResource);
		setFilter.invoke(oDynamicForm, subFormFilter);
//		setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
//		setRowsColList.invoke(display,rowsColList);//rowsColListGrid);
//		setBinder.invoke(display,binder);
		
//		setBean.invoke(display,bean2);
//		setDataProvider.invoke(display, dataProviderForm);
		divSubForm.removeAll();
		Method createContent= dynamicForm.getMethod("createContent");
		Object divInSubDisplay = createContent.invoke(oDynamicForm);
		divSubForm.add((Component)divInSubDisplay);
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

//	display.beforeEnter(null);
//	gridSplitDisplay.getElement().removeAllChildren();//removeChild(display.getElement());
//	gridSplitDisplay.getElement().appendChild(grid.getElement());
//	gridSplitDisplay.getElement().appendChild(display.getElement());

//UI.getCurrent().navigate("dymanic");
	
	
	return divSubForm;
}
private Component componExternalForm(DynamicDBean subBean, String subLayoutClassName, String subFormResource, String subFormFilter,
		String subFormClassName, Div divSubForm, DynamicDisplayForAskData dynamicDisplayForAskData) {
	Binder<DynamicDBean> binderForDialog = new Binder<>(DynamicDBean.class);
	try {
//		if (dialogForShow2 != null)
//			dialogForShow = dialogForShow2;

		DdbDataBackEndProvider dataProviderPopup = new DdbDataBackEndProvider();
		dataProviderPopup.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProviderPopup.setResourceName(subFormResource);
		
		Class<?> dynamicLayout = Class.forName(subLayoutClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
		Object layoutPopup = dynamicLayout.newInstance();
		if (subLayoutClassName.indexOf("DynamicViewGrid") > -1) // is a Layout that shows a Grid
		{
			Method setResourceName = dynamicLayout.getMethod("setResourceName",new Class[] {String.class} );
			Method setFilter = dynamicLayout.getMethod("setFilter",new Class[] {String.class} );
			Method setupGrid = dynamicLayout.getMethod("setupGrid",new Class[] {Boolean.class, Boolean.class} );
//			Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class, Boolean.class} );
			Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class} );

			setResourceName.invoke(layoutPopup, subFormResource);
			String filter = ProcessParams.componFilterFromParams(subFormFilter, subBean);
			setFilter.invoke(layoutPopup, filter);
			setupGrid.invoke(layoutPopup, true, true);
			if (subBean != null && (subBean.isReadOnly() || isSubResourceReadOnly(subBean.getResourceName()))) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
				{
				setButtonsRowVisible.invoke("layoutPopup", "true");
				}

		}
		else if (subLayoutClassName.indexOf("DynamicQryGridDisplay") > -1) // is a Layout that shows a QRY with Grid
		{
			Method setResourceName = dynamicLayout.getMethod("setResourceName",new Class[] {String.class} );
			Method setFilter = dynamicLayout.getMethod("setFilter",new Class[] {String.class} );
			Method prepareLayout = dynamicLayout.getMethod("prepareLayout",new Class[] {String.class, String.class} );
//@@?				Method setupGrid = dynamicLayout.getMethod("setupGrid",new Class[] {Boolean.class, Boolean.class} );
//			Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class, Boolean.class} );
//			Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class} );

			setResourceName.invoke(layoutPopup, subFormResource);
			String filter = ProcessParams.componFilterFromParams(subFormFilter, subBean);
			setFilter.invoke(layoutPopup, filter);
			String classForQuery = "coop.intergal.ui.views.GeneratedQuery"; // @@ TODO for now is using automatic query form, inthe future use a parameter 
			prepareLayout.invoke(layoutPopup,classForQuery, subFormClassName);
//@@?				setupGrid.invoke(layoutPopup, true, true);
//			if (bean != null && (bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName()))) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
//				{
//				setButtonsRowVisible.invoke(layoutPopup, true);
//				}

		}
		else if (subFormClassName != null && subFormClassName.isEmpty() == false) // is a layout that shows one row + children if exist
			{
			Div divSubGridPopup = null;
			Method getDivDisplay = dynamicLayout.getMethod("getDivDisplay");
			Method getButtons = dynamicLayout.getMethod("getButtons");
			FormButtonsBar formButtonsBar = (FormButtonsBar)getButtons.invoke(layoutPopup);
			if (formButtonsBar != null &&(subBean.isReadOnly() || isSubResourceReadOnly(subBean.getResourceName()))) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
				formButtonsBar.setVisible(false);
			Div divDisplayPopup = (Div) getDivDisplay.invoke(layoutPopup);
			if (subLayoutClassName.indexOf("DynamicDisplayForAskData") == -1) // is a form thta ask data for a process
				{
				Method getDivSubGrid = dynamicLayout.getMethod("getDivSubGrid");
				divSubGridPopup = (Div) getDivSubGrid.invoke(layoutPopup);
				}
				
			
		
//		DynamicDisplaySubgrid dynamicDisplaySubgrid = new DynamicDisplaySubgrid();
//		Div divDisplayPopup = dynamicDisplaySubgrid.getDivDisplay();
//		Div divSubGridPopup =  dynamicDisplaySubgrid.getDivSubGrid();//new Div();
			Object divInDisplayPopup = new Div();
//			setVisibleRowData(true);
//			if (bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName())) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
//				buttonsForm.setVisible(false);
			selectedRow = subBean;
			DynamicDBean keepRowBeforChanges = new DynamicDBean(); 
			keepRowBeforChanges = RestData.copyDatabean(subBean);
//		Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(subFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			Object displayPopup = dynamicForm.newInstance();
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
		
			Method setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			ArrayList<String[]> rowsColListGridPopup = dataProviderPopup.getRowsColList();
			setRowsColList.invoke(displayPopup,rowsColListGridPopup);
			setBean.invoke(displayPopup,subBean);
			setBinder.invoke(displayPopup,binderForDialog);
			setDataProvider.invoke(displayPopup, dataProviderPopup);
//			setBean.invoke(displayPopup,bean);
//??			resourcePopup = subBean.getResourceName(); // when is a display the bean is the on e to show and has the resourcename

			divDisplayPopup.removeAll();
			if (subFormClassName.indexOf("Generated") > -1)
			{
		//	setDataProvider.invoke(display, dataProvider);
				Method createContent= dynamicForm.getMethod("createContent",new Class[] { FormButtonsBar.class, GenericClassForMethods.class});
//				Method setdVGrid= dynamicForm.getMethod("setDVGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class});
//				setdVGrid.invoke(display, this); // to use methods in this class
				divInDisplayPopup = createContent.invoke(displayPopup, null, dynamicDisplayForAskData.getGenericClassForMethods() );
				divDisplayPopup.add((Component)divInDisplayPopup);
			}
			else
			{
				divDisplayPopup.add((Component)displayPopup);
			}
		
		
//		divDisplay.remove((Component) display);
			if (subLayoutClassName.indexOf("DynamicDisplayForAskData") == -1 && (subLayoutClassName.indexOf("DynamicDisplayOnly") == -1)) // this layout for now doesn't have subgrid
				{
				String resourceSubGrid = extractResourceSubGrid(subBean,0);
				divSubGridPopup.removeAll();
				String tabsList = rowsColListGrid.get(0)[12];
				if (resourceSubGrid != null && (tabsList == null || tabsList.length() == 0)) // there only one tab
					{
		//	divSubGrid.add(componSubgrid(bean, resourceSubGrid));
					Div content0=new Div(); 
					divSubGridPopup.add(fillContent("",content0, 0 , subBean));	
//??			setDataProvider.invoke(display, subDynamicViewGrid.getDataProvider());
				}
				else if (resourceSubGrid != null)
				{
					divSubGridPopup.removeAll();
					divSubGridPopup.add(createSubForms(subBean, tabsList));
				}
			}
		}
		return (Component) layoutPopup;
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
	return null;
}
private String componFilterRowPreseleted(String subFormFilter, String extraFilterToSelect) {
	String extraFilter = null;
	if (extraFilterToSelect != null &&extraFilterToSelect.indexOf("#SVKN#") > -1) // #SVN = Session Variable Key Name  By Example- > CLAVE_ALMACEN=#SVKN#"DynamicViewGrid.lastAlmacen#SVNKEnd#
	{
		int idxStart = extraFilterToSelect.indexOf("#SVKN#")+6;
		int idxEnd = extraFilterToSelect.indexOf("#SVNKEnd#");
		String sVkn = extraFilterToSelect.substring(idxStart,idxEnd );
		String vForFilter = UtilSessionData.getFormParams(sVkn);
		if (vForFilter != null)
		{
			extraFilter = extraFilterToSelect.substring(0,idxStart-6 )+vForFilter;//+extraFilterToSelect.substring(idxEnd);
			if (subFormFilter != null && subFormFilter.length() > 1)
				extraFilter = "("+extraFilter + ")" + "%20AND%20(" +subFormFilter + ")";
		}
		else
			return subFormFilter;			
	}
	else if (extraFilterToSelect != null && extraFilterToSelect.length() > 1)
	{
		extraFilter = extraFilterToSelect;
		if (subFormFilter != null && subFormFilter.length() > 1)
			extraFilter = "("+extraFilter + ")" + "%20AND%20(" +subFormFilter + ")";

	}
	else 
	{
		return subFormFilter;
	}
	System.out.println("DynamicViewGrid.componFilterRowPreseleted() extraFilter....:"+extraFilter);
	return extraFilter;
}
private boolean isSubResourceReadOnly(String resourceSubGrid2) {
	try {
//		if (isResourceReadOnly != null)
//			return isResourceReadOnly;
		JsonNode extProp = JSonClient.get("JS_ExtProp", resourceSubGrid2, cache, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		if (extProp.get("isReadOnly") != null)
		{
			isResourceReadOnly = extProp.get("isReadOnly").asBoolean();
			return isResourceReadOnly;
		}
		else
		{
			isResourceReadOnly = false;
			return isResourceReadOnly;
		}	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
}
private boolean isADate(String parentValue) {
//	2021-01-11T00:00:00
	if (parentValue.length() != 19)
		return false;
	if (parentValue.substring(10,11).equals("T") && parentValue.substring(13,14).equals(":") && parentValue.substring(16,17).equals(":"))
		return true;
	return false;
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


