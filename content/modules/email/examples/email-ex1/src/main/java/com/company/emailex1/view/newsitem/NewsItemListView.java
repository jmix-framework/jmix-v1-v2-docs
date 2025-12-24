package com.company.emailex1.view.newsitem;

import com.company.emailex1.entity.NewsItem;
import com.company.emailex1.view.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.Route;
import io.jmix.core.Resources;
import io.jmix.email.*;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Route(value = "news-items", layout = MainView.class)
@ViewController(id = "NewsItem.list")
@ViewDescriptor(path = "news-item-list-view.xml")
@LookupComponent("newsItemsDataGrid")
@DialogMode(width = "64em")
public class NewsItemListView extends StandardListView<NewsItem> {

    private static final Logger log = LoggerFactory.getLogger(NewsItemListView.class);
    @ViewComponent
    private DataGrid<NewsItem> newsItemsDataGrid;
    @Autowired
    private Notifications notifications;
    // tag::dialogs[]
    @Autowired
    private Dialogs dialogs; // <1>

    // end::dialogs[]
    // tag::emailer[]
    @Autowired
    private Emailer emailer; // <1>

    // end::emailer[]
    // tag::resources[]
    @Autowired
    private Resources resources; // <2>

    // end::resources[]

    // tag::send-email-handler[]
    @Subscribe("newsItemsDataGrid.sendEmailAction")
    public void onNewsItemsDataGridSendEmailAction(final ActionPerformedEvent event) {
        NewsItem newsItem = newsItemsDataGrid.getSingleSelectedItem();

        dialogs.createOptionDialog()
                .withHeader("Sending email")
                .withText("Emails will be queued for sending. Continue?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES) {
                            @Override
                            public void actionPerform(Component component) {
                                try {
                                    sendNewsByEmail(newsItem);
                                } catch (IOException e) {
                                    log.error("Error sending email");
                                    notifications.create("Error sending email").show();
                                }
                            }
                        },
                        new DialogAction(DialogAction.Type.NO)
                )
                .open();
    }
    // end::send-email-handler[]

    // tag::send-news-by-mail[]
    private void sendNewsByEmail(NewsItem newsItem) throws IOException {
        InputStream resourceAsStream = resources.getResourceAsStream("/META-INF/resources/icons/icon.png"); // <3>
        byte[] bytes = IOUtils.toByteArray(resourceAsStream);
        EmailAttachment emailAttachment = new EmailAttachment(bytes, "logo.png", "logoId"); // <4>

        final EmailInfo emailInfo = EmailInfoBuilder.create() // <5>
                .setAddresses("john.doe@company.com,jane.doe@company.com")
                .setSubject(newsItem.getSubject())
                .setFrom(null) // <6>
                .setBodyContentType("text/plain; charset=UTF-8")
                .setBody(newsItem.getContent())
                .setAttachments(emailAttachment)
                .setImportant(false)
                .build();
        emailer.sendEmailAsync(emailInfo); // <7>
    }
    // end::send-news-by-mail[]

    private EmailAttachment createEmailAttachment(String pathToResources) throws IOException {
        InputStream resourceAsStream = resources.getResourceAsStream(pathToResources);
        byte[] bytes = IOUtils.toByteArray(resourceAsStream);
        // tag::create-attachment[]
        EmailAttachment emailAttachment = new EmailAttachment(bytes, "logo.png", "logoId");
        // end::create-attachment[]
        return emailAttachment;
    }

    // tag::email-info1[]
    private void sendByEmailInfo() throws EmailException {
        // end::email-info1[]
        // tag::text-attachment[]
        String attachment = "<html><body><h1>Content of attachment</h1></body></html>";
        EmailAttachment emailAttachment = EmailAttachment.createTextAttachment(attachment, StandardCharsets.UTF_8.name(), "attachment.html");
        // end::text-attachment[]
        // tag::email-info2[]
        EmailInfo emailInfo = EmailInfoBuilder.create("john.doe@company.com",
                        "Email subject", "Email body")
                .build(); // <2>
        emailer.sendEmail(emailInfo); // <3>
    }
    // end::email-info2[]

}