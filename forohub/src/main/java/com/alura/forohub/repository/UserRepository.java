package com.alura.forohub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.alura.forohub.models.user.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM Usuario U WHERE u.email =:email")
    Optional<User> UserByEmail(String email);

    Optional<User> findByIdAndActivoTrue(Long id);
}
