package coop.intergal.ui.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import coop.intergal.ui.security.data.CustomUser;
import coop.intergal.ui.security.data.entity.User;
import coop.intergal.ui.security.data.repositories.UserRepository;

/**
 * Implements the {@link UserDetailsService}.
 * 
 * This implementation searches for {@link User} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
//@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 *
	 * Recovers the {@link User} from the database using the e-mail address supplied
	 * in the login screen. If the user is found, returns a
	 * {@link org.springframework.security.core.userdetails.User}.
	 *
	 * @param username User's e-mail address
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailIgnoreCase(username);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String email = "admin@vaadin.com";//user.getEmail();
		String passwordHash = "$2a$10$YOjvyzjm.530f5XIs4Ce7.BWR.zH1wy5BV9LY6zRIbcTwA6GELcOO";//passwordEncoder.encode("admin");//=user.getPasswordHash();
	//	passwordHash = user.getPasswordHash();
		System.out.println("UserDetailsServiceImpl.loadUserByUsername() passwordEncoder <"+ passwordHash+ "> passwordEncoder.encode(\"admin\") " + passwordEncoder.encode("admin") );
		String role = "admin" ;// user.getRole();
		String filterMyData = "xxxxxxxx22222" ;//user.getfilterMyData();
		
		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
//			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(),
//					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
			return new CustomUser(email, passwordHash,
					Collections.singletonList(new SimpleGrantedAuthority(role)), filterMyData);

		}
	}
	public User loadUserInfoByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailIgnoreCase(username);
		return user;
	
	}
}