package coop.intergal.ui.utils;

import java.util.Objects;

import com.vaadin.flow.component.Component;

public class UiComponentsUtils {
	
	public static Component findComponent(Component component, String id) {
		String idComponent = "";
		if ( component.getId().isPresent())
			idComponent = component.getId().get();
		System.out.println("findComponent() id:" + id  + " component "+  component.toString() +  " idComponent " +  idComponent);
		if (component.getId().filter(id::equals).isPresent()) {
			return component;
		} else {
			return component.getChildren().map(child->findComponent(child, id)).filter(Objects::nonNull).findFirst().orElse(null);
			}	
		}
//	return (Component) component.getChildren().map(child->findComponent(child, id)).findFirst().orElse(null);


}
