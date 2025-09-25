package com.est.b3.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

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
    public String addressCertify() {

        return "address-certify"; // templates/address-certify.html
    }
}
