package com.company.onboarding.view.component.simplepagination;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.flowui.component.PaginationComponent;
import io.jmix.flowui.component.pagination.SimplePagination;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(value = "SimplePaginationView", layout = MainView.class)
@ViewController("SimplePaginationView")
@ViewDescriptor("simple-pagination-view.xml")
public class SimplePaginationView extends StandardView {
    // tag::dataManager[]
    @Autowired
    private DataManager dataManager;

    // end::dataManager[]

    // tag::BeforeRefreshEvent[]
    @Subscribe("simplePagination")
    public void onSimplePaginationBeforeRefresh(
            final PaginationComponent.BeforeRefreshEvent<SimplePagination> event) {
        if (Objects.requireNonNull(event.getSource().getPaginationLoader()).size() > 10) // <1>
            event.preventRefresh(); // <2>
    }
    // end::BeforeRefreshEvent[]
    // tag::totalCountDelegate[]
    @Install(to = "simplePagination", subject = "totalCountDelegate")
    private Integer simplePaginationTotalCountDelegate(
            final LoadContext<User> loadContext) {
        return dataManager.loadValue("select count(e) from User e", Integer.class).one();
    }
// end::totalCountDelegate[]
}
