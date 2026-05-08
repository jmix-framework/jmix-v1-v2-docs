package com.company.demo.listener;

// tag::import-order[]
import com.company.demo.entity.Order;
// end::import-order[]
// tag::import-order-line[]
import com.company.demo.entity.OrderLine;
// end::import-order-line[]
// tag::import-data-manager[]
import io.jmix.core.DataManager;
// end::import-data-manager[]
// tag::import-id[]
import io.jmix.core.Id;
// end::import-id[]
// tag::import-entity-changed-event[]
import io.jmix.core.event.EntityChangedEvent;
// end::import-entity-changed-event[]
// tag::import-autowired[]
import org.springframework.beans.factory.annotation.Autowired;
// end::import-autowired[]
// tag::import-event-listener[]
import org.springframework.context.event.EventListener;
// end::import-event-listener[]
// tag::import-component[]
import org.springframework.stereotype.Component;
// end::import-component[]

import java.math.BigDecimal;

// tag::entity-changed[]

@Component
public class OrderLineEventListener {

    @Autowired
    private DataManager dataManager;

    @EventListener
    void onOrderLineChangedBeforeCommit(EntityChangedEvent<OrderLine> event) {
        Order order;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {               // <1>
            Id<Order> orderId = event.getChanges().getOldReferenceId("order");  // <2>
            order = dataManager.load(orderId).one();
        } else {
            OrderLine orderLine = dataManager.load(event.getEntityId()).one();
            order = orderLine.getOrder();
        }
        BigDecimal amount = order.getLines().stream()
                .map(line -> line.getProduct().getPrice().multiply(
                        BigDecimal.valueOf(line.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(amount);
        dataManager.save(order);
    }
}
// end::entity-changed[]