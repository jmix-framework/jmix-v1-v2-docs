package com.company.demo.listener;

// tag::import-order[]
import com.company.demo.entity.Order;
// end::import-order[]
// tag::import-entity-saving-event[]
import io.jmix.core.event.EntitySavingEvent;
// end::import-entity-saving-event[]
// tag::import-event-listener[]
import org.springframework.context.event.EventListener;
// end::import-event-listener[]
// tag::import-component[]
import org.springframework.stereotype.Component;
// end::import-component[]

import java.util.concurrent.atomic.AtomicLong;

// tag::saving-event[]

@Component
public class OrderEventListener {

    @EventListener
    void onOrderSaving(EntitySavingEvent<Order> event) {
        if (event.isNewEntity()) {
            Order order = event.getEntity();
            order.setNumber(generateOrderNumber());
        }
    }
    // end::saving-event[]

    private AtomicLong orderNumber = new AtomicLong();

    private String generateOrderNumber() {
        return String.valueOf(orderNumber.incrementAndGet());
    }
}