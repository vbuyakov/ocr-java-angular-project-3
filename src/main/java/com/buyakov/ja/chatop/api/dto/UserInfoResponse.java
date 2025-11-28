package com.buyakov.ja.chatop.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoResponse {
    private long id;
    private String name;
    private String email;
    private String created_at;
    private String updated_at;
}
