package com.company.samlex1.mapper;

import io.jmix.saml.user.DefaultJmixSamlUserDetails;


// tag::MyUser[]
public class MyUser extends DefaultJmixSamlUserDetails {

    private String position; // <1>

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
// end::MyUser[]