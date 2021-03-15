package io.microsamples.security.securechachies.conf;

import java.lang.reflect.Method;
import java.util.Collection;

import io.microsamples.security.securechachies.Chachkie;
import io.microsamples.security.securechachies.ChachkieController;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorizationModelProcessor implements
        RepresentationModelProcessor<EntityModel<Chachkie>> {

    private final AccessDecisionManager accessDecisionManager;
    private final MethodSecurityMetadataSource metadataSource;

    public AuthorizationModelProcessor(MethodInterceptor methodSecurityInterceptor) {
        MethodSecurityInterceptor interceptor = (MethodSecurityInterceptor) methodSecurityInterceptor;
        this.accessDecisionManager = interceptor.getAccessDecisionManager();
        this.metadataSource = interceptor.getSecurityMetadataSource();
    }

    @Override
    public EntityModel<Chachkie> process(EntityModel<Chachkie> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChachkieController controller = methodOn(ChachkieController.class);
        for (Method method : controller.getClass().getDeclaredMethods()) {
            MethodInvocation invocation = new SimpleMethodInvocation(controller, method, authentication);
            try {
                if (method.canAccess(controller)) {
                    Collection<ConfigAttribute> attributes = this.metadataSource.getAttributes(method, controller.getClass());
                    this.accessDecisionManager.decide(authentication, invocation, attributes);
                    model.add(linkTo(method, authentication).withRel(method.getName()));
                }
            } catch (Throwable ex) {
            }
        }
        return model;
    }
}
