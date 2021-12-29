package coop.intergal.ui.views;

import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.STYLES_CSS;
import static coop.intergal.AppConst.STYLES_FORM_ITEM_CSS;
import static coop.intergal.AppConst.STYLES_FORM_LAYOUT_ITEM_CSS;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import org.vaadin.intergal.validation.DynValidator;
import org.vaadin.intergal.validation.ValidationMetadata;
import org.vaadin.textfieldformatter.NumeralFieldFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.StatusChangeEvent;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.components.EsDatePicker;
import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.components.detailsdrawer.DetailsDrawer;
import coop.intergal.ui.security.SecurityUtils;
import coop.intergal.ui.util.GenericClassForMethods;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.ui.utils.TranslateResource;
import coop.intergal.ui.utils.UiComponentsUtils;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.ui.utils.converters.DecimalFormatter;
import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

//@PageTitle("Payments")
//@Route(value = "gridDetails", layout = MainLayout.class)
@CssImport(value = STYLES_CSS, themeFor="dynamic-grid-display")
@CssImport(value = STYLES_FORM_ITEM_CSS, themeFor = "vaadin-form-item")
//@CssImport(value = STYLES_FORM_ITEM_CSS, themeFor = "vaadin-text-field")
@CssImport(value = STYLES_FORM_LAYOUT_ITEM_CSS, themeFor = "vaadin-form-item")
@Uses(NumberField.class) 
public class GeneratedUtil  {//, AfterNavigationListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CLASSNAME_FOR_FORM_QUERY = ".formMargin50.formMarginL50";
//	private Grid<DynamicDBean> grid;
	private DynamicViewGrid grid;

 //   private ListDataProvider<Payment> dataProvider;
//	private DdbDataBackEndProvider dataProvider;
    private DetailsDrawer detailsDrawer;
    protected Binder<DynamicDBean> binder;
//	private FormLayout form;
//	private ArrayList<String[]> rowsColList;
//	private ArrayList<String[]> rowsFieldList;
//	private ArrayList<String[]> rowsColListGrid;
	private static CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	private static DecimalFormatter decimalFormatter = new DecimalFormatter();

	private Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param

	private String title;
	private String resource;
	private DynamicDBean bean;
	private DynamicDBean selectedRow;
//	private FormLayout form;
	private static Dialog dialogForPick;
	private static String pickMapFields; 
	private FormButtonsBar buttonsForm;
	
	public FormButtonsBar getButtonsForm() {
		return buttonsForm;
	}

	public void setButtonsForm(FormButtonsBar buttonsForm) {
		this.buttonsForm = buttonsForm;
	}
	private GenericClassForMethods genericClassForMethods;
	public GenericClassForMethods getGenericClassForMethods() {
		return genericClassForMethods;
	}

	public void setGenericClassForMethods(GenericClassForMethods genericClassForMethods) {
		this.genericClassForMethods = genericClassForMethods;
	}
	private Hashtable<String, String[]> resourceAndSubresources = new Hashtable<String, String[]>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param

	private Div divSubGrid; 



    
    public DynamicDBean getBean() {
		return bean;
	}

	public void setBean(DynamicDBean bean) {
		this.bean = bean;
	}

	public DynamicViewGrid getGrid() {
		return grid;
	}

	public void setGrid(DynamicViewGrid grid) {
		this.grid = grid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Component createTabs(String resourceName,ArrayList<String[]> rowsFieldList, Boolean isQuery, Boolean cache,String tabsLabels) {
			String [] tokens = tabsLabels.split(Pattern.quote(","));
//			int i = 0;
//		   	Div contentyDiv0 = new Div(); 
			Tab tab0 =null ;Tab tab1=null ;Tab tab2=null ;Tab tab3=null ;Tab tab4=null ;Tab tab5=null ;Tab tab6=null ;Tab tab7=null ;
			FlexBoxLayout content0=null; 
			FlexBoxLayout content1=null;
			FlexBoxLayout content2=null; FlexBoxLayout content3=null;
			FlexBoxLayout content4=null; 
			FlexBoxLayout content5=null; 
			FlexBoxLayout content6=null; 
			FlexBoxLayout content7=null; 

		   	int nTabs = tokens.length;
		   	String tabTitle;
//			while (tokens.length > i)
//			{ 
				if (nTabs > 0)
				{
					tabTitle = tokens[0];
					tab0 = new Tab(tabTitle);
					content0 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"0"));
					content0.setWidthFull();
					
				}
				if (nTabs > 1)
				{
					tabTitle = tokens[1];
					tab1 = new Tab(tabTitle);
					content1 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"1"));
					content1.setWidthFull();
					content1.setVisible(false);
					
				}
				if (nTabs > 2)
				{
					tabTitle = tokens[2];
					tab2 = new Tab(tabTitle);
					content2 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"2"));
					content2.setWidthFull();
					content2.setVisible(false);
					
				}
				if (nTabs > 3)
				{
					tabTitle = tokens[3];
					tab3 = new Tab(tabTitle);
					content3 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"3"));
					content3.setWidthFull();
					content3.setVisible(false);

					
				}
				if (nTabs > 4)
				{
					tabTitle = tokens[4];
					tab4 = new Tab(tabTitle);
					content4 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"4"));		
					content4.setWidthFull();
					content4.setVisible(false);

				}
				if (nTabs > 5)
				{
					tabTitle = tokens[5];
					tab5 = new Tab(tabTitle);
					content5 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"5"));
					content5.setWidthFull();
					content5.setVisible(false);
					
				}
				if (nTabs > 6)
				{
					tabTitle = tokens[6];
					tab6 = new Tab(tabTitle);
					content6 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"6"));
					content6.setWidthFull();
					content6.setVisible(false);

					
				}
				if (nTabs > 7)
				{
					tabTitle = tokens[7];
					tab7 = new Tab(tabTitle);
					content7 = new FlexBoxLayout(createDetails(resourceName,rowsFieldList, isQuery, cache,"7"));		
					content7.setWidthFull();
					content7.setVisible(false);

				}				
//				i++;
//			}

	 
	    	Map<Tab, Component> tabsToPages = new HashMap<>();
	 //   	Tabs tabs = new Tabs(tab0,tab1);
	    	Div pages =null ;
	      	if (nTabs > 7)
			{
	      		tabsToPages.put(tab0, content0);
	      		tabsToPages.put(tab1, content1);
	      		tabsToPages.put(tab2, content2);
	      		tabsToPages.put(tab3, content3);
	      		tabsToPages.put(tab4, content4);
	      		tabsToPages.put(tab5, content5);
	      		tabsToPages.put(tab6, content6);
	      		tabsToPages.put(tab7, content7);
	      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4, tab5, tab6,tab7);
	      		pages = new Div(content0, content1, content2,content3, content4, content5, content6 , content7);
	      		tabs.addSelectedChangeListener(event -> {
	      			tabsToPages.values().forEach(page -> page.setVisible(false));
	      			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	      			selectedPage.setVisible(true);
	    	});
		   	Div content = new Div();
	    	content.add(tabs, pages);
			return content;
			}
	      	else     	    	
	      	if (nTabs > 6)
			{
	      		tabsToPages.put(tab0, content0);
	      		tabsToPages.put(tab1, content1);
	      		tabsToPages.put(tab2, content2);
	      		tabsToPages.put(tab3, content3);
	      		tabsToPages.put(tab4, content4);
	      		tabsToPages.put(tab5, content5);
	      		tabsToPages.put(tab6, content6);
	      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4, tab5, tab6);
	      		pages = new Div(content0, content1, content2,content3, content4, content5, content6 );
	      		tabs.addSelectedChangeListener(event -> {
	      			tabsToPages.values().forEach(page -> page.setVisible(false));
	      			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	      			selectedPage.setVisible(true);
	    	});
		   	Div content = new Div();
	    	content.add(tabs, pages);
			return content;
			}
	      	else     	
	      	if (nTabs > 5)
			{
	      		tabsToPages.put(tab0, content0);
	      		tabsToPages.put(tab1, content1);
	      		tabsToPages.put(tab2, content2);
	      		tabsToPages.put(tab3, content3);
	      		tabsToPages.put(tab4, content4);
	      		tabsToPages.put(tab5, content5);
	      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4, tab5);
	      		pages = new Div(content0, content1, content2,content3, content4, content5 );
	      		tabs.addSelectedChangeListener(event -> {
	      			tabsToPages.values().forEach(page -> page.setVisible(false));
	      			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	      			selectedPage.setVisible(true);
	    	});
		   	Div content = new Div();
	    	content.add(tabs, pages);
			return content;
			}
	      	else     	
	    	if (nTabs > 4)
	    		{
	    		tabsToPages.put(tab0, content0);
	    		tabsToPages.put(tab1, content1);
	       		tabsToPages.put(tab2, content2);
	    		tabsToPages.put(tab3, content3);
	    		tabsToPages.put(tab4, content4);
	    		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4);
	    		pages = new Div(content0, content1, content2,content3, content4 );
	    	   	tabs.addSelectedChangeListener(event -> {
	        	    tabsToPages.values().forEach(page -> page.setVisible(false));
	        	    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	        	    selectedPage.setVisible(true);
	        	});
	    	   	Div content = new Div();
	        	content.add(tabs, pages);
	    		return content;
	    		}
	    	else if (nTabs > 3)
	    		{
	    		tabsToPages.put(tab0, content0);
	    		tabsToPages.put(tab1, content1);
	       		tabsToPages.put(tab2, content2);
	    		tabsToPages.put(tab3, content3);
	    		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3);
	    		pages = new Div(content0, content1, content2,content3);
	    	   	tabs.addSelectedChangeListener(event -> {
	        	    tabsToPages.values().forEach(page -> page.setVisible(false));
	        	    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	        	    selectedPage.setVisible(true);
	        	});
	    	   	Div content = new Div();
	        	content.add(tabs, pages);
	    		return content;

	    		}
	      	else if (nTabs > 2)
	      		{
	      		tabsToPages.put(tab0, content0);
	      		tabsToPages.put(tab1, content1);
	      		tabsToPages.put(tab2, content2);
	      		Tabs tabs = new Tabs(tab0, tab1, tab2);
	      		pages = new Div(content0, content1, content2);
	      		tabs.addSelectedChangeListener(event -> {
	      			tabsToPages.values().forEach(page -> page.setVisible(false));
	      			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	      			selectedPage.setVisible(true);
	      		});
	      		Div content = new Div();
	        	content.add(tabs, pages);
	        	content.setWidthFull();
	    		return content;

	      		}
	    	else
	    		{
	     		tabsToPages.put(tab0, content0);
	    		tabsToPages.put(tab1, content1);
	    		Tabs tabs = new Tabs(tab0, tab1);
	    		pages = new Div(content0, content1 );
	     		tabs.addSelectedChangeListener(event -> {
	      			tabsToPages.values().forEach(page -> page.setVisible(false));
	      			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
	      			selectedPage.setVisible(true);
	      		});
	        	Div content = new Div();
	        	content.add(tabs, pages);
	    		return content;


	    		}


	    }
 
 
    public Component createDetails(String resourceName, ArrayList<String[]> rowsFieldList, Boolean isQuery, Boolean cache, String currentTab ) {
 //			this.binder = new Binder<DynamicDBean>(DynamicDBean.class);
    		if (binder == null)
    			binder = new Binder<DynamicDBean>(DynamicDBean.class);
//			rowsFieldList = dataProvider.getRowsFieldList(cache);
		    if (bean != null)
				binder.setBean(bean);
		  
			Iterator<String[]> itRowsFieldList = rowsFieldList.iterator();
//			private FormLayout form;
//			if (form == null)
//				form = new FormLayout();
			FormLayout form = new FormLayout();
//			if (isQuery)
//				{
//				form.setId(ResourceName+"_QRY");
//				}
			form.removeAll();
			
			form.getStyle().set("overflow", "inherit");
			Div statusLabel = new Div();
			statusLabel.getElement().getStyle().set("color", "var(--lumo-error-text-color)");
			statusLabel.getElement().getStyle().set("white-space", "nowrap");
			statusLabel.getElement().getStyle().set("position","absolute");
			statusLabel.getElement().getStyle().set("background-color","azure");
			statusLabel.getElement().getStyle().set("left","100px");
			statusLabel.getElement().getStyle().set("top","100px");
			statusLabel.getElement().getStyle().set("padding","0px 10px");
			binder.setStatusLabel(statusLabel);
//			binder.addStatusChangeListener(e -> showWarning(e));
			//with error
			if (isQuery == false)
			{
				FormLayout.FormItem itemSL = form.addFormItem(statusLabel,"");
				form.setColspan(itemSL, 40);
			}	
			form.setResponsiveSteps(
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
//			int i = 0;
//			int ii = 0;
//			Div div = new Div();
			int nRow = 0;
			while (itRowsFieldList.hasNext())
			{
				String[] rowField = itRowsFieldList.next();
				String fieldName = rowField[0];
				if (fieldName.equals("TOTAL_LINEAS"))
					System.out.println("STOP DEBUG");	
				boolean isReadOnly = isReadOnly( rowField [1]);
				boolean isPick = isPick (rowField [1]);
				boolean isRequired = isRequired( rowField [1]);
				String label = rowField[6];
				String fieldNameInUI = rowField[2];
				String idFieldTypeStr = rowField[3];
				int idFieldType = 0;
				if ( idFieldTypeStr.isEmpty() == false)
					idFieldType = new Integer (idFieldTypeStr);
				String fieldWidth = rowField[7];
				String fieldSize = rowField[13];
				String validationRuleName = rowField[14];
				String classNames =  rowField[8];
				String classNamesForm = ""; 
				String classNamesItem = ""; 
				String tabNumber = rowField[18];
				String fieldHeight = rowField[19];	
				String toolTip = rowField[16].toString();
				String tagsForVisibility = rowField[21].toString();
				String tagsForEdition = rowField[22].toString();
				String tagsForQueryEdition = rowField[23].toString();
				String idButtonBarForButtons = rowField[25].toString();

				boolean visibleByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForVisibility);
				boolean editableByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForEdition);
				boolean editableQryByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForQueryEdition);
				String defaultValueForQuery = rowField[24].toString();
				if (defaultValueForQuery == null || defaultValueForQuery.isEmpty() || defaultValueForQuery.equals("null"))
					defaultValueForQuery = "";
				if (tabNumber.isEmpty() || tabNumber.equals(currentTab))
					{
				
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
					if (nRow == 0) // first field
					{
						form.setClassName("");
						if(isQuery)
						{
							form.setId(rowField[10]);
				//			form.add(new H3("TITULO En QUERY"));
							form = addClassNames(form,CLASSNAME_FOR_FORM_QUERY);
						}
						else
						{
				//			form.setId("TITULO En FORM");
				//			form.add(new H3("TITULO En FORM"));
							form = addClassNames(form,classNamesForm.trim());
						}	
						title = rowField[9];
					
					}
				classNamesItem = classNamesItem.replace("..", ".");	
				nRow ++;
				
				System.out.println("DetailsPreview.createDetails()" +" filedName " + fieldName + " "+classNames + " isRequired " +isRequired + " validationRuleName "+validationRuleName + " idFieldType "+ idFieldType);

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
		//		tf.setValueChangeMode(ValueChangeMode.EAGER);  
		//		tf.setRequired(true);//(isRequired);
				if (toolTip != null && toolTip.length() >0 )
				{
					toolTip = toolTip.replace("</CR>", "\n");
					tf.setTitle(toolTip);
				}	
				tf.setId("tf"+fieldNameInUI);
				tf.addThemeVariants(TextFieldVariant.LUMO_SMALL);
				tf.setReadOnly(isReadOnly || !editableByTag);
		//		tf.setReadOnly(!editableByTag);
				if (visibleByTag == false)
				{
					Span s = new Span();
					FormLayout.FormItem item = form.addFormItem(s, "" );
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
				}
				else if (idFieldType == 10) // is a button
				{
		//			Button b = new Button(label);
					Button b =coop.intergal.ui.util.UIUtils.createPrimaryButton(label);
					b.setId(label);
					b.addClickListener(e-> proccesButton(b));
					//		Div ib = (Div) UiComponentsUtils.findComponent(UI.getCurrent(), "itemButtons");
					if (idButtonBarForButtons.equals("2")) // Botonera formulario
					{
						buttonsForm.getCustomButtons().add(b);
					}
					else if (idButtonBarForButtons.equals("3")) // Formulario
					{
						FormLayout.FormItem item = form.addFormItem(b, "");
						item = addClassNames(item, classNamesItem);
						item.setId(fieldNameInUI);
						form.setColspan(item, colspan);
					}	
				}
				else if (fieldName.equals("#SPACE#"))
				{
					Span s = new Span();
					FormLayout.FormItem item = form.addFormItem(s, label );
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
				}
				else if (idFieldType == 2) // is TextArea
				{
					TextArea ta = new TextArea();
					if (fieldHeight.isEmpty())
						ta.setHeight(AppConst.DEFAULT_FIELD_HEIGHT);
					else
						ta.setHeight(fieldHeight);
					Div l = alingLabel(label); 
					FormLayout.FormItem item = form.addFormItem(ta, l );
					item.setId(fieldNameInUI);
// not in clon		item.addClickListener(e ->ShowFieldToEdit(e.getSource().getId()));
					item = addClassNames(item, classNamesItem);
					form.setColspan(item, colspan);
					ta.setWidth(fieldWidth);
//	not in clon		if (deFaultValue.isEmpty() == false)	
//						{
//						ta.setValue(deFaultValue);
//						}
					if (isRequired && isQuery == false )
					{	
					binder.forField(ta).asRequired()
//						.withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.isRequired",
//							ValidationMetadata.of(String.class)))
							.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));

					}
					else 
					{		
						binder.bind(ta, fieldNameInUI);
					}
					
				}
				else if (idFieldType == 1 && isQuery == false) // is Date
				{
					EsDatePicker dp = new EsDatePicker();
					dp.setReadOnly(isReadOnly || !editableByTag);

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
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					dp.setWidth(fieldWidth);
				}
				else if (idFieldType == 5 && isQuery == false) // is Number
				{
					IntegerField nf = new IntegerField();
					nf.setReadOnly(isReadOnly || !editableByTag);

//					nf.setValueChangeMode(ValueChangeMode.EAGER); 
					nf.setId("if"+fieldNameInUI);
					nf.getElement().setAttribute("theme", "small");
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
//					setValidators(null, nf, fieldNameInUI, validationRuleName, isRequired, isQuery, cache);
					setBeanValidators(validationRuleName, isQuery, cache) ;
					if (isRequired && isQuery == false && fieldSize.length() == 0)
					{
						binder.forField(nf).asRequired()
//						.withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.isRequired",
//								ValidationMetadata.of(Integer.class)))
								.bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
//						binder.forField(nf).withValidator(new Validator<Integer>() {
//				            @Override
//				            public ValidationResult apply(Integer integer, ValueContext valueContext) {
//				                System.out.println("Got " + integer);
//				                return ValidationResult.ok();
//				            }
//				        }).bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));//.bind("col9");
					}
					if (fieldSize.length() > 0 && isQuery == false)	
					{
	//					nf.setMax(100);
						System.out.println("****** FIELD WITH WARNING "+ nf.getId());
						if (isRequired)
							{
							binder.forField(nf).asRequired()
						
							.withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.lessThan#"+getMaxValueForNumber(fieldSize),//+";"+isRequired,
								ValidationMetadata.of(Integer.class)))
//						.withValidator(e -> {
//					         return e == 0;
//					      }, "Maybe you should enter more than 5 characters", ErrorLevel.WARNING)
								.bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
							}
						else
						{
							{
							binder.forField(nf)
							.withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.lessThan#"+getMaxValueForNumber(fieldSize),//+";"+isRequired,
								ValidationMetadata.of(Integer.class)))
								.bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
							}

						}
					}
					
					else
					{
						binder.forField(nf)
						.bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
					
					}	
//********
					
					FormLayout.FormItem item = form.addFormItem(nf, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					nf.setWidth(fieldWidth);
				}
				else if (idFieldType == 3  && isQuery == false) // is currency
				{
				//	int nDecimals = idFieldType - 100 ; 
					TextField cTf = new TextField();
					cTf.setReadOnly(isReadOnly || !editableByTag);

					cTf.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
					cTf.addThemeVariants(TextFieldVariant.LUMO_SMALL);
//					cTf.addClassName("alignRigth");
//					new NumeralFieldFormatter(".", ",", nDecimals).extend(bdf);
//					nf.setValueChangeMode(ValueChangeMode.EAGER); 
					cTf.setId("tf"+fieldNameInUI);
//					cTf.getElement().setAttribute("theme", "small");
					Div l = alingLabel(label); 
//					binder.bind(cTf, fieldNameInUI);
					if (isRequired)
						binder.forField(cTf).asRequired()
						.bind(d-> currencyFormatter.encode(CurrencyFormatter.getCents(d.getCol(fieldNameInUI))), (d,v)-> d.setColInteger(v,fieldNameInUI));

					else
						binder.forField(cTf)
						.bind(d-> currencyFormatter.encode(CurrencyFormatter.getCents(d.getCol(fieldNameInUI))), (d,v)-> d.setColInteger(v,fieldNameInUI));
					FormLayout.FormItem item = form.addFormItem(cTf, l );
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					cTf.setWidth(fieldWidth);

				}
				else if (idFieldType > 100 && isQuery == false) // is Decimal
				{
//					BigDecimalField bdf = new BigDecimalField();
					int nDecimals = idFieldType - 100 ; 
					TextField bdf = new TextField();
					bdf.setReadOnly(isReadOnly || !editableByTag);

					bdf.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
					bdf.addThemeVariants(TextFieldVariant.LUMO_SMALL);

					new NumeralFieldFormatter(".", ",", nDecimals).extend(bdf);
//					nf.setValueChangeMode(ValueChangeMode.EAGER); 
					bdf.setId("tf"+fieldNameInUI);
					bdf.addClassName("alignRigth");
//					bdf.set
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
//					setValidators(null, nf, fieldNameInUI, validationRuleName, isRequired, isQuery, cache);
					setBeanValidators(validationRuleName, isQuery, cache) ;
					if (isRequired && isQuery == false && fieldSize.length() == 0)
					{
						binder.forField(bdf).asRequired()
//						withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.isRequired",
//						ValidationMetadata.of(String.class)))
						.bind(d-> d.getColDecimalPoint(fieldNameInUI,nDecimals), (d,v)-> d.setColDecimalPoint(v,fieldNameInUI));

//						binder.forField(bdf).withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.isRequired",
//								ValidationMetadata.of(BigDecimal.class)))
//								.bind(d-> d.getColBigDecimal(fieldNameInUI), (d,v)-> d.setColBigDecimal(v,fieldNameInUI));
					}
//		decimals doesn't apply max length, the numer of decimal already linits			else if (fieldSize.length() > 0 && isQuery == false)
//					{
//						binder.forField(bdf).withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.lessThan#"+getMaxValueForNumber(fieldSize)+";"+isRequired,
//						ValidationMetadata.of(String.class)))
//						.bind(d-> d.getColDecimalPoint(fieldNameInUI,nDecimals), (d,v)-> d.setColDecimalPoint(v,fieldNameInUI));
//
//
//					}
					
					else
					{
//						binder.bind(tf, fieldNameInUI);
						if (fieldSize.isEmpty() == false)
							bdf.setMaxLength(new Integer(fieldSize));
						binder.forField(bdf)
						.bind(d-> d.getColDecimalPoint(fieldNameInUI,nDecimals), (d,v)-> d.setColDecimalPoint(v,fieldNameInUI));
//						binder.forField(bdf).bind(d-> d.getColBigDecimal(fieldNameInUI), (d,v)-> d.setColBigDecimal(v,fieldNameInUI));
					
					}	
//********
					
					FormLayout.FormItem item = form.addFormItem(bdf, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					bdf.setWidth(fieldWidth);
				}
				else if (idFieldType == 6 )// is combobox; 
				{

					String parentResource = rowField[20];
	
					ComboBox<DynamicDBean> cB = fillComboBox(parentResource);
					cB.setReadOnly(isReadOnly || !editableByTag);
					cB.setId("cb"+fieldNameInUI);
					

//					setBeanValidators(validationRuleName, isQuery, cache) ;
		
//					cB.setValue(getRowById(bean.getCol(fieldNameInUI), cB));
					binder.forField(cB).withConverter(
							item-> Optional.ofNullable(item).map(DynamicDBean::getCol0).orElse(null),
							id-> getRowById(id, cB))
					.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));
	//				.bind( DynamicDBean::getCol11, DynamicDBean::setCol11);
//					binder.forField(cB).withConverter(
//							item-> Optional.ofNullable(item).map(DynamicDBean::getCol0).orElse(null),
//							id-> getRowById(id, cB))
//							﻿.bind( Bean::getGenderId, Bean::setGenderId);
					boolean isRightLabel = false;
					Div l = alingLabel(label); 
					FormLayout.FormItem item = form.addFormItem(cB, l );
					if (isQuery)
						{
						item.getElement().setAttribute("title","Ayuda busqueda...."); 
						}
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					cB.setWidth(fieldWidth);
					if (fieldSize.length() > 0)						
						tf.setMaxLength(new Integer(fieldSize));
					}
				else if (idFieldType == 4 )// is checkBox (boolean); 
				{
					Checkbox checkB = new Checkbox();
					setBeanValidators(validationRuleName, isQuery, cache) ;
					if (isQuery)
						{
						checkB.setReadOnly(!editableQryByTag);
						checkB.setValue(defaultValueForQuery.equals("1"));
						checkB.setIndeterminate(true);
						checkB.setId("chb"+fieldNameInUI);
					//	checkB = new Checkbox();
						checkB.addClickListener(ev->{
				            if (ev.getClickCount()>1) {
				            	checkB.setIndeterminate(true);
				            }
				        });
						}
					else	
						checkB.setReadOnly(isReadOnly || !editableByTag);
			
					if (isRequired && isQuery == false )
					{	
					binder.forField(checkB).asRequired()
							.bind(d-> d.getColBoolean(fieldNameInUI), (d,v)-> d.setColBoolean(v,fieldNameInUI));
					}
					else 
					{
						binder.forField(checkB)
							.bind(d-> d.getColBoolean(fieldNameInUI), (d,v)-> d.setColBoolean(v,fieldNameInUI));
					}
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
					FormLayout.FormItem item = form.addFormItem(checkB, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					if (isQuery)
						{
//				        Tooltip tooltip = new Tooltip();
//				        tooltip.attachToComponent(tf);
//				        tooltip.setPosition(TooltipPosition.RIGHT);
//				        tooltip.setAlignment(TooltipAlignment.LEFT);
//				        tooltip.add("Hola");
//				        tooltip.add(new Paragraph(TranslateResource.getFieldLocale("FABORRARAVISO", AppConst.PRE_CONF_PARAM)));
						item.getElement().setAttribute("title","Ayuda busqueda...."); 
						}
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					checkB.setWidth(fieldWidth);
					}	
				else  // is Text
				{
					setBeanValidators(validationRuleName, isQuery, cache) ;
					if (isQuery)
						{
						tf.setReadOnly(!editableQryByTag);
						tf.setValue(defaultValueForQuery);
						}
					else	
						tf.setReadOnly(isReadOnly || !editableByTag);
			
					if (isRequired && isQuery == false )
					{	
					binder.forField(tf).asRequired()
//						.withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.isRequired",
//							ValidationMetadata.of(String.class)))
							.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));

					}
					else 
					{		
					binder.bind(tf, fieldNameInUI);
					}
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
					FormLayout.FormItem item = form.addFormItem(tf, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					if (isQuery)
						{
//				        Tooltip tooltip = new Tooltip();
//				        tooltip.attachToComponent(tf);
//				        tooltip.setPosition(TooltipPosition.RIGHT);
//				        tooltip.setAlignment(TooltipAlignment.LEFT);
//				        tooltip.add("Hola");
//				        tooltip.add(new Paragraph(TranslateResource.getFieldLocale("FABORRARAVISO", AppConst.PRE_CONF_PARAM)));
						item.getElement().setAttribute("title","Ayuda busqueda...."); 
						}
					if (isPick)
					{
//                        idForm = null;
//                        if (form.getId().isPresent())
//                        	idForm = form.getId().get();
 						//						final form2 = form; 
						Component formForFields = tf.getParent().get().getParent().get();
						Icon icon = new Icon(VaadinIcon.DOWNLOAD_ALT);
						icon.getElement().addEventListener("click", ev->showDialogForPick(formForFields,resourceName, null,null, bean, fieldNameInUI, cache, isQuery));
						tf.setSuffixComponent(icon);
					}
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					tf.setWidth(fieldWidth);
					if (fieldSize.length() > 0)						
						tf.setMaxLength(new Integer(fieldSize));
					}
				}
//				i++;
				
			}

//			[part="error-message"] {white-space: nowrap;}

//			form.add(statusLabel);
//			form.setId(resourceName);

			return form;
	    }
    
    


// ******* PROCESAR BOTONES *********   
public void proccesButton(Button b, DynamicDBean bean2) {
	this.bean = bean2;
	proccesButton(b);
	
}

	Object proccesButton(Button b) {
		String idButton = b.getId().get();
		System.out.println("GeneratedUtil.proccesButton() " + idButton);
		if (idButton.indexOf("@POP@IDM") > -1)
		{
			runMethodFor("processButtonForNavigation",idButton);
		}
		else if (idButton.indexOf("@IDM") > -1)
		{
			runMethodFor("processButtonForNavigation",idButton);
//			processButtonForNavigation(idButton);
		}
		else if (idButton.indexOf("@IDP") > -1)
		{
			runMethodFor("processButtonForProcess",idButton);
//			processButtonForNavigation(idButton);
		}
		else if (idButton.indexOf("@CPOption") > -1)
		{
			runMethodFor("proccesButtonForContinueProcess",idButton);
//			processButtonForNavigation(idButton);
		}


		else
		{
			DataService.get().showError("Proceso no implementado");
		}
		return null;
	}

	private void runMethodFor(String methodName, String idButton) {
		System.out.println("method to run "+ methodName);
//		Class<?> dynamicQuery;
		try {
			Class<?> classForMethods = Class.forName(AppConst.CLASS_FOR_METHODS);		
			Object oClassForMethods = classForMethods.newInstance();
			if (genericClassForMethods != null)
				oClassForMethods = genericClassForMethods;
			Method method = classForMethods.getMethod(methodName, new Class[] {String.class, coop.intergal.vaadin.rest.utils.DynamicDBean.class, com.vaadin.flow.component.html.Div.class, DynamicViewGrid.class} );
//			this.getParent().get().getParent().get().getParent().get().getParent().get().getParent().get().getChildren().findFirst();
//			UI.getCurrent().getChildren().findFirst();
			method.invoke(oClassForMethods, idButton, bean, divSubGrid, grid);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
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


	
}
		
	private static DynamicDBean getRowById(String id, ComboBox cB) {
		
		if (id != null)
			{
			ListDataProvider<DynamicDBean> ListCombo = (ListDataProvider<DynamicDBean>) cB.getDataProvider();
			for (DynamicDBean bean : ListCombo.getItems()) {
		
				System.out.println("GeneratedUtil.getRowById()------>"+bean.getCol0());
				if(id.equals(bean.getCol0()))
					return bean;
				}
			}
		DynamicDBean errorBean = new DynamicDBean();
		errorBean.setCol0("1");
		errorBean.setCol1("ERROR, Reg no EXISTE");
		return errorBean ;
	}

	private DynamicDBean getID(String id) {
		System.out.println("ID "+ id);
		return new DynamicDBean();
	}

	private Object putCBintoBean(DynamicDBean d, DynamicDBean v, String fieldNameInUI) {
		// TODO Auto-generated method stub
		return null;
	}

	private DynamicDBean getCBValue(DynamicDBean d) {
		// TODO Auto-generated method stub
		return d;
	}

	private static ComboBox<DynamicDBean> fillComboBox(String parentResource) {
		ArrayList<String[]> rowsColList = new ArrayList<String[]>();
		
		String[] fieldArr  = new String[3];
		fieldArr[0] = "STOREDVALUE";
		fieldArr[1] = "";
		fieldArr[2] = "col0";
		rowsColList.add(fieldArr);
		
		fieldArr  = new String[3];
		fieldArr[0] = "DISPLAYVALUE";
		fieldArr[1] = "";
		fieldArr[2] = "col1";
		rowsColList.add(fieldArr);
//		String parentResource = getParentResource(resourceName, fieldNameInUI);
		Collection<DynamicDBean> 	teacherList = RestData.getResourceData("!!ERROR!! combo sin Resource Parent, especificar en MetaConfig ");
		if (parentResource != null && parentResource.isEmpty() == false)
		{				
			teacherList = RestData.getResourceData(0,0,parentResource, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM, rowsColList, null, true, false, null);
		}
		ComboBox<DynamicDBean> cB = new ComboBox<DynamicDBean>() ;
		cB.setItems(teacherList);
		cB.setItemLabelGenerator(DynamicDBean::getCol1);
		
		return cB;
	}

	private String getParentResource(String fieldNameInUI, String resourceName) {
		String filter="tableName='"+resourceName+"'%20AND%20FieldNameInUI='"+fieldNameInUI+"'";
		String parentResource = "";
		
		JsonNode rowsList;
		try {
			rowsList = JSonClient.get("FieldTemplate",filter, UtilSessionData.getCache(),AppConst.PRE_CONF_PARAM_METADATA,"1");

		for (JsonNode eachRow : rowsList)  {
			if (eachRow.size() > 0)
			{
				parentResource = eachRow.get("parentResource").asText();				
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Object showWarning(StatusChangeEvent e) {
//		((Object) e).getFieldValidationStatuses();
//		List<ValidationResult> listErrors = e.getBinder(),
//	    	List<ValidationResult> listErrors = binder.validate().getValidationErrors();
//	    	boolean isWarning = false;
//	    	if (listErrors != null )
//	    		{
//	    		Iterator<ValidationResult> itErrors = listErrors.iterator();
//	    		if (itErrors.hasNext())
//	    			{
//	    			ValidationResult error = itErrors.next();
//	    			String errorMsg = error.getErrorMessage();
//	    			if (errorMsg.startsWith("Aviso"))
//	    				{
//	    				isWarning = true;
//	    	//			Div statusLabel = new Div();
//	    				}
//	    			}
//	    		}
//	    	if (isWarning ) {
//	    		DataService.get().showError("NO se puede salvar hay errores en el formulario");	
//	    	}
		System.out.println("GeneratedUtil.showWarning()" );
		return null;
	}

	private String getMaxValueForNumber(String fieldSize) {
		Integer size = new Integer(fieldSize);
		String space = new String(new char[size]).replace('\0', '9');
		return space;
	}

	private void setBeanValidators(String validationRuleName,Boolean isQuery, Boolean cache) {
		if (validationRuleName.length() > 1 && isQuery == false)
		{
			if (validationRuleName.startsWith("Warning"))
				binder.withValidator(
					new DynValidator<>("org.vaadin.intergal.validation.Constraints.warningFromBackEnd#"+validationRuleName+","+cache,
							ValidationMetadata.of(DynamicDBean.class)));
			else			
				binder.withValidator(
					new DynValidator<>("org.vaadin.intergal.validation.Constraints.validateFromBackEnd#"+validationRuleName+","+cache,
							ValidationMetadata.of(DynamicDBean.class)));
		}
	}
		
		private void setFieldWarningValidators(String validationRuleName,Boolean isQuery, TextField tf) {

			if (validationRuleName.length() > 1 && isQuery == false)
			{
				if (validationRuleName.startsWith("Warning"))
				{
					AtomicReference<String> ref = new AtomicReference<>();
//				    TextField tf = new TextField();
				    Binder<AtomicReference<String>> binder = new Binder<>();
				    binder.forField(tf).withValidationStatusHandler(ev->{
				    ev.getValidationResults().stream().filter(r->r.getErrorLevel().isPresent() && r.getErrorLevel().get()==ErrorLevel.WARNING).forEach(r->System.out.println(r.getErrorMessage()));
				    })
				    .withValidator((value,ctx) -> ValidationResult.create("WARNING , you wrote: "+value, ErrorLevel.WARNING))
				    .bind(AtomicReference::get, AtomicReference::set);
					
//					binder.forField(textField).withValidationStatusHandler(ev->{	
//						ev.getValidationResults().stream().filter(r->r.getErrorLevel().isPresent() && r.getErrorLevel().get()==ErrorLevel.WARNING).forEach(warning->{
//					System.out.println("WARNING: "+warning.getErrorMessage());
//					});
//					});//./*addValidator, etc*/
				}
			}	
//	binder.forField(tf).withValidator(new DynValidator<>("org.vaadin.intergal.validation.Constraints.minLength#4",
//			ValidationMetadata.of(String.class)))
//			.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));
		
	}

	public Binder<DynamicDBean> getBinder() {
		return binder;
	}

	public void setBinder(Binder<DynamicDBean> binder) {
		this.binder = binder;
	}

	public Div getDivSubGrid() {
		return divSubGrid;
	}

	public void setDivSubGrid(Div divSubGrid) {
		this.divSubGrid = divSubGrid;
	}

	private boolean isRequired(String params) {
		if (params == null)
			return false;
		if (params.indexOf("#REQ#")>-1)
			return true;
		else 
			return false;
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
//			l.getElement().getStyle().set("text-align","right");
//			l.getElement().getStyle().set("margin-right","10px");
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
	private static boolean isPick(String params) {
		
		if (params == null)
			return false;
		if (params.indexOf("#PCK#")>-1)
			return true;
		else 
			return false;
			
	}
    private static boolean isPIckFor(String params, String forV) {
		if (params == null)
			return false;
		if (params.indexOf(forV)>-1)
			return true;
		else 
			return false;
			
	}
    
    // ANTES NO ERA STATIC

private Object showDialogForPick(Component parentTF, String resourceName, DynamicViewGrid gridChild, DynamicDBean item, DynamicDBean beanFromAGenerateForm, String fieldName, boolean cache, Boolean isQuery) { 
		
		try { 
		DynamicGridForPick dynamicGridForPick = new DynamicGridForPick(); 
		String queryFormForPickClassName = null;
		DynamicDBean currentRow = null;
//		final resourceName;
//		String resourceName = null;
		boolean isPickFromAGrid= false;
		String idForm = null;
		if (item != null) // the item is only send when comes from a a grid
			{
//			resourceName =item.getResourceName();
			isPickFromAGrid = true;
//			currentRow=item;
			}
//		else if (beanFromAGenerateForm != null)
//		{
//			currentRow = beanFromAGenerateForm;
//			binder.setBean(currentRow);
//			resourceName =currentRow.getResourceName();
//		}
//		else if (isQuery)
//		{
//			idForm = resourceName+"_QRY";
////			resourceName = beanFromAGenerateForm.getResourceName();
//		}
		else if (isQuery == false)
		{
			currentRow = binder.getBean();
//			resourceName =currentRow.getResourceName();
		}
		String filter="tableName='"+resourceName+"'%20AND%20FieldNameInUI='"+fieldName+"'";
		String parentResource = "";
		String titleGrid ="";
		
		JsonNode rowsList = JSonClient.get("CR-FieldTemplate",filter,cache,AppConst.PRE_CONF_PARAM_METADATA,"1");
		for (JsonNode eachRow : rowsList)  {
			if (eachRow.size() > 0)
			{
				parentResource = eachRow.get("parentResource").asText();
				pickMapFields =  eachRow.get("pickMapFields").asText();
//				titleGrid = eachRow.get("titleGrid").asText();
				queryFormForPickClassName =  eachRow.get("queryFormForPickClassName").asText();
			}
		}
//		queryFormForPickClassName = queryFormForPickClassName;
		if (queryFormForPickClassName == null)// || queryFormForPickClassName.equals("null"))
			return null;
		if (queryFormForPickClassName.startsWith("coop.intergal.ui.views") == false)
			queryFormForPickClassName = PACKAGE_VIEWS+queryFormForPickClassName;	
		DynamicViewGrid grid = dynamicGridForPick.getGrid();
		Class<?> dynamicQuery = Class.forName(queryFormForPickClassName);
		Object queryForm = dynamicQuery.newInstance();
//		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.tys.ui.views.DynamicViewGrid.class} );
		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );

		setGrid.invoke(queryForm,grid);
		String titleByID ="";
		if (queryFormForPickClassName.indexOf("Generated") > -1)
		{
			
			DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
			dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
			dataProvider.setResourceName(parentResource);
			Method setDataProvider= dynamicQuery.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
			Method createContent= dynamicQuery.getMethod("createDetails");
			Method setRowsColList = dynamicQuery.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			setDataProvider.invoke(queryForm,dataProvider );
//			setRowsColList.invoke(queryForm,rowsColList);
//			Method createContent= dynamicQuery.getMethod("createDetails");
//			queryForm = 
			createContent.invoke(queryForm);
			if (((GeneratedQuery) queryForm).getId().isPresent())
			{
				titleByID = ((GeneratedQuery) queryForm).getId().get();
			}
		}
		if (titleByID  != null && titleByID.length() > 2)
			dynamicGridForPick.getDivQuery().add(new H3(titleByID));	
		dynamicGridForPick.getDivQuery().add((Component)queryForm);
		 
		
		grid.setButtonsRowVisible(false);
		grid.setResourceName(parentResource);
		grid.setupGrid();

		//			subDynamicViewGrid.getElement().getStyle().set("height","100%");
//		subDynamicViewGrid.setResourceName(resourceSubGrid);
//		if (resourceSubGrid.indexOf(".")> -1)
//			subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
//		subDynamicViewGrid.setupGrid();
//		dynamicGridForPick.setRowsColList(currentRow.getRowsColList());
		if (isPickFromAGrid)
			dynamicGridForPick.addAcceptPickListener(e -> fillDataForGridPickAndAccept(gridChild, grid.getGrid().getSelectedItems(),dialogForPick,item, pickMapFields ));
		else
			dynamicGridForPick.addAcceptPickListener(e -> fillDataForPickAndAccept(parentTF, resourceName,grid.getGrid().getSelectedItems(),dialogForPick,beanFromAGenerateForm, pickMapFields ));
//			dynamicGridForPick.addAcceptPickListener(e -> fillDataForPickAndAccept(grid.getGrid().getSelectedItems(),dialogForPick, binder.getBean(), pickMapFields ));//, currentRow, pickMapFields ));
//			dynamicGridForPick.addAcceptPickListener(e -> fillDataForPickAndAccept(grid.getGrid().getSelectedItems(),dialogForPick,currentRow, pickMapFields ));//, currentRow, pickMapFields ));
//		if (dialogForPick == null)
			dialogForPick = new Dialog();
		dialogForPick.removeAll();
		dialogForPick.setModal(false);
		dialogForPick.setCloseOnOutsideClick(false);
		if (titleGrid.length() > 2)
			dialogForPick.add(new H3(titleGrid));
		dialogForPick.add(dynamicGridForPick);
		dialogForPick.open();
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private Object fillDataForPickAndAccept(Component parentTF, String idForm, Set<DynamicDBean> seletedRows, Dialog dialogForPick2, DynamicDBean currentRow, String pickMapFields) {
		StringTokenizer tokens = new StringTokenizer(pickMapFields,"#");
		if (seletedRows.iterator() ==  null || seletedRows.iterator().hasNext() == false)
		{
			dialogForPick.close();
			return null;
		}	
		DynamicDBean seletedParentRow = seletedRows.iterator().next();
		while (tokens.hasMoreElements())
		{
			String eachFieldMap = tokens.nextToken();
			int idxSeparator = eachFieldMap.indexOf(";");
			String childField = eachFieldMap.substring(0, idxSeparator);
			String parentField = eachFieldMap.substring(idxSeparator+1);
			if (currentRow != null)
				currentRow.setCol(seletedParentRow.getCol(parentField), childField);
			else
				getCOlById(parentTF, "tf" + childField).setValue(seletedParentRow.getCol(parentField));
			
		}
		if (currentRow != null)
			binder.setBean(currentRow);
		dialogForPick.close();
		return null;
	}
	private TextField getCOlById(Component parentTF, String childField) {
//		FormLayout form = (FormLayout) UiComponentsUtils.findComponent(UI.getCurrent(), idForm);
		return (TextField) UiComponentsUtils.findComponent(parentTF, childField);
//		return null;
	}

	private Object fillDataForGridPickAndAccept(DynamicViewGrid grid,Set<DynamicDBean> seletedRows, Dialog dialogForPick2, DynamicDBean currentRow, String pickMapFields) {
		StringTokenizer tokens = new StringTokenizer(pickMapFields,"#");
		if (seletedRows == null || seletedRows.isEmpty())
		{
			dialogForPick.close();
			return null;
		}
		DynamicDBean seletedParentRow = seletedRows.iterator().next();
		while (tokens.hasMoreElements())
		{
			String eachFieldMap = tokens.nextToken();
			int idxSeparator = eachFieldMap.indexOf(";");
			String childField = eachFieldMap.substring(0, idxSeparator);
			String parentField = eachFieldMap.substring(idxSeparator+1);
			currentRow.setCol(seletedParentRow.getCol(parentField), childField);						
		}
		grid.getDataProvider().refresh(currentRow);
//		binder.setBean(currentRow);
		grid.colChanged(currentRow, null, "");
		dialogForPick.close();
		return null;
	}

	// ******* COLUMNS ********
	public Column<DynamicDBean> addFormatedColumn(int i, ArrayList<String[]> rowsColListGrid, DynamicViewGrid dynamicViewGrid, GridPro<DynamicDBean> grid, boolean isGridEditable, Div itemButtoms) {  // for now grid is not editable , then was copy and adapted from DynamicViewGrid
//		String colName = "col"+i;
		
		String[] colData = rowsColListGrid.get(i);
		String colName = colData[2];
		String idFieldTypeStr = colData[3];
		String tagsForVisibility = colData[21].toString();
		String tagsForEdition = colData[22].toString();
		String idButtonBarForButtons = colData[25].toString();
 		boolean visibleByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForVisibility);
		boolean editableByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForEdition);
		if (visibleByTag == false)
			return null;
		int idFieldType = 0;
		if ( idFieldTypeStr.isEmpty() == false)
			idFieldType = new Integer (idFieldTypeStr);
		String colHeader = colData[6];
		boolean isPick = isPick (colData [1]);
		grid.setId(colData[11]); // contents titla for grid 
		Column<DynamicDBean> col = null;
		boolean isNotAParentField = colData[1].indexOf("#SORT")>-1; // parent field for now can not be use as sort column
		boolean isCOlEditable = true;;
		if (colData[1].indexOf("#CNoEDT#")>-1)
			isCOlEditable = false;
		if (editableByTag == false)
			isCOlEditable = false;
		if (idFieldType == 10) // is button
		{
			String buttonLabel = colData[7];
			Button b =coop.intergal.ui.util.UIUtils.createPrimaryButton(buttonLabel);
			b.setId(buttonLabel);
			b.addClickListener(e-> proccesButton(b));
			if (idButtonBarForButtons.equals("1")) // Botonera Listado
				itemButtoms.add(b);
		}
		if (colData[1].indexOf("#SIG#")>-1) { // #SIG# = Show In Grid
//			String header = TranslateResource.getFieldLocale(colData[0], preConfParam);
			String header = colHeader;
			if (isDate(header, idFieldType)) { /// ********** DATE **************
				if (header.indexOf("#")>0)
					header = header.substring(2); // to avoid date typ indicator "D#"
				if (isCOlEditable && isGridEditable)
				{
//					if (isNotAParentField)
//						grid.addEditColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setResizable(true).setSortProperty(colData[0]);
//					else
//						grid.addEditColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setResizable(true);
//					EsDatePicker datePicker = new EsDatePicker();
//					grid.addEditColumn(Item::getValue, new LocalDateRenderer<>(Item::getValue, "yyyy/MM/dd")).custom(datePicker, Item::setValue);
//					LocalDate getValue();
//					void setValue(LocalDate value);
					EsDatePicker datePicker = new EsDatePicker();
					col= grid.addEditColumn(item->item.getColLocalDate(colName),  new LocalDateRenderer<>(item->item.getColLocalDate(colName), "dd/MM/yyyy")).custom(datePicker,(item,date) -> {item.setColDate(date, colName); dynamicViewGrid.colChanged(item,colName,date);}).setHeader(header);
//					col..setHeader(header);item.getColLocalDate(colName)).custom(datePicker, (item,date) -> {item.setColDate(date, colName); dynamicViewGrid.colChanged(item,colName,newValue);})
//					col = grid.addEditColumn(colName).custom(datePicker, (item,date) -> item.setColDate(date, colName));//(date));
	//				System.err.println(" REVISAR ESTE CODIGO AL MIGRAR A 14 da error");
				}
				else
					if (isNotAParentField)
						col = grid.addColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).setHeader(header).setSortProperty(colData[0])
						.setResizable(true).setSortProperty(colData[0]);
					else
						col = grid.addColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).setHeader(header).setSortProperty(colData[0])
						.setResizable(true);
						
				
//		grid.addColumn(d ->d.getCol(i)).setHeader(header).setResizable(true);
			} else if (isCurrency(header,idFieldType)) { //// ********** CURRENCY *********
				if (header.indexOf("#")>0)
					header = header.substring(2);
				if (isCOlEditable  && isGridEditable) 
					if (isNotAParentField)
					{
						col = grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
					}
					else
					{
						col = grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
					}
				else
				if (isNotAParentField)
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

			}
			else if (isDecimal(header,idFieldType)) { //// ********** decimal *********
				if (header.indexOf("#")>0)
					header = header.substring(2);
				int ndecimals = idFieldType - 100;
				if (isCOlEditable  && isGridEditable) 
					if (isNotAParentField)
					{
						col = grid.addEditColumn(d -> decimalFormatter.encode(decimalFormatter.getCents(d.getCol(colName),ndecimals))).text((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
					}
					else
					{
						col = grid.addEditColumn(d -> decimalFormatter.encode(decimalFormatter.getCents(d.getCol(colName),ndecimals))).text((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
					}
				else
				if (isNotAParentField)
						{
						col = grid.addColumn(d -> decimalFormatter.encode(decimalFormatter.getCents(d.getCol(colName),ndecimals))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> decimalFormatter.encode(decimalFormatter.getCents(d.getCol(colName), ndecimals))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

			}else if (isBoolean(header,idFieldType)) {  /// ****** BOOLEAN ************
				if (header.indexOf("#")>0)
					header = header.substring(2);
				if (isCOlEditable  && isGridEditable) {
					col = grid.addEditColumn(d -> d.getColBoolean(colName)?//"Si":"No")
							TranslateResource.getFieldLocale("YES"): TranslateResource.getFieldLocale("NO"))
					.checkbox((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue))		
					.setHeader(header);
					}
				else

					if (isNotAParentField)
						{
						col = grid.addColumn(d -> d.getColBoolean(colName)?//"Si":"No")
								TranslateResource.getFieldLocale("YES"): TranslateResource.getFieldLocale("NOT"))		
								.setHeader(header)
//						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> d.getColBoolean(colName)?//"Si":"No")
								TranslateResource.getFieldLocale("YES"): TranslateResource.getFieldLocale("NOT", AppConst.PRE_CONF_PARAM))		
								.setHeader(header)
//						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

				}
				else if (isComboBox(idFieldType)) // combobox
				{
					String parentResource = colData[20];
					ComboBox<DynamicDBean> cB = fillComboBox(parentResource);	
					if ((isCOlEditable && isGridEditable))
						{
						col = grid.addEditColumn(d -> getRowById(d.getCol(colName), cB), new TextRenderer<DynamicDBean>(d-> getRowById(d.getCol(colName), cB).getCol1()))
						.custom(cB, (item, newValue) -> dynamicViewGrid.colChangedComboBox(item,colName,newValue))
						.setHeader(header)
						.setResizable(true).setSortProperty(colData[0]);
						}
					else
					{
						col = grid.addColumn(d -> getRowById(d.getCol(colName), cB).getCol1()).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
					}
						
				}
				else
					if ((isCOlEditable && isGridEditable))
						if (isNotAParentField)						
							col = grid.addEditColumn(d -> d.getCol(colName)).text((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue)).setHeader(header).setResizable(true).setSortProperty(colData[0]);
						else
							col = grid.addEditColumn(d -> d.getCol(colName)).text((item, newValue) -> dynamicViewGrid.colChanged(item,colName,newValue)).setHeader(header).setResizable(true);				
					else if (isGridEditable && isCOlEditable == false ) 
					{
//						if (isNotAParentField || isPick == false)
//							{
//							col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
//							}
//						else 
						if (isPIckFor(colData [1],"pickMapFields")){
							col = grid.addColumn(new ComponentRenderer<Label,DynamicDBean>(item->{
								 Label l = new Label("Buscar....");
								 if (item.getCol(colName) != null && item.getCol(colName).isEmpty() == false)
									 l = new Label(item.getCol(colName));
								 l.getElement().addEventListener("click", ev->dynamicViewGrid.pickParentComboTwinFormT(colName, item));
								 return l;
								 })).setResizable(true).setHeader(header).setSortProperty(colData[0]);
							
						}
						else
						if (isNotAParentField || isPick == false)
						{
							col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
						}

						else {
							col = grid.addColumn(new ComponentRenderer<Label,DynamicDBean>(item->{
								 Label l = new Label("Buscar....");
								 if (item.getCol(colName) != null && item.getCol(colName).isEmpty() == false)
									 l = new Label(item.getCol(colName));
								 l.getElement().addEventListener("click", ev->showDialogForPick(null, item.getResourceName(),dynamicViewGrid, item,null, colName, false, false) );//ev->dynamicViewGrid.pickParent(colName, item));
								 return l;
								 })).setResizable(true).setHeader(header).setSortProperty(colData[0]);
							
						}
					}	
					else
					{
					if (isNotAParentField)
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
					else 
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true) ;
				}
		}
		if (col !=null)
			{
				if (colName.equals("col0"))
					System.out.println("DynamicViewGrid.addFormatedColumn()");
				col.setKey(colName);
				System.out.println("DynamicViewGrid.addFormatedColumn() Header" + colName);
			}
		return col;
	}
	  


	private static boolean isDate(String header, int idFieldType) {
    	if (header.startsWith("D#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
    		return true; 
    	if (idFieldType== 1)
    		return true;
    	return false;
    }
    private static boolean isBoolean(String header, int idFieldType) {
    	if (idFieldType == 4)
    		return true;
    	return false;
    }
    private static boolean isComboBox(int idFieldType) {
    	if (idFieldType == 6)
    		return true;
    	return false;
    }

    private static boolean isCurrency(String header, int idFieldType) {
    	if (header.startsWith("C#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
    		return true; 
    	if (idFieldType==3)
    		return true;
    	return false;
    }
    private static boolean isDecimal(String header, int idFieldType) {
    	if (idFieldType > 100)
    		return true;
    	return false;
    }

	public static void fillDefaultValues(DynamicDBean bean) {  
		ArrayList<String[]> rowsColList = bean.getRowsColList();
//		rowsColList.
		
		Field[] fields = bean.getClass().getDeclaredFields();
		int i=0;
	//	JsonNode eachRow =  lTxsumary.get(0); // VER EN TAbeEL como gestiona que el resultado traiga varias tablas
	//	dB.setRowJSon(eachRow); 
		int maxNumberOfFields = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE;
		String maxNumberOfFieldsSTR = "";
		if (rowsColList.size() > 15)
			maxNumberOfFieldsSTR =rowsColList.get(0)[15];
		if (maxNumberOfFieldsSTR.length() > 0)
			maxNumberOfFields = new Integer(maxNumberOfFieldsSTR);
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < maxNumberOfFields)
					{
					field.setAccessible(true);
	//				String colName = getColName(rowsColList,i);
					String defaultValue = getDefaultValue(rowsColList,i);
					if (defaultValue != null && ! defaultValue.equals("null") && defaultValue.length() > 0)
						if (defaultValue.equals("user()"))
						{
							field.set(bean, SecurityUtils.getUsername());
						}
						else
							field.set(bean, defaultValue);
					i++;
					}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i>maxNumberOfFields) 
				break;
		}
	}
	private static String getDefaultValue(ArrayList<String[]> rowsColList, int i) {
		String colNameInUIinColList ="null";
		String colNameInUIGenByI = "col"+i;
		if (rowsColList.size() > i)
			colNameInUIinColList = rowsColList.get(i)[2];
		if ( colNameInUIinColList.equals(colNameInUIGenByI) || colNameInUIinColList.isEmpty() ) // if colinIU = col... then return colName 
			return rowsColList.get(i)[5];
		else // otherwise it searchs
		{
			for (String[] row : rowsColList) // search for col.. to get his column name
			{
				if (row[2].equals(colNameInUIGenByI))
					return row[5];
			}
				
			return "null";
		}
	}


  
 
}
