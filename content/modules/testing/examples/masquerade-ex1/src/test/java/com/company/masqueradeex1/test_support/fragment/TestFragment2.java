package com.company.masqueradeex1.test_support.fragment;

import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.component.TextField;
import io.jmix.masquerade.sys.Composite;

//tag::TestFragment2[]
@TestComponent(path = {"FragmentsView", "testFragment2Root"}) // <2>
public class TestFragment2 extends Composite<TestFragment2> {

    @TestComponent
    private TextField testFragment2TextField;

    public TextField getTestField() {
        return testFragment2TextField;
    }
}
//end::TestFragment2[]
