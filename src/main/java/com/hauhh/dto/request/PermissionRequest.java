package com.hauhh.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {

    private String name;

    private String description;
}
