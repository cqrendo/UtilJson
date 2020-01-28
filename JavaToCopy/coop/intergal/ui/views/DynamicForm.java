package coop.intergal.ui.views;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

//@Tag("dynamic-form")
@JsModule("./src/views/admin/products/dynamic-form.js")
@Tag("dynamic-form")
//@JsModule("src/views/ComprasYVentas/Compras/pedido-proveedor-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = "display")
//public class DynamicForm extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, CrudForm<DynamicDBean> {
public class DynamicForm extends GenericDynamicForm implements BeforeEnterObserver{ //, CrudForm<DynamicDBean> {
	//public class DynamicForm extends	Component implements BeforeEnterObserver, CrudForm<DynamicDBean> {
	@Id("title")
	private H3 title;

	@Id("form")
	private FormLayout form;
	
	@Id("buttons")
	private FormButtonsBar buttons;

	@Id("col0")
	private TextField col0;

	@Id("col1")
	private TextField col1;

	@Id("col2")
	private TextField col2;

	private Binder<DynamicDBean> binder;

	private DynamicDBean bean;

	private ArrayList<String[]> rowsColList;

//	@Override
	public void setBinder(Binder<DynamicDBean> binder) { // @@ TODO revisar si se usa, si carga bien el nombre de la columna
		this.binder = binder;
		Iterator<String[]> itRowsColList = rowsColList.iterator();
		if (form == null)
			form = new FormLayout();
		form.removeAll();
		int i = 0;
		while (itRowsColList.hasNext())
		{
			String[] col = itRowsColList.next();
//			TextField tf = new TextField(itRowsColList.next()[0]);			
//			binder.bind(tf, "col"+i);
			TextField tf = new TextField(col[0]);
			binder.bind(tf, col[2]); //col[2] = fieldNameinUI
			form.add(tf);
			i++;
			
		}
//		binder.forField(price).withConverter(new PriceConverter()).bind("price");
//		binder.bind(col1, "col1");
//		binder.bind(col2, "col2");
//		col1.setPattern("\\d+(\\.\\d?\\d?)?$");
//		col1.setPreventInvalidInput(true);

//		String currencySymbol = Currency.getInstance(AppConst.APP_LOCALE).getSymbol();
//		col1.setPrefixComponent(new Span(currencySymbol));
	}

//	@Override
//	public FormButtonsBar getButtons() {
//		return buttons;
//	}

//	@Override
//	public HasText getTitle() {
//		return title;
//		
//	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
//		UI.getCurrent().getPage().addHtmlImport("frontend://src/views/admin/products/dynamic-form.html");
		if (bean != null)
			binder.setBean(bean);
	}

	public DynamicDBean getBean() {
		return bean;
	}

	public void setBean(DynamicDBean bean) {
		this.bean = bean;
		binder.setBean(bean);
	}

	public void setRowsColList(ArrayList<String[]> rowsColList) {
		this.rowsColList = rowsColList;
		
	}
}
