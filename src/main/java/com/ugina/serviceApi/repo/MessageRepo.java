package com.ugina.serviceApi.repo;

import com.ugina.serviceApi.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
