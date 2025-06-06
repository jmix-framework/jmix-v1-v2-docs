package com.company.masqueradeex1.test_support.fragment;

import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.component.TextField;
import io.jmix.masquerade.sys.Composite;

//tag::TestFragment1[]
public class TestFragment1 extends Composite<TestFragment1> { //<1>

    @TestComponent
    private TextField testFragment1TextField;

    public TextField getTestField() {
        return testFragment1TextField;
    }
}
//end::TestFragment1[]
