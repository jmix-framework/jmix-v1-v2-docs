package com.company.demo.view.order;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.repository.OrderRepository;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.core.repository.JmixDataRepositoryContext;
import io.jmix.core.repository.JmixDataRepositoryUtils;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@Route(value = "orders-2", layout = MainView.class)
@ViewController(id = "sample_Order.list2")
@ViewDescriptor(path = "order-list-view-2.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView2 extends StandardListView<Order> {

    @Autowired
    private OrderRepository repository;

//    @Install(to = "ordersDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
//    private List<Order> loadDelegate(Pageable pageable, JmixDataRepositoryContext context) {
//        return repository.findAllSlice(pageable, context).getContent();
//    }

    @Install(to = "ordersDataGrid.removeAction", subject = "delegate")
    private void ordersDataGridRemoveDelegate(final Collection<Order> collection) {
        repository.deleteAll(collection);
    }

    @Install(to = "pagination", subject = "totalCountByRepositoryDelegate")
    private Long paginationTotalCountByRepositoryDelegate(final JmixDataRepositoryContext context) {
        return repository.count(context);
    }

    // tag::load-context-to-repository-context[]
    @Install(to = "ordersDl", target = Target.DATA_LOADER)
    private List<Order> customersDlLoadDelegate(LoadContext<Order> loadContext) {
        JmixDataRepositoryContext repositoryContext = JmixDataRepositoryUtils.buildRepositoryContext(loadContext);
        return repository.findAll(repositoryContext);
    }
    // end::load-context-to-repository-context[]
}