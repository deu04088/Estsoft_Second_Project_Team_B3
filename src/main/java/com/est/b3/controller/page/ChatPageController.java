package com.est.b3.controller.page;

import com.est.b3.controller.api.ChatController;
import com.est.b3.domain.Boss;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.dto.MessageDto;
import com.est.b3.dto.SessionUserDTO;
import com.est.b3.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatPageController {
    private final ChatService chatService;

    @GetMapping
    public String chatList(HttpSession session, Model model) {
        // 세션 내의 정보 확인
        Object sessionAttribute = session.getAttribute("loginBoss");

        // instanceof : 객체 타입 확인
        // 로그인 풀리면 로그인하라고 보내버림
        // 패턴 변수 인텔리제이가 추천해줌
        if (!(sessionAttribute instanceof SessionUserDTO sessionUser)) {
            log.warn("[SESSION] 객체 타입 불일치");
            return "redirect:/login";
        }

        // 사용자 아이디 확인
        Long bossId = sessionUser.getId();
        log.info("[SESSION] boss id : " + bossId);

        // 채팅방 리스트 가져오기
        // 채팅방, 사용자 id 전달
        List<ChatRoomDto> chatRooms = chatService.getChatRoomsByBossId(bossId)
                .stream()
                .map(room -> chatService.toDto(room, bossId))
                .toList();

        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("user", sessionUser);

        return "chat";
    }

    @GetMapping("/{chatRoomId}")
    public String chat(HttpSession session, Model model, @PathVariable Long chatRoomId) {
        // 세션 내의 정보 확인
        Object sessionAttribute = session.getAttribute("loginBoss");

        // instanceof : 객체 타입 확인
        // 로그인 풀리면 로그인하라고 보내버림
        // 패턴 변수 인텔리제이가 추천해줌
        if (!(sessionAttribute instanceof SessionUserDTO sessionUser)) {
            log.warn("[SESSION] 불일치");
            return "redirect:/login";
        }

        // 사용자 아이디 확인
        Long bossId = sessionUser.getId();
        log.info("[SESSION] boss id : " + bossId);

        // 채팅방 리스트 가져오기
        // 채팅방, 사용자 id 전달
        List<ChatRoomDto> chatRooms = chatService.getChatRoomsByBossId(bossId)
                .stream()
                .map(room -> chatService.toDto(room, bossId))
                .toList();

        for (ChatRoomDto chatRoom : chatRooms) {
            log.info("ChatRoomDto : " + chatRoom.toString());
        }

        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("user", sessionUser);

        // 현재 채팅방 정보 가져오기
        ChatRoomDto partnerInfo = chatService.getChatRoomDetail(chatRoomId, bossId);
        // 메시지 리스트 가져오기
        List<MessageDto> messages = chatService.getMessagesByChatRoomId(chatRoomId);

        model.addAttribute("partnerInfo", partnerInfo);
        model.addAttribute("messages", messages);

        return "chat";
    }
}
