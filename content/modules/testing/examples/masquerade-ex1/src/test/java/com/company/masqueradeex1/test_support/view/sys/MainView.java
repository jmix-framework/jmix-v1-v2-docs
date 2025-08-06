package com.company.masqueradeex1.test_support.view.sys;

import io.jmix.masquerade.TestComponent;
import io.jmix.masquerade.TestView;
import io.jmix.masquerade.component.ListMenu;
import io.jmix.masquerade.sys.Composite;
import io.jmix.masquerade.sys.View;

@TestView
public class MainView extends View<MainView> {

    @TestComponent
    private ListMenu listMenu;

    public <T extends Composite<T>> T openItem(Class<T> viewClass, String... path) {
        return listMenu.openItem(viewClass, path);
    }
}

