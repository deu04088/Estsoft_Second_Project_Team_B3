package com.est.b3.controller.page;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");

        if (isAdminAuth == null || !isAdminAuth) {

            // AJAX 요청이라면 JSON 응답
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션 만료");
            }

            redirectAttributes.addFlashAttribute("alert", "관리자 인증이 필요합니다.");

            return "redirect:/restaurants";
        }

        return "admin/dashboard"; // templates/admin/dashboard.html
    }

    @GetMapping("/admin/users")
    public String users(HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");

        if (isAdminAuth == null || !isAdminAuth) {
            redirectAttributes.addFlashAttribute("alert", "관리자 인증이 필요합니다.");
            return "redirect:/restaurants";
        }

        return "admin/users";
    }

    @GetMapping("/admin/posts")
    public String posts(HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");

        if (isAdminAuth == null || !isAdminAuth) {
            redirectAttributes.addFlashAttribute("alert", "관리자 인증이 필요합니다.");
            return "redirect:/restaurants";
        }

        return "admin/posts";
    }

    @GetMapping("/admin/stats")
    public String stats(HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");

        if (isAdminAuth == null || !isAdminAuth) {
            redirectAttributes.addFlashAttribute("alert", "관리자 인증이 필요합니다.");
            return "redirect:/restaurants";
        }

        return "admin/stats";
    }
}
