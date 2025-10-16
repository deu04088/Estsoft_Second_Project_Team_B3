package com.est.b3.controller.page;

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

import java.util.Comparator;
import java.util.List;

// 채팅 페이지 이동 컨트롤러
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatPageController {
    private final ChatService chatService;

    @GetMapping
    public String chatList(HttpSession session, Model model, @RequestParam(required = false) Long partnerId) {
        // 세션 내의 정보 확인
        Object sessionAttribute = session.getAttribute("loginBoss");

        // instanceof : 객체 타입 확인
        // 로그인 풀리면 로그인하라고 보내버림
        // 패턴 변수 인텔리제이가 추천해줌
        if (!(sessionAttribute instanceof SessionUserDTO sessionUser)) {
            log.warn("[SESSION] 객체 타입 불일치");
            return "redirect:/login";
        }

        // 주소 정보가 없으면 동네 인증 페이지로
        if (sessionUser.getAddress() == null || sessionUser.getAddress().isBlank()) {
            return "redirect:/address-certify";
        }

        // 사용자 아이디 확인
        Long bossId = sessionUser.getId();
        log.info("[SESSION] boss id : " + bossId);

        if (partnerId != null) {
            // 자기 자신과의 채팅 방지
            if (bossId.equals(partnerId)) {
                log.warn("[CHAT] 자기 자신과 채팅 시도: bossId={}", bossId);
                return "redirect:/chat"; // 목록 페이지로 돌아감
            }

            try {
                // ChatService에서 채팅방 생성 및 초기 메시지 저장이 모두 처리됨
                Long chatRoomId = chatService.getOrCreateChatRoom(bossId, partnerId);
                log.info("[CHAT] 채팅방 생성 또는 조회 성공: roomId={}", chatRoomId);

                // 생성된 채팅방 ID로 리다이렉트하여 상세 페이지 로드
                return "redirect:/chat/" + chatRoomId;
            } catch (IllegalArgumentException e) {
                log.error("[CHAT] 채팅방 생성 오류: {}", e.getMessage());
                return "redirect:/chat"; // 오류 시 목록 페이지로 리다이렉트
            }
        }

        // 채팅방 리스트 가져오기, 최근 메시지 시간 기준으로 내림차순 정렬
        // 채팅방, 사용자 id 전달
        List<ChatRoomDto> chatRooms = chatService.getChatRoomsByBossId(bossId)
                .stream()
                .map(room -> chatService.toDto(room, bossId))
                .toList();

        List<ChatRoomDto> sortedChatRooms = chatRooms.stream()
                .sorted(Comparator.comparing(ChatRoomDto::getLastMessageCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();

        // 안읽은 메시지 수
        int totalUnreadCount = sortedChatRooms.stream()
                .mapToInt(ChatRoomDto::getUnreadCount)
                .sum();

        model.addAttribute("chatRooms", sortedChatRooms); // 정렬된 리스트 사용
        model.addAttribute("user", sessionUser);
        model.addAttribute("totalUnreadCount", totalUnreadCount);

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

        // 내림차순 정렬
        List<ChatRoomDto> sortedChatRooms = chatRooms.stream()
                .sorted(Comparator.comparing(ChatRoomDto::getLastMessageCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();

        // 안읽은 메시지 수
        int totalUnreadCount = sortedChatRooms.stream()
                .mapToInt(ChatRoomDto::getUnreadCount)
                .sum();

        model.addAttribute("chatRooms", sortedChatRooms);
        model.addAttribute("user", sessionUser);
        model.addAttribute("totalUnreadCount", totalUnreadCount);

        // 현재 채팅방 정보 가져오기
        ChatRoomDto partnerInfo = chatService.getChatRoomDetail(chatRoomId, bossId);
        // 메시지 리스트 가져오기
        List<MessageDto> messages = chatService.getMessagesByChatRoomId(chatRoomId);

        model.addAttribute("partnerInfo", partnerInfo);
        model.addAttribute("messages", messages);

        return "chat";
    }
}
