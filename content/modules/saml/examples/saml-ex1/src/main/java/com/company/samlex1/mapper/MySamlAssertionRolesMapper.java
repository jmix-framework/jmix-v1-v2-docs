package com.company.samlex1.mapper;

/**
import io.jmix.saml.mapper.role.BaseSamlAssertionRolesMapper;
import io.jmix.saml.util.SamlAssertionUtils;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;



// tag::MySamlAssertionRolesMapper[]
@Component
public class MySamlAssertionRolesMapper extends BaseSamlAssertionRolesMapper {

    @Override
    protected Collection<String> getResourceRolesCodes(Assertion assertion) {
        Map<String, List<Object>> assertionAttributes = SamlAssertionUtils.getAssertionAttributes(assertion);
        List<Object> rawPositionAttributeValues = assertionAttributes.get("Position");

        Collection<String> jmixRoleCodes = new HashSet<>();
        rawPositionAttributeValues.stream()
                .map(Object::toString)
                .forEach(position -> {
                    if ("Manager".equals(position)) {
                        jmixRoleCodes.add("edit-contracts");
                        jmixRoleCodes.add("view-archive");
                    } else {
                        jmixRoleCodes.add("view-contracts");
                    }
                });

        return jmixRoleCodes;
    }

    @Override
    protected Collection<String> getRowLevelRoleCodes(Assertion assertion) {
        // Do something for row-level role codes
        return List.of();
    }
}
// end::MySamlAssertionRolesMapper[]

**/