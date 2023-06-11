package com.app.adventurehub.user.domain.persistence;

import com.app.adventurehub.user.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    @Query("SELECT u.username FROM User u WHERE u.id = :id")
    String getUsernameById(Long id);
}