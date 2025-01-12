package com.hauhh.controllers.impl;


import com.hauhh.controllers.request.RoleCreationRequest;
import com.hauhh.controllers.request.RoleUpdateRequest;
import com.hauhh.controllers.response.RoleResponse;
import com.hauhh.services.impl.BaseServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Role Controller")
@RequestMapping("/api/roles")
public class RoleController extends BaseController<RoleResponse, RoleCreationRequest, RoleUpdateRequest> {

    public RoleController(BaseServiceImpl<RoleResponse, RoleCreationRequest, RoleUpdateRequest> service) {
        super(service);
    }

}
