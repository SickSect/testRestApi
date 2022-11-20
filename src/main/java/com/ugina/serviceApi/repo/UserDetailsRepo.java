package com.ugina.serviceApi.repo;

import com.ugina.serviceApi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
