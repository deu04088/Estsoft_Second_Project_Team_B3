package com.est.b3.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionStatResponse {
    private String siDo;
    private String guGun;
    private String dongEupMyeon;
    private Long count;
}
