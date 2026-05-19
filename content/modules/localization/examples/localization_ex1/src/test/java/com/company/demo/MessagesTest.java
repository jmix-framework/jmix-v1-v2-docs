package com.company.demo;

import com.company.demo.entity.Status;
// tag::import-user[]
import com.company.demo.entity.User;
// end::import-user[]
// tag::import-message-tools[]
import io.jmix.core.MessageTools;
// end::import-message-tools[]
// tag::import-metadata[]
import io.jmix.core.Metadata;
// end::import-metadata[]
// tag::import-messages[]
import io.jmix.core.Messages;
// end::import-messages[]
// tag::import-meta-class[]
import io.jmix.core.metamodel.model.MetaClass;
// end::import-meta-class[]
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::class-declaration[]

// class declaration and annotations omitted

// end::class-declaration[]
@SpringBootTest
public class MessagesTest {

    // tag::autowired-messages[]
    @Autowired
    private Messages messages;

    // end::autowired-messages[]
    // tag::autowired-message-tools[]
    @Autowired
    private MessageTools messageTools;

    // end::autowired-message-tools[]

    // tag::autowired-metadata[]
    @Autowired
    private Metadata metadata;

    // end::autowired-metadata[]
    @Autowired
    private SystemAuthenticator authenticator;

    @Test
    void test() {
        authenticator.withSystem(() -> {
            // tag::using-messages-1[]
    String message1 = messages.getMessage("com.company.demo/someNotification"); // <1>

    String message2 = messages.getMessage("com.company.demo", "someNotification"); // <2>

    String message3 = messages.getMessage(getClass(), "someNotification"); // <3>
            // end::using-messages-1[]

            assertEquals("Something has happened", message1);
            assertEquals("Something has happened", message2);
            assertEquals("Something has happened", message3);

            return null;
        });
    }

    @Test
    void testMessageWithoutGroup() {
        authenticator.withSystem(() -> {
            // tag::using-messages-3[]
            String message = messages.getMessage("messageWithoutGroup");
            // end::using-messages-3[]

            assertEquals("Message without a group", message);

            return null;
        });
    }

    @Test
    void testEnum() {
        authenticator.withSystem(() -> {
            // tag::using-messages-enum[]
            String message = messages.getMessage(Status.ACTIVE);
            // end::using-messages-enum[]

            assertEquals("Active", message);

            return null;
        });
    }

    @Test
    void testMessageTools() {
        authenticator.withSystem(() -> {
            // tag::message-tools[]
    MetaClass userMetaClass = metadata.getClass(User.class);

    String entityCaption = messageTools.getEntityCaption(userMetaClass);

    String propertyCaption = messageTools.getPropertyCaption(userMetaClass, "username");
            // end::message-tools[]

            assertEquals("User", entityCaption);
            assertEquals("Username", propertyCaption);

            return null;
        });
    }
}
