package io.github.peterberghuis.springmysqleskafka.repository;

import io.github.peterberghuis.springmysqleskafka.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
