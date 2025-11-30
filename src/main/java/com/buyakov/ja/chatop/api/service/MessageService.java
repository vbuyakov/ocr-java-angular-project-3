package com.buyakov.ja.chatop.api.service;

import com.buyakov.ja.chatop.api.dto.MessageDto;
import com.buyakov.ja.chatop.api.mapper.MessageMapper;
import com.buyakov.ja.chatop.api.model.Message;
import com.buyakov.ja.chatop.api.model.Rental;
import com.buyakov.ja.chatop.api.model.User;
import com.buyakov.ja.chatop.api.repository.MessageRepository;
import com.buyakov.ja.chatop.api.repository.RentalRepository;
import com.buyakov.ja.chatop.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final MessageMapper messageMapper;

    public Message sendMessage(MessageDto messageDto) throws Exception {

        User user = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        Rental rental = rentalRepository.findById(messageDto.getRentalId())
                .orElseThrow(() -> new Exception("Rental not found"));
        Message message = messageMapper.dtoToMessage(messageDto, user, rental);
        return messageRepository.save(message);
    }
}
