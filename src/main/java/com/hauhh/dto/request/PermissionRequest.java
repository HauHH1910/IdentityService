package com.hauhh.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest implements Serializable {

    private String name;

    private String description;
}
