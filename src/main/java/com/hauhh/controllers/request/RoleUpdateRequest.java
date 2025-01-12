package com.hauhh.controllers.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateRequest implements Serializable {

    private String name;

    private String description;

}
