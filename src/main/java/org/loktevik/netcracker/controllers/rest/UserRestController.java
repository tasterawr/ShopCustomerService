package org.loktevik.netcracker.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.service.CustomerService;
import org.loktevik.netcracker.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final CustomerService customerService;
}

