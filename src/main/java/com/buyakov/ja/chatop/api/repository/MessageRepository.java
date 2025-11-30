package com.buyakov.ja.chatop.api.repository;

import com.buyakov.ja.chatop.api.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
