package com.company.masqueradeex1.ui_autotest;

import org.junit.jupiter.api.Test;
import com.company.masqueradeex1.test_support.fragment.TestFragment2;
import com.company.masqueradeex1.test_support.view.FragmentsView;
import com.company.masqueradeex1.test_support.view.sys.MainView;

import static io.jmix.masquerade.JConditions.value;
import static io.jmix.masquerade.Masquerade.$j;

public class FragmentsUiTest extends AbstractUiTest {
    @Test
    public void testFragmentUi() {
        //tag::openFragmentsView[]
        FragmentsView fragmentsView = openFragmentsView();

        //end::openFragmentsView[]

        //tag::testChildFragment[]
        fragmentsView.getTestFragment1()
                .getTestField()
                .shouldHave(value(""))
                .setValue("Fragment_1")
                .shouldHave(value("Fragment_1"));
        //end::testChildFragment[]

        //tag::testLoneFragment[]
        $j(TestFragment2.class)
                .getTestField()
                .shouldHave(value(""))
                .setValue("Fragment_2")
                .shouldHave(value("Fragment_2"));
        //end::testLoneFragment[]
    }

    protected FragmentsView openFragmentsView() {
        MainView mainView = loginAsAdmin();

        return mainView.openItem(FragmentsView.class,
                "applicationListItem", "fragmentsViewListItem");
    }
}
