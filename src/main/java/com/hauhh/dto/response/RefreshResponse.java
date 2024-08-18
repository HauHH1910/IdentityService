package com.hauhh.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefreshResponse {

    private String token;
    private boolean authenticated;

}
