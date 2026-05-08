package com.company.demo.app;

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

// tag::class[]

@Component
public class OrderLineEventListener {

    @Autowired
    private DataManager dataManager;

    // tag::recalculate-order-amount[]
    @EventListener
    public void recalculateOrderAmount(EntityChangedEvent<OrderLine> event) {
        Order order = findOrderFromEvent(event);

        BigDecimal amount = new OrderAmountCalculation().calculateTotalAmount(order.getLines());
        order.setAmount(amount);

        dataManager.save(order);
    }
    // end::recalculate-order-amount[]

    private Order findOrderFromEvent(EntityChangedEvent<OrderLine> event) {
        Order order;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Order> orderId = event.getChanges().getOldReferenceId("order");
            order = dataManager.load(orderId).one();
        } else {
            OrderLine orderLine = dataManager.load(event.getEntityId()).one();
            order = orderLine.getOrder();
        }
        return order;
    }
}
// end::class[]