package io.microsamples.security.oauth2server;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {
    @GetMapping("/user")
    Map<String, Object> user(Authentication authentication) {
        return Collections.singletonMap("sub", authentication.getName());
    }
}
