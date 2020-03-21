package coop.intergal.ui.security;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.spring.annotation.SpringComponent;

import coop.intergal.ui.security.data.Role;
import coop.intergal.ui.security.data.entity.User;
import coop.intergal.ui.security.data.repositories.UserRepository;

@SpringComponent
public class DataGenerator implements HasLogger {

    private static final String[] FILLING = new String[]{"Strawberry", "Chocolate", "Blueberry", "Raspberry",
            "Vanilla"};
    private static final String[] TYPE = new String[]{"Cake", "Pastry", "Tart", "Muffin", "Biscuit", "Bread", "Bagel",
            "Bun", "Brownie", "Cookie", "Cracker", "Cheese Cake"};
    private static final String[] FIRST_NAME = new String[]{"Ori", "Amanda", "Octavia", "Laurel", "Lael", "Delilah",
            "Jason", "Skyler", "Arsenio", "Haley", "Lionel", "Sylvia", "Jessica", "Lester", "Ferdinand", "Elaine",
            "Griffin", "Kerry", "Dominique"};
    private static final String[] LAST_NAME = new String[]{"Carter", "Castro", "Rich", "Irwin", "Moore", "Hendricks",
            "Huber", "Patton", "Wilkinson", "Thornton", "Nunez", "Macias", "Gallegos", "Blevins", "Mejia", "Pickett",
            "Whitney", "Farmer", "Henry", "Chen", "Macias", "Rowland", "Pierce", "Cortez", "Noble", "Howard", "Nixon",
            "Mcbride", "Leblanc", "Russell", "Carver", "Benton", "Maldonado", "Lyons"};

    private final Random random = new Random(1L);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataGenerator(UserRepository userRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData() {
        if (userRepository.count() != 0L) {
            getLogger().info("Using existing database");
            return;
        }

        getLogger().info("Generating demo data");

        getLogger().info("... generating users");
        createGuest(userRepository, passwordEncoder);
        createAdmin(userRepository, passwordEncoder);
        userRepository.findAll().forEach(user -> System.out.println(String.format("%s, %s", user.getEmail(), user.getPasswordHash())));

        // A set of products that will be used for creating orders.
  
        getLogger().info("Generated demo data");
    }
 

 
    private User createGuest(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return userRepository.save(createUser("guest@vaadin.com", "Malin", "Castro",
                "guest", Role.GUEST, true));
    }

    private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return userRepository.save(createUser("admin@vaadin.com", "Göran", "Rich",
                "admin", Role.ADMIN, true));
    }

    private User createUser(String email, String firstName, String lastName, String password, String role,
                            boolean locked) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        user.setLocked(locked);
        user.setfilterMyData("xxxxx");
        return user;
    }
}
