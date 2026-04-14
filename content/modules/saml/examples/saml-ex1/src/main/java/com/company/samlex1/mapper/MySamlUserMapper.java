package com.company.samlex1.mapper;
/**
import io.jmix.saml.mapper.role.SamlAssertionRolesMapper;
import io.jmix.saml.mapper.user.BaseSamlUserMapper;
import io.jmix.saml.util.SamlAssertionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml4AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;


// tag::MySamlUserMapper[]
@Component
public class MySamlUserMapper extends BaseSamlUserMapper<MyUser> {

    @Autowired
    protected SamlAssertionRolesMapper rolesMapper;

    @Override
    protected MyUser initJmixUser(Assertion assertion) { // <1>
        return new MyUser();
    }

    @Override
    protected void populateUserAttributes(Assertion assertion, OpenSaml4AuthenticationProvider.ResponseToken responseToken, MyUser jmixUser) { // <2>
        Map<String, List<Object>> assertionAttributes = SamlAssertionUtils.getAssertionAttributes(assertion);
        List<Object> rawValues = assertionAttributes.get("Position");
        String positionValue = CollectionUtils.isNotEmpty(rawValues) ? rawValues.get(0).toString() : null;
        jmixUser.setPosition(positionValue);
        System.out.println(positionValue);
    }

    @Override
    protected void populateUserAuthorities(Assertion assertion, MyUser jmixUser) { // <3>
        Collection<? extends GrantedAuthority> grantedAuthorities = rolesMapper.toGrantedAuthorities(assertion);
        jmixUser.setAuthorities(grantedAuthorities);
    }
}
// end::MySamlUserMapper[]
 **/