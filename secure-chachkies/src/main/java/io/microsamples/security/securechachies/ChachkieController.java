package io.microsamples.security.securechachies;

import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/chachkie")
public class ChachkieController {
    private final Map<String, Chachkie> chachkies = new ConcurrentHashMap<>();


    @PostMapping("/up")
    @PreAuthorize("hasAuthority('SCOPE_chachkie.write')")
    public EntityModel<Chachkie> up(Authentication authentication) {
        Chachkie chachkie = read(authentication.getName());
        return EntityModel.of(chachkie.up());
    }

    @PostMapping("/down")
    @PreAuthorize("hasAuthority('SCOPE_chachkie.write')")
    public EntityModel<Chachkie> down(Authentication authentication) {
        Chachkie chachkie = read(authentication.getName());
        return EntityModel.of(chachkie.down());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_chachkie.read')")
    public EntityModel<Chachkie> read(Authentication authentication) {
        return EntityModel.of(read(authentication.getName()));
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }


    private Chachkie read(String name) {
        return this.chachkies.computeIfAbsent(name, (k) -> new Chachkie(name));
    }
}
