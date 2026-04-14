package com.company.samlex1.mapper;

import com.company.samlex1.entity.User;
import io.jmix.saml.mapper.user.SynchronizingSamlUserMapper;
import io.jmix.saml.util.SamlAssertionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml4AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// tag::MySynchronizingSamlUserMapper[]
@Component
public class MySynchronizingSamlUserMapper extends SynchronizingSamlUserMapper<User> {

    public MySynchronizingSamlUserMapper() {
        super();
        setSynchronizeRoleAssignments(true); // <1>
    }

    @Override
    protected Class<User> getApplicationUserClass() {
        return User.class;
    }

    @Override
    protected void populateUserAttributes(Assertion assertion, OpenSaml4AuthenticationProvider.ResponseToken responseToken, User jmixUser) {
        String username = SamlAssertionUtils.getUsername(assertion);
        Map<String, List<Object>> assertionAttributes = SamlAssertionUtils.getAssertionAttributes(assertion);
        String firstNameValue = getStringAttributeValue(assertionAttributes, "FirstName", username);
        String lastNameValue = getStringAttributeValue(assertionAttributes, "LastName", username);

        jmixUser.setUsername(username);
        jmixUser.setFirstName(firstNameValue);
        jmixUser.setLastName(lastNameValue);
    }

    protected String getStringAttributeValue(Map<String, List<Object>> assertionAttributes, String attributeName, String username) {
        List<Object> rawValues = assertionAttributes.get(attributeName);
        return CollectionUtils.isNotEmpty(rawValues)
                ? rawValues.get(0).toString()
                : "%s (%s)".formatted(attributeName, username);
    }
}
// end::MySynchronizingSamlUserMapper[]