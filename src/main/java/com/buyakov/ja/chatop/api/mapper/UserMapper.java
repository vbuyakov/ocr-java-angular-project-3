package com.buyakov.ja.chatop.api.mapper;

import com.buyakov.ja.chatop.api.dto.UserInfoResponse;
import com.buyakov.ja.chatop.api.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoResponse toUserInfoResponse(User user);
}
