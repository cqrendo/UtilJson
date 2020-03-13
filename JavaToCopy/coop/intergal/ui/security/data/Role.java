package coop.intergal.ui.security.data;

public class Role {
    public static final String GUEST = "guest";
    // This role implicitly allows access to all views.
    public static final String ADMIN = "admin";

    private Role() {
        // Static methods and fields only
    }

    public static String[] getAllRoles() {
        return new String[] { GUEST, ADMIN };
    }
}
