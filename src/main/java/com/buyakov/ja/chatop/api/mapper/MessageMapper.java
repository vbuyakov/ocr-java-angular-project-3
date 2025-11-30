package com.buyakov.ja.chatop.api.mapper;

import com.buyakov.ja.chatop.api.dto.MessageDto;
import com.buyakov.ja.chatop.api.model.Message;
import com.buyakov.ja.chatop.api.model.Rental;
import com.buyakov.ja.chatop.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rental", source="rental")
    @Mapping(target = "user", source="user")
    Message dtoToMessage(MessageDto messageDto, User user, Rental rental );
}
