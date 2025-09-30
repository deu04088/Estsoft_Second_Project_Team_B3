package com.est.b3.controller.page;

import com.est.b3.controller.api.ChatController;
import com.est.b3.domain.Boss;
import com.est.b3.dto.ChatRoomDto;
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
        Boss boss = (Boss) session.getAttribute("loginBoss");

        if (boss == null) {
            log.warn("[SESSION]boss is null");
            return "redirect:/login";
        }
        // 사용자 아이디
        Long bossId = boss.getId();
        log.info("[SESSION] boss id : " + bossId);

        // 채팅방 리스트 가져오기
        // 채팅방, 사용자 id 전달
        List<ChatRoomDto> chatRooms = chatService.getChatRoomsByBossId(bossId)
                .stream()
                .map(room -> chatService.toDto(room, bossId))
                .toList();

        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("user", boss);

        return "chat";
    }

    @GetMapping("/{chatRoomId}")
    public String chat(Model model, @PathVariable Long chatRoomId) {
        // 세션 user 더미
        Map<String, Object> dummyUser = new HashMap<>();
        dummyUser.put("username", "testUser");
        model.addAttribute("session", Map.of("user", dummyUser));

        // partner 더미
        Map<String, Object> dummyPartner = new HashMap<>();
        dummyPartner.put("profileUrl", "/images/default-profile.png");
        dummyPartner.put("nickname", "상대방유저");
        dummyPartner.put("address", "강서구 화곡동");
        model.addAttribute("partner", dummyPartner);

        return "chat";
    }

    @GetMapping("/chat")
    public String chat(Model model) {
        return "chat-test";
    }
}
