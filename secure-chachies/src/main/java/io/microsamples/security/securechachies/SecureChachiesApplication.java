package io.microsamples.security.securechachies;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class SecureChachiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureChachiesApplication.class, args);
    }

}

@RestController
@RequestMapping("/chachkie")
class ChachkieController {
    private Map<String, Chachkie> values = new ConcurrentHashMap<>();
    private String name = "Alex";


    @PostMapping("/up")
    private Chachkie up() {
        return read(this.name).up();
    }

    @PostMapping("/down")
    private Chachkie down() {
        return read(this.name).down();
    }

    @GetMapping
    private Chachkie read() {
        return read(name);
    }


    private Chachkie read(String name) {
        return this.values.computeIfAbsent(name, (k) -> new Chachkie(k));
    }
}

class Chachkie {
    private AtomicInteger sentiment = new AtomicInteger();
    private String name;

    public Integer getSentiment() {
        return sentiment.get();
    }

    public String getName() {
        return name;
    }

    Chachkie(String name) {
        this.name = name;
    }


    Chachkie up() {
        sentiment.incrementAndGet();
        return this;
    }

    Chachkie down() {
        sentiment.decrementAndGet();
        return this;
    }
}