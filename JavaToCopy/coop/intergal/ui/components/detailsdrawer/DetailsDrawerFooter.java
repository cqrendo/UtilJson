package coop.intergal.ui.components.detailsdrawer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;

import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.util.LumoStyles;
import coop.intergal.ui.util.UIUtils;
import coop.intergal.ui.layout.size.Horizontal;
import coop.intergal.ui.layout.size.Right;
import coop.intergal.ui.layout.size.Vertical;

public class DetailsDrawerFooter extends FlexBoxLayout {

    private Button save;
    private Button cancel;

    public DetailsDrawerFooter() {
        setBackgroundColor(LumoStyles.Color.Contrast._5);
        setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
        setSpacing(Right.S);
        setWidthFull();

        save = UIUtils.createPrimaryButton("Save");
        cancel = UIUtils.createTertiaryButton("Cancel");
        add(save, cancel);
    }

    public Registration addSaveListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return save.addClickListener(listener);
    }

    public Registration addCancelListener(
            ComponentEventListener<ClickEvent<Button>> listener) {
        return cancel.addClickListener(listener);
    }

}
