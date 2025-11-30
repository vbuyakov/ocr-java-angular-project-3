package com.buyakov.ja.chatop.api.controller;

import com.buyakov.ja.chatop.api.dto.MessageDto;
import com.buyakov.ja.chatop.api.dto.ResponseMessage;
import com.buyakov.ja.chatop.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path="/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<ResponseMessage>
    sendMessage(@RequestBody MessageDto messageDto) {

        try {
            messageService.sendMessage(messageDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Message sent successfully!"));
        } catch (Exception e) {}

        return  ResponseEntity.notFound().build();
    }


}
