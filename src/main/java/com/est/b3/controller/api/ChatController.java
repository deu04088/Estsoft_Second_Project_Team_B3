package com.est.b3.controller.api;

import com.est.b3.dto.ChatDto;
import com.est.b3.domain.Message;
import com.est.b3.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public Message sendMessage(ChatDto dto) {
        return chatService.saveMessage(dto);
    }
}
