package com.hauhh.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IntrospectResponse implements Serializable {

    private boolean valid;

}
