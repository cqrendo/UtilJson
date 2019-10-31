package coop.intergal.ui.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Java wrapper of the polymer element `form-buttons-bar`
 */
@Tag("query-buttons-bar")
@JsModule("./src/components/query-buttons-bar.js")
public class QueryButtonsBar extends PolymerTemplate<TemplateModel> {


	@Id("bSearch")
	private Button bSearch;
	@Id("bCleanSearch")
	private Button bCleanSearch;

//	public void setSaveText(String saveText) {
//		save.setText(saveText == null ? "" : saveText);
//	}
//
//	public void setCancelText(String cancelText) {
//		cancel.setText(cancelText == null ? "" : cancelText);
//	}
//
//	public void setDeleteText(String deleteText) {
//		delete.setText(deleteText == null ? "" : deleteText);
//	}
//
//	public void setSaveDisabled(boolean saveDisabled) {
//		save.setEnabled(!saveDisabled);
//	}
//
//	public void setCancelDisabled(boolean cancelDisabled) {
//		cancel.setEnabled(!cancelDisabled);
//	}
//
//	public void setDeleteDisabled(boolean deleteDisabled) {
//		delete.setEnabled(!deleteDisabled);
//	}

	public static class SearchEvent extends ComponentEvent<QueryButtonsBar> {
		public SearchEvent(QueryButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}
	public static class ClearSearchEvent extends ComponentEvent<QueryButtonsBar> {
		public ClearSearchEvent(QueryButtonsBar source, boolean fromClient) {
			super(source, fromClient);
		}
	}

	public Registration addSearchListener(ComponentEventListener<SearchEvent> listener) {
	return bSearch.addClickListener(e -> listener.onComponentEvent(new SearchEvent(this, true)));
}

	public Registration addClearSearchListener(ComponentEventListener<ClearSearchEvent> listener) {
	return bCleanSearch.addClickListener(e -> listener.onComponentEvent(new ClearSearchEvent(this, true)));
}
//	public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
//		return save.addClickListener(e -> listener.onComponentEvent(new SaveEvent(this, true)));
//	}
//
//	public static class CancelEvent extends ComponentEvent<QueryButtonsBar> {
//		public CancelEvent(QueryButtonsBar source, boolean fromClient) {
//			super(source, fromClient);
//		}
//	}
//
//	public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
//		return cancel.addClickListener(e -> listener.onComponentEvent(new CancelEvent(this, true)));
//	}
//
//	public static class DeleteEvent extends ComponentEvent<QueryButtonsBar> {
//		public DeleteEvent(QueryButtonsBar source, boolean fromClient) {
//			super(source, fromClient);
//		}
//	}
//
//	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
//		return delete.addClickListener(e -> listener.onComponentEvent(new DeleteEvent(this, true)));
//	}
}