package com.sesasis.donusum.yok.core.security.repository;

import com.sesasis.donusum.yok.core.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);


  Boolean existsByEmail(String email);
  List<User> findAllByOrderByIdDesc();
}
