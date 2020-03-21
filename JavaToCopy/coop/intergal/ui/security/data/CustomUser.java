package coop.intergal.ui.security.data;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

   private static final long serialVersionUID = -3531439484732724601L;

    private String role;
   private String passwordHash;
   private String email;
   private String filterMyData;

 
    public CustomUser(String username, String password,
       Collection authorities, String filterMyData
       ) {

           super(username, password, true, true,
        		   true, true, authorities);

           this.filterMyData = filterMyData;
   }

    public CustomUser(String username, String password, boolean enabled,
    	       boolean accountNonExpired, boolean credentialsNonExpired,
    	       boolean accountNonLocked,
    	       Collection authorities
    	       ) {

    	           super(username, password, enabled, accountNonExpired,
    	              credentialsNonExpired, accountNonLocked, authorities);

    	           this.filterMyData = filterMyData;
    	   }

   public static long getSerialversionuid() {
      return serialVersionUID;
   }


public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public String getPasswordHash() {
	return passwordHash;
}

public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
public String getFilterMyData() {
	return filterMyData;
}
public void setFilterMyData(String filterMyData) {
	this.filterMyData = filterMyData;
}
}