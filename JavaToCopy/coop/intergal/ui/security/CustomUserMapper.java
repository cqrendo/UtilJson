package coop.intergal.ui.security;

import java.util.Collection;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

public class CustomUserMapper extends LdapUserDetailsMapper {

	@Override
	public CustomUserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
	     
			UserDetails details = super.mapUserFromContext(ctx, username, authorities);
	        String[] changedValues = ctx.getStringAttributes("telephoneNumber");
	        /// Do something here, like map to your custom UserDetails object.
	        
	        CustomUserDetails customUserDetails = new CustomUserDetails();
	        customUserDetails.setTelephoneNumber(ctx.getStringAttribute("telephoneNumber"));
	        return customUserDetails;  

	}

//	 @Override
//	    public CustomUserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities){
//
//	        // set from userDetails 
//	        UserDetails details = super.mapUserFromContext(ctx, username, authorities);
//
//	        // set directly from ctx 
//	        CustomUserDetails customUserDetails = new CustomUserDetails();
//	        customUserDetails.setFirstName(ctx.getStringAttribute("givenName"));
//	        customUserDetails.setLastName(ctx.getStringAttribute("sn"));
//
//	        return customUserDetails;
//	    }

}
