package coop.intergal.ui.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Java wrapper of the polymer element `form-buttons-bar`
 */
@Tag("form-buttons-bar")
@JsModule("./src/components/form-buttons-bar.js")
public class FormButtonsBar extends PolymerTemplate<TemplateModel> {

	@Id("save")
	private Button save;
	@Id("cancel")
	private Button cancel;
	@Id("delete")
	private Button delete;
	@Id("add")
	private Button add;
	@Id("print")
	private Button print;
	@Id("customButtons")
	private Div customButtons;


	public Div getCustomButtons() {
		return customButtons;
	}

	public void setCustomButtons(Div customButtons) {
		this.customButtons = customButtons;
	}

	public void setSaveText(String saveText) {
		save.setText(saveText == null ? "" : saveText);
	}

	public void setCancelText(String cancelText) {
		cancel.setText(cancelText == null ? "" : cancelText);
	}

	public void setDeleteText(String deleteText) {
		delete.setText(deleteText == null ? "" : deleteText);
	}
	public void setAddVisible(boolean visible) {
		add.setVisible(visible);
	}

	public void setSaveVisible(boolean visible) {
		save.setVisible(visible);
	}

	public void setCancelVisible(boolean visible) {
		cancel.setVisible(visible);
	}

	public void setDeleteVisible(boolean visible) {
		delete.setVisible(visible);
	}
	
	public void setAddDisabled(boolean addDisabled) {
		add.setEnabled(!addDisabled);
	}

	public void setSaveDisabled(boolean saveDisabled) {
		save.setEnabled(!saveDisabled);
	}

	public void setCancelDisabled(boolean cancelDisabled) {
		cancel.setEnabled(!cancelDisabled);
	}

	public void setDeleteDisabled(boolean deleteDisabled) {
		delete.setEnabled(!deleteDisabled);
	}


	public static class SaveEvent extends ComponentEvent<FormButtonsBar> {
		public SaveEvent(FormButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
		return save.addClickListener(e -> listener.onComponentEvent(new SaveEvent(this, true)));
	}

	public static class CancelEvent extends ComponentEvent<FormButtonsBar> {
		public CancelEvent(FormButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
		return cancel.addClickListener(e -> listener.onComponentEvent(new CancelEvent(this, true)));
	}

	public static class AddEvent extends ComponentEvent<FormButtonsBar> {
		public AddEvent(FormButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addAddListener(ComponentEventListener<AddEvent> listener) {
		return add.addClickListener(e -> listener.onComponentEvent(new AddEvent(this, true)));
	}
	public static class DeleteEvent extends ComponentEvent<FormButtonsBar> {
		public DeleteEvent(FormButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
		return delete.addClickListener(e -> listener.onComponentEvent(new DeleteEvent(this, true)));
	}
	public static class PrintEvent extends ComponentEvent<FormButtonsBar> {
		public PrintEvent(FormButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addPrintListener(ComponentEventListener<PrintEvent> listener) {
		return print.addClickListener(e -> listener.onComponentEvent(new PrintEvent(this, true)));
	}
}