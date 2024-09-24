package com.hauhh.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationResponse implements Serializable {

    private boolean authenticated;
    private String token;

}
