package com.company.demo.security;

// tag::import-user[]
import com.company.demo.entity.User;
// end::import-user[]
// tag::import-logger[]
import org.slf4j.Logger;
// end::import-logger[]
// tag::import-logger-factory[]
import org.slf4j.LoggerFactory;
// end::import-logger-factory[]
// tag::import-event-listener[]
import org.springframework.context.event.EventListener;
// end::import-event-listener[]
// tag::import-auth-failure-event[]
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
// end::import-auth-failure-event[]
// tag::import-auth-success-event[]
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
// end::import-auth-success-event[]
// tag::import-interactive-auth-success-event[]
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
// end::import-interactive-auth-success-event[]
// tag::import-logout-success-event[]
import org.springframework.security.authentication.event.LogoutSuccessEvent;
// end::import-logout-success-event[]
// tag::import-component[]
import org.springframework.stereotype.Component;
// end::import-component[]

// tag::class[]

@Component
public class AuthenticationEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(AuthenticationEventListener.class);

    @EventListener
    public void onInteractiveAuthenticationSuccess(
            InteractiveAuthenticationSuccessEvent event) { // <1>
        User user = (User) event.getAuthentication().getPrincipal(); // <2>
        log.info("User logged in: " + user.getUsername());
    }

    @EventListener
    public void onAuthenticationSuccess(
            AuthenticationSuccessEvent event) { // <3>
        User user = (User) event.getAuthentication().getPrincipal(); // <4>
        log.info("User authenticated " + user.getUsername());
    }

    @EventListener
    public void onAuthenticationFailure(
            AbstractAuthenticationFailureEvent event) { // <5>
        String username = (String) event.getAuthentication().getPrincipal(); // <6>
        log.info("User login attempt failed: " + username);
    }

    @EventListener
    public void onLogoutSuccess(LogoutSuccessEvent event) { // <7>
        User user = (User) event.getAuthentication().getPrincipal(); // <8>
        log.info("User logged out: " + user.getUsername());
    }
}
// end::class[]