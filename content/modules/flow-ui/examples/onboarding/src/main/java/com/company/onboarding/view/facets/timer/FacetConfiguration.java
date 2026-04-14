package com.company.onboarding.view.facets.timer;
// tag::register[]
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.sys.registration.FacetRegistration;
import io.jmix.flowui.sys.registration.FacetRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacetConfiguration {
    @Bean
    public FacetRegistration extTimerFacet() {
        return FacetRegistrationBuilder.create(TimerExtImpl.class) // <1>
                .replaceFacet(Timer.class) // <2>
                .withFacetLoader("timer", TimerFacetExtLoader.class) // <3>
                .build();
    }
}
// end::register[]