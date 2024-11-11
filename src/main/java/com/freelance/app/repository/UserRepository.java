package com.freelance.app.repository;

import com.freelance.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Метод за намиране на потребител по имейл
    Optional<User> findByEmail(String email);
}