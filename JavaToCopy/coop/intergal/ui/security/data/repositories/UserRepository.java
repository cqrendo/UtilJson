package coop.intergal.ui.security.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import coop.intergal.ui.security.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailIgnoreCase(String email);

}
