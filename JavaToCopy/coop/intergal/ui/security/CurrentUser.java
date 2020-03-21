package coop.intergal.ui.security;


import coop.intergal.ui.security.data.CustomUser;
import coop.intergal.ui.security.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
