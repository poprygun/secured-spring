package io.microsamples.security.securechachies.conf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class CsrfHeaderAdvice {

    @ModelAttribute("csrf")
    public CsrfToken token(CsrfToken token, HttpServletResponse response){
        response.setHeader(token.getHeaderName(), token.getToken());
        return token;
    }
}
