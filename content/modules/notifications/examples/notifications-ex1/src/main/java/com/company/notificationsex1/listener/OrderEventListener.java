package com.company.notificationsex1.listener;
// tag::ntf-create[]
import com.company.notificationsex1.entity.Order;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.channel.impl.InAppNotificationChannel;
import io.jmix.notifications.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    @Autowired
    private DataManager dataManager;
    @Autowired
    protected NotificationManager notificationManager;

    @TransactionalEventListener // <1>
    @Transactional(propagation = Propagation.REQUIRES_NEW) // <2>
    public void onOrderChangedAfterCommit(final EntityChangedEvent<Order> event) {
        try {
            if (event.getType() == EntityChangedEvent.Type.CREATED) {
                Order order = dataManager.load(event.getEntityId()).one();

                notificationManager.createNotification() // <3>
                        .withSubject("New order")// <4>
                        .withRecipientUsernames("admin") // <5>
                        .toChannelsByNames(InAppNotificationChannel.NAME) // <6>
                        .withContentType(ContentType.PLAIN) // <7>
                        .withBody("A new order with number " + order.getNumber() + " is created.") // <8>
                        .send(); // <9>
            }
        } catch (Exception e) {
            log.error("Error processing order", e);
        }
    }
}
// end::ntf-create[]