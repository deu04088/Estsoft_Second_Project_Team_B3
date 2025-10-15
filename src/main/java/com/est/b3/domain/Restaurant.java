package com.est.b3.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "restaurants")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(length = 30, nullable = false)
  private String name;

  @Column(length = 50)
  private String address;

  @Column(name = "siDo", length = 20)
  private String siDo;

  @Column(name = "guGun", length = 20)
  private String guGun;

  @Column(name = "dongEupMyeon", length = 20)
  private String dongEupMyeon;

  @Column(name = "menu_name", length = 30)
  private String menuName;

  @Column
  private Integer price;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column
  private Integer state;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "boss_id")
  private Boss boss;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "photo_id")
  private Photo photo;

  @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
  private List<Like> likes = new ArrayList<>();

  @Column(name = "view_count")
  private Integer viewCount = 0;

  public void update(
        String name,
        String menuName,
        Integer price,
        String description,
        String address,
        Photo photo,
        String siDo,
        String guGun,
        String dongEupMyeon
    ) {
        this.name = name;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.address = address;
        this.photo = photo;
        this.siDo = siDo;
        this.guGun = guGun;
        this.dongEupMyeon = dongEupMyeon;
  }

  public void toggleState() {
    this.state = (this.state == 1) ? 0 : 1;
  }

}