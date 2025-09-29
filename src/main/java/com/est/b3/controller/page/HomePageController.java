package com.est.b3.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomePageController {

    private final String googleMapsApiKey = Dotenv.load().get("GOOGLE_MAPS_API_KEY");


  // 메인 페이지[단순 연결]
    @GetMapping("/")
    public String index() {

        return "index"; // templates/index.html
    }

    // 로그인 페이지[단순 연결]
    @GetMapping("/login")
    public String login() {

        return "login"; // templates/login.html
    }

    // 회원가입 페이지[단순 연결]
    @GetMapping("/signup")
    public String signup() {

        return "signup"; // templates/signup.html
    }

    // 동네 인증 페이지[단순 연결]
    @GetMapping("/address-certify")
    public String addressCertify(Model model) {
        model.addAttribute("apiKey", googleMapsApiKey);
        return "address-certify"; // templates/address-certify.html
    }

    @GetMapping("/chat")
    public String chat(Model model) {
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

}
