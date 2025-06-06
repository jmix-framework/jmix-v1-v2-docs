package com.company.masqueradeex1.test_support.view;

import com.company.masqueradeex1.test_support.fragment.TestFragment1;
import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.TestView;
import io.jmix.masquerade.component.TextField;
import io.jmix.masquerade.sys.View;


//tag::FragmentsView[]
@TestView
public class FragmentsView extends View<FragmentsView> {

    @TestComponent
    private TestFragment1 testFragment1Root;

    public TestFragment1 getTestFragment1() {
        return testFragment1Root;
    }
}
//end::FragmentsView[]

