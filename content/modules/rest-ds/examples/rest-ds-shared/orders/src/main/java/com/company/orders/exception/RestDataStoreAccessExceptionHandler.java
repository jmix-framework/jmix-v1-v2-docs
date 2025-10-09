package com.company.orders.exception;

import io.jmix.flowui.Notifications;
import io.jmix.flowui.exception.AbstractUiExceptionHandler;
import io.jmix.restds.exception.RestDataStoreAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

// tag::exception-handler[]
@Component
public class RestDataStoreAccessExceptionHandler extends AbstractUiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestDataStoreAccessExceptionHandler.class);

    private final Notifications notifications;

    public RestDataStoreAccessExceptionHandler(Notifications notifications) {
        super(RestDataStoreAccessException.class.getName());
        this.notifications = notifications;
    }

    @Override
    protected void doHandle(@NonNull String className, @NonNull String message, @Nullable Throwable throwable) {
        log.warn(message);

        if (throwable instanceof RestDataStoreAccessException exception) {
            notifications.create("Connection error",
                            "'" + exception.getDataStoreName() + "' service is unavailable")
                    .withType(Notifications.Type.ERROR)
                    .show();
        }
    }
}
// end::exception-handler[]
