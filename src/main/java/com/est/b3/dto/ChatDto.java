package com.est.b3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {
    private Long chatRoomId;
    private Long senderId;
    private String content;
}