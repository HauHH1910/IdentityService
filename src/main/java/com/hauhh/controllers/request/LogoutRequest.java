package com.hauhh.controllers.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LogoutRequest implements Serializable {

    private String token;

}