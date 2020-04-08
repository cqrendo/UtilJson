package coop.intergal.ui.security;

import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

public class CustomUserDetails extends LdapUserDetailsImpl {

    private String telephoneNumber;

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }
}