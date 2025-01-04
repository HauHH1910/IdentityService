package com.hauhh.controllers.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefreshRequest implements Serializable {

    private String token;

}
