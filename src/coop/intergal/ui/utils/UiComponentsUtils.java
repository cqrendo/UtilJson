package coop.intergal.ui.utils;

import java.util.Objects;

import com.vaadin.flow.component.Component;

public class UiComponentsUtils {
	
	public static Component findComponent(Component component, String id) {
//		final id = "col12";
		System.out.println("GenericDynamicQuery.findComponent() id:" + id );
		if (component.getId().filter(id::equals).isPresent()) {
			return component;
		} else {
//			return (Component) component.getChildren().map(child->findComponent(child, id)).findFirst().orElse(null);
			return component.getChildren().map(child->findComponent(child, id)).filter(Objects::nonNull).findFirst().orElse(null);
			}	
		}


}
