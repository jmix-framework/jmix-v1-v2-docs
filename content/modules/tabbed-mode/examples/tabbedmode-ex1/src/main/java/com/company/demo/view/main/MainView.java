package com.company.demo.view.main;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.tabbedmode.component.tabsheet.MainTabSheet;
import io.jmix.tabbedmode.component.workarea.TabbedViewsContainer.SelectedChangeEvent;
import io.jmix.tabbedmode.component.workarea.TabbedViewsContainer.TabsCollectionChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
// tag::baseClass[]
import io.jmix.tabbedmode.app.main.StandardTabbedModeMainView;

@Route("")
@ViewController(id = "MainView")
@ViewDescriptor(path = "main-view.xml")
public class MainView extends StandardTabbedModeMainView {
    // tag::notifications[]

    @Autowired
    private Notifications notifications;
    // end::notifications[]
    // tag::tabsCollectionChangeEvent[]

    @Subscribe("mainTabSheet")
    public void onMainTabSheetTabsCollectionChange(final TabsCollectionChangeEvent<MainTabSheet> event) {
        notifications.create("Tabs collection changed", "Change type: " + event.getChangeType())
                .show();
    }
    // end::tabsCollectionChangeEvent[]
    // tag::selectedChangeEvent[]

    @Subscribe("mainTabSheet")
    public void onMainTabSheetSelectedChange(final SelectedChangeEvent<MainTabSheet> event) {
        notifications.create(event.getSelectedTab() + " is now selected")
                .show();
    }
    // end::selectedChangeEvent[]
}
// end::baseClass[]
