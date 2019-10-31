package coop.intergal.ui.views;

import coop.intergal.ui.components.ConfirmDialog;

public interface HasConfirmation {

	void setConfirmDialog(ConfirmDialog confirmDialog);

	ConfirmDialog getConfirmDialog();
}
