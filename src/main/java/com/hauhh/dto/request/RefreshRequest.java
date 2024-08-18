package com.hauhh.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefreshRequest {

    private String token;

}
