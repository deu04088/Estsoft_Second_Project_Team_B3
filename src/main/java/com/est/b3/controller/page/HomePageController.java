package com.est.b3.controller.page;

import com.est.b3.dto.SessionUserDTO;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
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
    public String login(HttpSession session) {

        // 이미 로그인 되어 있다면 로그인 페이지로 접근 불가
        if (session.getAttribute("loginBoss") != null) {
            return "redirect:/"; // 현재는 index.html로 이동하게끔 했으나, 이후에 "redirect:/restaurants"로 이동 논의 예정
        }

        return "login"; // templates/login.html
    }

    // 회원가입 페이지[단순 연결]
    @GetMapping("/signup")
    public String signup() {

        return "signup"; // templates/signup.html
    }

    // 동네 인증 페이지
    @GetMapping("/address-certify")
    public String addressCertify(HttpSession httpSession, Model model) {
      SessionUserDTO loginUser = (SessionUserDTO) httpSession.getAttribute("loginBoss");
      if (loginUser == null) {
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
      }
      model.addAttribute("user", loginUser);
      model.addAttribute("apiKey", googleMapsApiKey);
      return "address-certify"; // templates/address-certify.html
    }
}
