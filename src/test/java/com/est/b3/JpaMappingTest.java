package com.est.b3;

import static org.assertj.core.api.Assertions.assertThat;

import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Message;
import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.repository.ChatRoomRepository;
import com.est.b3.repository.LikeRepository;
import com.est.b3.repository.MessageRepository;
import com.est.b3.repository.PhotoRepository;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@DisplayName("JPA 매핑 테스트")
@SpringBootTest
class JpaMappingTest {

//  @Autowired
//  private UserRepository userRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private PhotoRepository photoRepository;

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private LikeRepository likeRepository;

  @DisplayName("User , Restaurant , Photo 매핑 Test")
  @Test
  void testInsertUserAndRestaurant() {
//    // 유저 생성 및 저장 , @Builder 사용
//    User user = User.builder()
//        .id(1L) // 오류방지 Long 타입 리터럴 명시
//        .userName("장중원")
//        .nickName("BlueStar")
//        .password("password1234")
//        .address("의정부")
//        .createdAt(LocalDateTime.now())
//        .role("user")
//        .state(1)
//        .build();
//
//    userRepository.save(user);
//
//    Photo photo = Photo.builder()
//        .s3Url("https://dummy.com/image.jpg") // 테스트용 dummy URL
//        .s3Key("test/photo.jpg") // 테스트용 test Key
//        .uploadDate(LocalDateTime.now())
//        .build();
//
//    photoRepository.save(photo);
//
//    // 레스토랑 생성 및 저장 , @Builder 사용
//    Restaurant restaurant = Restaurant.builder()
//        .id(100L)
//        .name("중식당")
//        .address("의정부시 민락동")
//        .menuName("간짜장")
//        .price(9000)
//        .description("돼지기름볶음짜장")
//        .createdAt(LocalDateTime.now())
//        .updatedAt(LocalDateTime.now())
//        .state(1)
//        .user(user)  // User와 연결
//        .photo(photo) // Photo 연결
//        .build();
//
//    restaurantRepository.save(restaurant);
//
//    // 검증
//    Restaurant found = restaurantRepository.findById(100L).orElse(null);
//    assertThat(found).isNotNull();
//    assertThat(found.getUser().getUserName()).isEqualTo("장중원"); // User 참조 확인
//    assertThat(found.getPhoto().getS3Url()).isEqualTo("https://dummy.com/image.jpg"); // Photo 참조 확인
//    assertThat(found.getMenuName()).isEqualTo("간짜장");
  }
  @Test
  @Transactional
  @DisplayName("User , ChatRoom , Message , Like 매핑 Test")
  void testInsertMessageLikeChatRoom(){
//      User user = User.builder()
//          .id(2L)
//          .userName("장중원")
//          .nickName("BlueStar")
//          .password("password1234")
//          .address("의정부")
//          .createdAt(LocalDateTime.now())
//          .role("user")
//          .state(1)
//          .build();
//      User user2 = User.builder()
//          .id(3L)
//          .userName("장중원2")
//          .nickName("BlueStar2")
//          .password("password1234")
//          .address("의정부")
//          .createdAt(LocalDateTime.now())
//          .role("user")
//          .state(1)
//          .build();
//
//      userRepository.save(user);
//      userRepository.save(user2);
//
//      ChatRoom chatRoom = ChatRoom.builder()
//          .id(1L)
//          .user(user)
//          .user2(user2)
//          .createdAt(LocalDateTime.now())
//          .build();
//      chatRoomRepository.save(chatRoom);
//
//      Message message = Message.builder()
//          .id(200L)
//          .chatRoom(chatRoom)
//          .sender(user)
//          .content("Hello World")
//          .createdAt(LocalDateTime.now())
//          .build();
//      messageRepository.save(message);
//
//      // 검증
//      Message foundMessage = messageRepository.findById(200L).orElse(null);
//      assertThat(foundMessage).isNotNull();
//      assertThat(foundMessage.getContent()).isEqualTo("Hello World");
//      assertThat(foundMessage.getSender().getUserName()).isEqualTo("장중원"); //user 참조 확인
//      assertThat(foundMessage.getChatRoom().getUser2().getUserName()).isEqualTo(
//          "장중원2"); // chat_room 참조 확인

  }
}
