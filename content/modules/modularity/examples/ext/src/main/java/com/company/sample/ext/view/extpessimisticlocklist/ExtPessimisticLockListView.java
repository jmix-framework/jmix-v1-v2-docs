package com.company.sample.ext.view.extpessimisticlocklist;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DefaultMainViewParent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.pessimisticlockflowui.view.pessimisticlock.PessimisticLockListView;

// tag::extend[]
@Route(value = "pslock/extpessimistic-locks", layout = DefaultMainViewParent.class)
@ViewController(id = "ext_pslock_LockInfo.list")
@ViewDescriptor(path = "ext-pessimistic-lock-list-view.xml")
public class ExtPessimisticLockListView extends PessimisticLockListView {
    @Override
    public void onInit(InitEvent event) {
        messageBundle.setMessageGroup("io.jmix.pessimisticlockflowui.view.pessimisticlock");
        super.onInit(event);
    }
}
// end::extend[]