package com.est.b3.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ChatPageController {

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
