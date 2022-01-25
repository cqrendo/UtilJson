package coop.intergal.ui.util;

import static coop.intergal.AppConst.PACKAGE_VIEWS;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.function.SerializableComparator;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.utils.ProcessParams;
import coop.intergal.ui.views.DynamicDisplayForAskData;
import coop.intergal.ui.views.DynamicGridForPick;
import coop.intergal.ui.views.DynamicQryGridDisplay;
import coop.intergal.ui.views.DynamicViewGrid;
import coop.intergal.ui.views.GeneratedUtil;
import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

public class GenericClassForMethods {
	
	private DynamicDBean rowOfAskData;
	private DynamicDBean keepFirstParent;
	private DynamicQryGridDisplay dQGD;
	private JsonNode actualRowStep;
	private DynamicViewGrid actualGrid;
	private DynamicDBean actualRowOfInputData;
	public Object processButtonForNavigation(String idButton, DynamicDBean bean, Div divSubGrid, DynamicViewGrid grid) {
		return processButtonForNavigation(idButton, bean, divSubGrid, grid, null);
	}
	public Object processButtonForNavigation(String idButton, DynamicDBean bean, Div divSubGrid, DynamicViewGrid grid, String filter2) {
//		String titleOption = rowSubMenu.get("optionName").asText();
		int idxIdMenu = idButton.indexOf("@IDM");
		boolean isPopup = idButton.indexOf("@POP") > -1;
		String filterInButton = "";
		int idxFIB = idButton.indexOf("@FILTER");
		if (idxFIB > -1) {
			filterInButton = idButton.substring(idxFIB+7);
			if (filterInButton.indexOf("@") > -1) { // to avoid other params
				filterInButton = filterInButton.substring(0,filterInButton.indexOf("@"));
			}
			
		}
		if (idxIdMenu == -1)
		{
			DataService.get().showError("Id de menu sin indicar, se debe de especificar en el formato nombreopcion#IDMnumero");
			return null; 
		}
		try {
		String idMenu = idButton.substring(idxIdMenu+4);
		if (idMenu.indexOf("@") > -1) { // to avoid other params
			idMenu = idMenu.substring(0,idMenu.indexOf("@"));
		}
		
		String filter = "idMenu="+idMenu;
		JsonNode rowsList = JSonClient.get("menu",filter,false,AppConst.PRE_CONF_PARAM_METADATA,1+"");
		for (JsonNode rowSubMenu : rowsList)  
			{	
	
			String urlBase = "../dymanic";
			String hostName = InetAddress.getLocalHost().getHostName() ;
			if (hostName.indexOf(".local") == -1) // to diferent when is running in local (Maven) or in remote (tys.war -> tomcat)
				urlBase= "../tys/dymanic";
			String optionName = rowSubMenu.get("optionName").asText();	
			String resource = rowSubMenu.get("resource").asText();
			String queryFormClassName = rowSubMenu.get("queryFormClassName").asText();
			String displayFormClassName = rowSubMenu.get("displayFormClassName").asText();
			String layoutClassName = rowSubMenu.get("layout").asText();
			String filterForPopup = rowSubMenu.get("filterForPopup").asText();
			if (queryFormClassName.startsWith("coop.intergal.ui.views") == false)
				queryFormClassName = PACKAGE_VIEWS+queryFormClassName;
			if (displayFormClassName.startsWith("coop.intergal.ui.views") == false)
				displayFormClassName = PACKAGE_VIEWS+displayFormClassName;
			if (filterInButton.length() > 0 && filterForPopup != null && filterForPopup.equals("null") == false && filterForPopup.length() > 0)
			{
				filterForPopup = filterForPopup + ";"+ filterInButton;
			}
			else if (filterInButton.length() > 0)
			{
				filterForPopup = filterInButton;
			}
			
			
//		titleOption = titleOption.replace(" ", "%20");
			if (isPopup)
			{
//				String filterForNavigation = "row.subgrid.CLAVEARTICULO=rowtarget.CLAVE_ARTICULO";
				if ( layoutClassName.indexOf("DynamicViewGrid") > -1 || layoutClassName.indexOf("DynamicQryGridDisplay") > -1)  // when is a grid list is send the origin row to filter the list in the target 
				{
					DynamicDBean sourceRow = getRowSelected(divSubGrid, grid, bean);
					showDialog(sourceRow, resource, layoutClassName, displayFormClassName, grid, filterForPopup, null );
				}
				else // // when is a Display is send the target row to be show as target
				{
				DynamicDBean beanToShow = getBeanToShow(bean, filterForPopup, resource, divSubGrid, grid);
				if (beanToShow != null )
					showDialog(beanToShow, resource, layoutClassName, displayFormClassName, grid, filterForPopup, null );
				}
			}	
			else // is new tab open, the filter must be made with " for values
				{
				if (filterInButton.length() > 0)
				{
					if (bean == null) // comes from a grid
					{
						bean = grid.getSelectedRow();
						if (bean == null)
						{
							DataService.get().showError("Debe seleccionar un registro");
							return null;
						}
					}
					filterInButton = ProcessParams.componFilterFromParams(filterInButton, bean);
//					filterInButton = filterInButton.replace("'","\"");
				}
					
				if((filter2 != null && filter2.length() > 0 && filter2 != null && filter2.equals("null") == false && filter2.length() > 0)
					&&
					(filterInButton != null && filterInButton.length() > 0 && filterInButton != null && filterInButton.equals("null") == false && filterInButton.length() > 0))
				{
					filter2 = filter2 + "%20AND%20"+ filterInButton;
				}
				else if (filterInButton != null && filterInButton.length() > 0)
				{
					filter2 = filterInButton;
				}
				if (filter2 != null)
				{
					String url = urlBase+"?resourceName="+resource+"&queryFormClassName="+queryFormClassName+"&displayFormClassName="+displayFormClassName+"&title="+optionName+"&filter="+filter2;
					UI.getCurrent().getPage().executeJs("window.open(\""+url+"\", \"_blank\");");
				}
				else
					UI.getCurrent().getPage().executeJs("window.open(\""+urlBase+"?resourceName="+resource+"&queryFormClassName="+queryFormClassName+"&displayFormClassName="+displayFormClassName+"&title="+optionName+"\", \"_blank\");");
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	private DynamicDBean getBeanToShow(DynamicDBean bean2, String filterForNavigation, String resource, Div divSubGrid, DynamicViewGrid grid) {
		if (filterForNavigation.startsWith("row."))
		{				
			String filter = componFilterFromSubgridRowSelected(filterForNavigation, divSubGrid, grid, bean2);
			if (filter != null)
				{
				DynamicDBean rowtoShow = RestData.getOneRow(resource, filter, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);					
				return rowtoShow;
				}
		}
		return null;
	}

	private String componFilterFromSubgridRowSelected(String filterForNavigation, Div divSubGrid, DynamicViewGrid grid, DynamicDBean bean2) { // @@ TODO prepare for multi filter
	
		return ProcessParams.componFilterFromParams(filterForNavigation,getRowSelected(divSubGrid, grid, bean2));

		
		
	}

	private DynamicDBean getRowSelected(Div divSubGrid, DynamicViewGrid dvGrid, DynamicDBean bean2) {
		if (bean2 != null) {
			return bean2;
		}
		if (dvGrid == null)
			dvGrid = (DynamicViewGrid) divSubGrid.getChildren().findFirst().get().getChildren().findFirst().get().getChildren().findFirst().get();
		Set<DynamicDBean> seletedItems = dvGrid.getGrid().getSelectedItems();
		if (seletedItems.isEmpty())
		{
			DataService.get().showError("Debe seleccionar un registro");
			return null;
		}
		return seletedItems.iterator().next();
	}
	private DynamicDBean showDialog(DynamicDBean bean2, String resource2, String layoutClassName, String displayFormClassName, DynamicViewGrid grid, String filterForPopup, DynamicDisplayForAskData dynamicDisplayForAskData) { 
		
		DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
		dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName(resource2);
//		dataProvider.setFilter(getFilter());

		
		grid.showBeaninPopup(bean2, resource2,layoutClassName, displayFormClassName, null, filterForPopup, dynamicDisplayForAskData );

//			dialogForPick = new Dialog();
//		dialogForPick.removeAll();
//		dialogForPick.setCloseOnOutsideClick(false);
//		dialogForPick.add(new Label(bean.getCol0()));
//		dialogForPick.open();
		
		
		
		return null;
	}

	public Object processButtonForProcess(String idButton, DynamicDBean bean, Div divSubGrid, DynamicViewGrid grid)
	{
		int idxIdProcess = idButton.indexOf("@IDP");
		if (idxIdProcess == -1)
		{
			DataService.get().showError("Id de proceso sin indicar, se debe de especificar en el formato nombreopcion#IDPnumero");
			return null; 
		}
		try {
			String idMProcess = idButton.substring(idxIdProcess+4);
			String filter = "idProcess="+idMProcess;
			JsonNode rowsList;
			rowsList = JSonClient.get("CR-Process",filter,false,AppConst.PRE_CONF_PARAM_METADATA,1+"");
			JsonNode rowProcess = rowsList.get(0);  
			System.out.println("GenericClassForMethods.processButtonForProcess() "+ rowProcess.get("name").asText());
			JsonNode rowsSteps = rowProcess.get("List-ProcessStep");
			JsonNode FirstStep = rowsSteps.get(0);
			processSteps(FirstStep, grid, bean); 
			
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		return null; 
	}
	public Object proccesButtonForContinueProcess(String idButton, DynamicDBean bean, Div divSubGrid, DynamicViewGrid grid)
	{
		int idxStart = idButton.indexOf("@CPOption")+9;
		String idNextStep = null;
		if (idxStart > 8 )
			idNextStep = idButton.substring(idxStart);
		nextStepIfExist(actualRowStep, actualGrid, actualRowOfInputData, idNextStep);
		return null;
	}
	private void processSteps(JsonNode rowStep, DynamicViewGrid grid, DynamicDBean bean) {//(JsonNode rowStep, DynamicViewGrid grid, DynamicDBean rowPreviousStep) {
		System.out.println("GenericClassForMethods.processButtonForProcess() STEP "+ rowStep.get("name").asText());	
		String inputResourceForReadData = rowStep.get("inputResourceForReadData").asText().trim();
		String inputResourceForAskData = rowStep.get("inputResourceForAskData").asText().trim();
		DynamicDBean rowOfInputData = bean;
		String dialogClassLayout = rowStep.get("dialogClassLayout").asText();
		String dialogClassDisplayForm = rowStep.get("dialogClassDisplayForm").asText();
		String filterForShowResult = rowStep.get("filterForShowResult").asText();
		String idButtonForShowResult = rowStep.get("idButtonForShowResult").asText(); // format @POP (if popup) + @IDM<<idmenu>>
		String idMenuOpenUIToProcess = rowStep.get("idMenuOpenUIToProcess").asText();
		if (idMenuOpenUIToProcess != null && idMenuOpenUIToProcess.equals("null") == false )
		{
			dQGD = (DynamicQryGridDisplay) getOpenUI(idMenuOpenUIToProcess);  // @@TODO for now all the option are of the type DynamicQryGridDisplay, consider to add more types
			if (dQGD ==  null) 
			{
				DataService.get().showError("Formulario para recibir dato sin abrir");
			}
			else
			{
				System.out.println("GenericClassForMethods.processSteps() UI found!" + dQGD.getPageTitle());
				nextStepIfExist(rowStep, grid, rowOfInputData,null);
			}
				
		}
		else if (inputResourceForAskData != null && inputResourceForAskData.equals("null") == false && inputResourceForAskData.isEmpty() == false  )
		{
//			rowOfInputData = askInputData(inputResourceForAskData, grid, dialogClassLayout, dialogClassDisplayForm);
			askForDataAndContinue(dialogClassLayout, inputResourceForAskData, grid, dialogClassDisplayForm, rowStep, rowOfInputData);
		}
		else
		{	
			if (filterForShowResult != null && filterForShowResult.equals("null") == false && filterForShowResult.isEmpty() == false)
			{
				showResult(filterForShowResult,idButtonForShowResult);
			}
			else
			{
				proccesDataInputOuput(rowStep, grid, dialogClassDisplayForm, rowOfInputData);//(rowPreviousStep,rowStep, grid, dialogClassDisplayForm, rowOfInputData);(rowPreviousStep,rowStep, grid, dialogClassDisplayForm, rowOfInputData);
				nextStepIfExist(rowStep, grid, rowOfInputData, null);
		}
		}
//		JsonNode rowsStepOuput = rowStep.get("List-ProcessStepOutput");
//		processInputOuput(grid, inputResourceForReadData, rowOfInputData, rowsStepOuput);
//		for (JsonNode rowStepOuput : rowsStepOuput) 
//			{
//			System.out.println("GenericClassForMethods.processButtonForProcess() STEP OUTPUT "+ rowStepOuput.get("resource").asText());
//			}
		}

		
	private DynamicQryGridDisplay getOpenUI(String idMenuOpenUIToProcess) {
		// TODO Auto-generated method stub
		return (DynamicQryGridDisplay) UtilSessionData.getOpenUI(idMenuOpenUIToProcess);
	}
	private void showResult(String filterForShowResult, String idButton) {
		String filter = componeFilter(filterForShowResult);
		processButtonForNavigation(idButton, null, null, null, filter);
//		menuRow = 
		
	}
	private String componeFilter(String filterForShowResult) { // sintasis -> field => <<FieldName1WithValue>> and .....<<FieldName2WithValue>> 
		String resultFilter = filterForShowResult;
		while (true)
		{
			int idxStartField = filterForShowResult.indexOf("<<");
			int inxEndField = filterForShowResult.indexOf(">>");
			if (idxStartField ==-1)
				break;
			String fieldToGetValue = filterForShowResult.substring(idxStartField+2,inxEndField);
			if (keepFirstParent == null)
				{
				DataService.get().showError("ERROR -> NO se ha creado ninún registro");
				break;
				}
			else
			{
				String value = keepFirstParent.getRowJSon().get(fieldToGetValue).asText();
				resultFilter = resultFilter.replace("<<"+fieldToGetValue+">>",value);
				filterForShowResult = filterForShowResult.substring(inxEndField+2);
			}	
		}
		return resultFilter;
	}
	private void proccesDataInputOuput(JsonNode rowStep, DynamicViewGrid grid, String inputResourceForReadData, DynamicDBean rowOfInputData) {//(DynamicDBean rowPreviousStep, JsonNode rowStep, DynamicViewGrid grid, String inputResourceForReadData, DynamicDBean rowOfInputData) {
		Hashtable<Integer, DynamicDBean> beansFromGrid = new Hashtable<Integer, DynamicDBean>();
		Integer id=0;
		if (rowStep.get("groupBy") != null && rowStep.get("groupBy").asText().equals("row") )  // is only processed one row
		{
			beansFromGrid.put(id,rowOfInputData);
		}
		else
		{
			Stream<DynamicDBean> rowsStream = ((DataProvider<DynamicDBean, String>) grid.getGrid().getDataProvider()).fetch(createQuery(grid.getGrid()));
//		grid.getGrid().getSelectedItems();j
			Iterator<DynamicDBean> rowsInput = rowsStream.iterator();
			while (rowsInput.hasNext()) {
				DynamicDBean rowInput = rowsInput.next();
				if (rowInput.getRowJSon().get("NotProcessRow") == null)
				{
					beansFromGrid.put(id,rowInput);
					id++;
					System.out.println("GenericClassForMethods.proccesDataInputOuput() SI PROCESAR "+ rowInput.getRowJSon().get("cantidadPedirFinal"));
				}
				else
				{
					System.out.println("GenericClassForMethods.proccesDataInputOuput() NO PROCESAR "+ rowInput.getRowJSon().get("cantidadPedirFinal"));
				}
			
		}
		}
		DynamicDBean row = null;
		DynamicDBean lastParent = null;
		String groupBy = rowStep.get("groupBy").asText();
		if (groupBy.equals("row") == false)  // when groupBy is "row" then only one row is processed
			{
			String keepGroup = null;
			int i = 0;
			DynamicDBean firstRowInput = null ;
			while (beansFromGrid.size() > i) {

				if (i == 0) // first row
				{
					firstRowInput = beansFromGrid.get(0);
					lastParent = insertOrUpdateOutput(firstRowInput, rowStep, true, lastParent);//(firstRowInput, rowStep, true, lastParent, rowPreviousStep);
					keepFirstParent = lastParent;
					if (groupBy.equals("null") == false && groupBy.equals("row") == false)
						if (firstRowInput.getRowJSon() != null && firstRowInput.getRowJSon().get(groupBy) != null )
							keepGroup = firstRowInput.getRowJSon().get(groupBy).asText();				
				}
				else
				{
					row = beansFromGrid.get(i);
					if (row != null)
					{
//	!!!!!			System.out.println("GenericClassForMethods.proccesDataInputOuput() ROW INPUT --->"+ row.getRowJSon().get("DESCRIPCION").asText());
					if (keepGroup != null && row.getRowJSon().get(groupBy).asText().equals(keepGroup) == false) // Group change
						{
						lastParent = insertOrUpdateOutput(row, rowStep, true, lastParent);
						keepGroup = row.getRowJSon().get(groupBy).asText();
						}
					else
						{
						insertOrUpdateOutput(row, rowStep, false, lastParent);//(row, rowStep, false, lastParent, rowOfInputData);
						}
					}
				}	
				i++;
//			System.out.println("GenericClassForMethods.processInput() EACH Row in grid " + row.getCol0() + " "+  row.getRowJSon().asText() );
			}
		}
		else  // only one row is processed
		{
			DynamicDBean RowInput = beansFromGrid.get(0);
			lastParent = insertOrUpdateOutput(RowInput, rowStep, true, lastParent);//(firstRowInput, rowStep, true, lastParent, rowPreviousStep);
	
		}
//		JsonNode rowsStepOuput = rowStep.get("List-ProcessStepOutput");
//		processInputOuput(grid, inputResourceForReadData, rowOfInputData, rowsStepOuput);
//
//		if (rowPreviousStep != null)
//		{
//			System.out.println("GenericClassForMethods.proccesDataInputOuput() rowPreviousStep.getCol0()=" + rowPreviousStep.getCol0());
//		}
//		for (JsonNode rowStepOuput : rowsStepOuput) 
//			{
//			System.out.println("GenericClassForMethods.processButtonForProcess() STEP OUTPUT "+ rowStepOuput.get("resource").asText());
//			}
//		return keepFirstParent;
	}
	private DynamicDBean insertOrUpdateOutput(DynamicDBean rowInputData, JsonNode rowStep, boolean isNewForGroupChange, DynamicDBean lastParent) {//(DynamicDBean rowInputData, JsonNode rowStep, boolean isNewForGroupChange, DynamicDBean lastParent, DynamicDBean rowPreviousStep) {
//		if (rowStep.get("filter"))
		JsonNode rowsStepOuput = rowStep.get("List-ProcessStepOutput");
		for (JsonNode rowStepOuput : rowsStepOuput) 
		{ //
			JsonNode ouputResource = rowStepOuput.get("resource"); 
			if (ouputResource != null && ouputResource.asText().indexOf("@form@") > -1) // the output is in a resource of a form 
			{
				System.out.println("GenericClassForMethods.insertOrUpdateOutput() + TITULO  form a procesar: " + dQGD.getPageTitle());
				String ouputResourceStr = ouputResource.asText().substring(6);
				DynamicViewGrid subgridToAddItem = dQGD.getDvgIntheForm().get(ouputResourceStr);
				if ( subgridToAddItem == null)
				{
					DataService.get().showError("ERROR -> Listado para insertar sin abrir, el registro donde se quiere añadir la linea ha de estar abierto");
					return null;
				}
			//	subgridToAddItem.setRowIsInserted(insertAnOuputRow(rowStepOuput, lastParent, rowInputData));
				DynamicDBean newItem = insertAnOuputRowInAForm(rowStepOuput, rowInputData);	
				newItem.setFilter(subgridToAddItem.getFilter());
				subgridToAddItem.insertBeanInList(); // prepare a new item
				subgridToAddItem.setRowIsInserted(newItem); // fills data
				subgridToAddItem.insertBeanInList(); // saves the item
			}
			else if (rowStepOuput.get("childOf").asText().equals("null")) // is root ouput row
			{
				String filter = rowStepOuput.get("filter").asText();
				if (rowStepOuput.get("filter").asText().equals("null") && isNewForGroupChange) // is new row for groupBy change in root 
				{
					lastParent = //insertAnOuputRow(rowStepOuput, null, rowPreviousStep);
								 insertAnOuputRow(rowStepOuput, null, rowInputData);//(DynamicDBean rowInputData, JsonNode rowStep, boolean isNewForGroupChange, DynamicDBean lastParent, DynamicDBean rowPreviousStep) {
				}
//				else if (filter.equals("null") == false && filter.indexOf("rowAD.") > -1 ) // if the rowAD.field has data it means that the a row is not created
//				{
//					System.out.println("PROCEESS AN UPDATE or INSERT for " +rowStepOuput.get("resource"));
//					lastParent = checkIfParentExistAndHaveSameData(rowStepOuput);
//				}
				else if (isNewForGroupChange) // is a new row by a group change but is not a insert it updates the ouput
				{
					System.out.println("PROCEESS AN UPDATE for " +rowStepOuput.get("resource"));
				}
			}
			else // is a ChildRow
			{
	//			insertAnOuputRow(rowStepOuput, lastParent, rowPreviousStep);
				insertAnOuputRow(rowStepOuput, lastParent, rowInputData);//(rowStepOuput, lastParent, rowInputData, rowPreviousStep);
			}
				
//			System.out.println("GenericClassForMethods.processButtonForProcess() STEP OUTPUT "+ rowStepOuput.get("resource").asText());
		}
		
		return lastParent;

		
	}
	/// instead of ask for a key, we do with 2 buttons combined with a form open where the childs will be add, 
//	private DynamicDBean checkIfParentExistAndHaveSameData(JsonNode rowStepOuput) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	private DynamicDBean insertAnOuputRow(JsonNode rowStepOuput, DynamicDBean lastParent, DynamicDBean rowInputData) {//(JsonNode rowStepOuput, DynamicDBean lastParent, DynamicDBean rowInputData, DynamicDBean rowPreviousStep) {
		String ouputResource = rowStepOuput.get("resource").asText();
		System.out.println("PROCEESS AN INSERT for " +ouputResource);
		DynamicDBean newBean = new DynamicDBean(); 
		DdbDataBackEndProvider dataProviderOuput = new DdbDataBackEndProvider();
		dataProviderOuput.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProviderOuput.setResourceName(ouputResource);
		
		newBean.setResourceName(rowStepOuput.get("resource").asText());
		newBean.setRowsColList(dataProviderOuput.getRowsColList());
		newBean.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		GeneratedUtil.fillDefaultValues(newBean);
		newBean = fillDataOuput(newBean, rowStepOuput, rowInputData);//(newBean, rowStepOuput, rowInputData, rowPreviousStep);
		Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param
		beansToSaveAndRefresh.clear();
		beansToSaveAndRefresh.put(newBean.getResourceName(), newBean);
		if (lastParent != null)
		{
			beansToSaveAndRefresh.put(lastParent.getResourceName(), lastParent);
	//		if (resourceSubGrid2.indexOf(".")> -1)
			newBean.setFilter(DataService.get().componFKFilter(lastParent, ouputResource));

		}

		dataProviderOuput.save(newBean.getResourceName(), beansToSaveAndRefresh);	

		return newBean;

		
	}
	private DynamicDBean insertAnOuputRowInAForm(JsonNode rowStepOuput, DynamicDBean rowInputData) {//(JsonNode rowStepOuput, DynamicDBean lastParent, DynamicDBean rowInputData, DynamicDBean rowPreviousStep) {
		String ouputResource = rowStepOuput.get("resource").asText().substring(6); // to clean @form@
		System.out.println("PROCEESS AN INSERT for " +ouputResource);
		DynamicDBean newBean = new DynamicDBean(); 
		DdbDataBackEndProvider dataProviderOuput = new DdbDataBackEndProvider();
		dataProviderOuput.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProviderOuput.setResourceName(ouputResource);
		
		newBean.setResourceName(ouputResource);
		newBean.setRowsColList(dataProviderOuput.getRowsColList());
		newBean.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		GeneratedUtil.fillDefaultValues(newBean);
		newBean = fillDataOuput(newBean, rowStepOuput, rowInputData);//(newBean, rowStepOuput, rowInputData, rowPreviousStep);
		Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param
		beansToSaveAndRefresh.clear();
		beansToSaveAndRefresh.put(newBean.getResourceName(), newBean);
//		if (lastParent != null)
//		{
//			beansToSaveAndRefresh.put(lastParent.getResourceName(), lastParent);
//			newBean.setFilter(DataService.get().componFKFilter(lastParent, ouputResource));
//
//		}

//		dataProviderOuput.save(newBean.getResourceName(), beansToSaveAndRefresh);	

		return newBean;

		
	}
	private DynamicDBean fillDataOuput(DynamicDBean newBean, JsonNode rowStepOuput, DynamicDBean rowInputData) {//fillDataOuput(DynamicDBean newBean, JsonNode rowStepOuput, DynamicDBean rowInputData, DynamicDBean rowPreviousStep) {
		JsonNode rowsStepOuputMap = rowStepOuput.get("List-ProcessStepOutputMap");
		ArrayList<String[]> colList = newBean.getRowsColList();
		for (JsonNode rowStepOuputMap : rowsStepOuputMap) 
		{
			newBean.setCol(getValueForField(rowStepOuputMap.get("fieldValue").asText(), rowInputData), getColNameInUi(rowStepOuputMap.get("ouputField").asText(), colList, rowStepOuput.get("resource").asText() ));
		}
		return newBean;
	//	Orvisa@.03
		
	}
	private String getColNameInUi(String colName, ArrayList<String[]> colList, String resource) {
//		colName
		int idxCol = colList.indexOf(colName);
		String[] col = findCol(colList, colName) ;
		if (col == null)
		{
			DataService.get().showError("ERROR -> Campo sin definir en metaconfig : " +colName + " del recurso "+ resource);
			return null;
		}
		System.out.println("GenericClassForMethods.getColNameInUi() colName "+ colName +  " col[2] " +col[2] );
		return col[2];
	}
	private String[] findCol(ArrayList<String[]> colList, String colName) {
		for (int i = 0; i < colList.size(); i++) {
			
		if (colList.get(i)[0].equals(colName))
			{
			return colList.get(i);
			}
			
		}
		return null;
	}
	private String getValueForField(String valueFormula, DynamicDBean rowInputData) {//(String valueFormula, DynamicDBean rowInputData, DynamicDBean rowPreviousStep) {
		if (valueFormula.indexOf("rowID") > -1)
		{
			int idxStart = valueFormula.indexOf("rowID") + 6;
			String colNameToGetValue = valueFormula.substring(idxStart);
			String colNameInUi = getColNameInUi(colNameToGetValue, rowInputData.getRowsColList(),rowInputData.getResourceName());
			System.out.println("rowID GenericClassForMethods.getValueForField() valueFormula "+ valueFormula + " colNameInUi " + colNameInUi + " value "+ rowInputData.getCol(colNameInUi));
			return rowInputData.getCol(colNameInUi);
		}
		if (valueFormula.indexOf("rowAD") > -1)
		{
			int idxStart = valueFormula.indexOf("rowAD") + 6;
			String colNameToGetValue = valueFormula.substring(idxStart);
			/// AQUI /////
			String colNameInUi = getColNameInUi(colNameToGetValue, rowOfAskData.getRowsColList(), rowInputData.getResourceName());
			System.out.println("rowAD GenericClassForMethods.getValueForField() valueFormula "+ valueFormula + " colNameInUi " + colNameInUi + " value "+ rowInputData.getCol(colNameInUi));
			return rowOfAskData.getCol(colNameInUi);
		}
		return null;
	}
	private void askForDataAndContinue(String dialogClassLayout, String inputResourceForAskData, DynamicViewGrid grid, String dialogClassDisplayForm, JsonNode rowStep, DynamicDBean rowOfInputData) {
		DynamicDisplayForAskData dynamicDisplayForAskData = new DynamicDisplayForAskData();
		
		rowOfAskData = RestData.getOneRow(inputResourceForAskData, null, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		String defaultData = rowStep.get("mapDefaultInputDataForAskData").asText();
		if (defaultData.isEmpty() == false && defaultData.equals("null") == false)
		{
			StringTokenizer tokens = new StringTokenizer(defaultData,"#");  // format coln1;Fielname1#coln2;Fielname2
			while (tokens.hasMoreElements())
			{
				String eachFieldMap = tokens.nextToken();
				int idxSeparator = eachFieldMap.indexOf(";");
				String colnameOuput = eachFieldMap.substring(0, idxSeparator);
				String colnameInput = eachFieldMap.substring(idxSeparator+1);
				rowOfAskData.setCol(rowOfInputData.getRowJSon().get(colnameInput).asText(), colnameOuput);						
			}
			
		}
		this.actualRowStep = rowStep;
		this.actualGrid = grid;
		this.actualRowOfInputData = rowOfInputData;
		dynamicDisplayForAskData.setClassForMethod(this);
// 		dynamicDisplayForAskData.addAcceptDataAndContinueListener(e -> nextStepIfExist(rowStep, grid, rowOfInputData, null));//(rowOfAskData, rowStep, grid)); // is not use a generic button, all the buttons are paint in generated form
		//Object rowOfInputData = 
		askInputData(inputResourceForAskData, grid, dialogClassLayout, dialogClassDisplayForm, dynamicDisplayForAskData, rowOfAskData);

	}


	
	private Object nextStepIfExist(JsonNode step, DynamicViewGrid grid, DynamicDBean rowOfInputData, String idNextStep) {//(DynamicDBean rowtoShow, JsonNode step, DynamicViewGrid grid) {
//		System.out.println("GenericClassForMethods.nextStepAfterAskData() " + rowOfAskData.getCol0());
		JsonNode nextStep = foundNextStep(step, idNextStep);
		if (nextStep != null)
			processSteps(nextStep.get(0), grid, rowOfInputData);
		return null;
	}
	private JsonNode foundNextStep(JsonNode step, String idNextStep) {
		int idProcces = step.get("idProcess").asInt();
		int sequence = step.get("sequence").asInt()+1;
		String filter = "idProcess="+idProcces+"%20AND%20sequence="+sequence;
		if (idNextStep !=null && idNextStep.length() > 0)
			filter = "idProcess="+idProcces+"%20AND%20idNextStep='"+idNextStep+"'";
		try {
			JsonNode rowsList = JSonClient.get("CR-Process.List-ProcessStep",filter,false,AppConst.PRE_CONF_PARAM_METADATA,100+"");  // 100 maximun number of rows in child nodes, ouputs, ouputMap
			if (rowsList.size() > 0)
				return rowsList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}
	private void processInputOuput(DynamicViewGrid grid, String inputResourceForReadData, DynamicDBean rowOfInputData, JsonNode rowsStepOuput) {
		Stream<DynamicDBean> rowsStream = ((DataProvider<DynamicDBean, String>) grid.getGrid().getDataProvider()).fetch(createQuery(grid.getGrid()));
//		grid.getGrid().getSelectedItems();j
		Iterator<DynamicDBean> rows = rowsStream.iterator();
		DynamicDBean row = null;
		while (rows.hasNext()) {
			row = rows.next();
			System.out.println("GenericClassForMethods.processInput() EACH Row in grid " + row.getCol0() + " "+  row.getRowJSon().asText() );
		}
			
		
		
	}
	private DynamicDBean askInputData(String inputResourceForAskData, DynamicViewGrid grid, String layoutClassName, String displayFormClassName, DynamicDisplayForAskData dynamicDisplayForAskData, DynamicDBean rowtoShow) {
//		DynamicDBean rowtoShow = RestData.getOneRow(inputResourceForAskData, null, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
//		String layoutClassName="coop.intergal.ui.views.DynamicDisplaySubgrid"; 
//		String displayFormClassName = "coop.intergal.ui.views.GeneratedDetails";  
		String filterForPopup = null ;
		rowtoShow= showDialog(rowtoShow, inputResourceForAskData, layoutClassName, displayFormClassName, grid, filterForPopup, dynamicDisplayForAskData);
		return rowtoShow;
		
	}

    /*
     * This method is needed if using Vaadin 14, which does not have DataView API yet
     */
    private Query<DynamicDBean, String> createQuery(Grid<DynamicDBean> grid) {
        List<GridSortOrder<DynamicDBean>> gridSort = grid.getSortOrder();
        List<QuerySortOrder> sortOrder = gridSort
            .stream()
            .map(order -> order.getSorted().getSortOrder(order.getDirection()))
            .flatMap(orders -> orders)
            .collect(Collectors.toList());

        BinaryOperator<SerializableComparator<DynamicDBean>> operator = (comparator1, comparator2) -> {
            return comparator1.thenComparing(comparator2)::compare;
        };
        SerializableComparator<DynamicDBean> inMemorySorter = gridSort
            .stream()
            .map(order -> order.getSorted().getComparator(order.getDirection()))
            .reduce(operator)
            .orElse(null);
// @@ TODO handle The Integer.MAX_VALUE to avoid large numer of rows to procces 
        return new Query<DynamicDBean, String>(0, Integer.MAX_VALUE, sortOrder, inMemorySorter, null);
    }
}
