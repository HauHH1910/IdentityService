package com.hauhh.controllers.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;

}
