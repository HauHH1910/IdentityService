package com.hauhh.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefreshResponse implements Serializable {

    private String token;
    private boolean authenticated;

}
