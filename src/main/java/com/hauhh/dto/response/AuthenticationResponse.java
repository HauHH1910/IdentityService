package com.hauhh.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationResponse {

    private boolean authenticated;
    private String token;

}
