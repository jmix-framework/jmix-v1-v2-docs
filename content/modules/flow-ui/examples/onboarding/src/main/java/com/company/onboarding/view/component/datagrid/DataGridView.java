package com.company.onboarding.view.component.datagrid;


// tag::import-user[]
import com.company.onboarding.entity.User;
// end::import-user[]
import com.company.onboarding.entity.UserStep;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
// tag::import-grid[]
import com.vaadin.flow.component.grid.Grid;
// end::import-grid[]
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
// tag::import-image[]
import com.vaadin.flow.component.html.Image;
// end::import-image[]
// tag::import-span[]
import com.vaadin.flow.component.html.Span;
// end::import-span[]
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
// tag::import-download-handler[]
import com.vaadin.flow.server.streams.DownloadHandler;
// end::import-download-handler[]
// tag::import-download-response[]
import com.vaadin.flow.server.streams.DownloadResponse;
// end::import-download-response[]
// tag::import-input-stream-download-handler[]
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
// end::import-input-stream-download-handler[]
// tag::import-file-ref[]
import io.jmix.core.FileRef;
// end::import-file-ref[]
// tag::import-file-storage-locator[]
import io.jmix.core.FileStorageLocator;
// end::import-file-storage-locator[]
// tag::import-ui-components[]
import io.jmix.flowui.UiComponents;
// end::import-ui-components[]
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.JmixComboBox;
// tag::import-data-grid[]
import io.jmix.flowui.component.grid.DataGrid;
// end::import-data-grid[]

import io.jmix.flowui.component.grid.editor.DataGridEditor;
import io.jmix.flowui.data.grid.ContainerDataGridItems;

import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
// tag::import-input-stream[]
import java.io.InputStream;
// end::import-input-stream[]
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;
// tag::some-code[]

// class declaration and annotations omitted

// end::some-code[]
@Route(value = "DataGridView", layout = MainView.class)
@ViewController("DataGridView")
@ViewDescriptor("data-grid-view.xml")
public class DataGridView extends StandardView {

    // tag::dataGrid[]
    @ViewComponent
    private DataGrid<User> dataGrid;

    // end::dataGrid[]
    // tag::usersDc[]
    @ViewComponent
    private CollectionContainer<User> usersDc;

    // end::usersDc[]
    // tag::usersDtGr[]
    @ViewComponent
    private DataGrid<User> usersDtGr;

    // end::usersDtGr[]
    // tag::uiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::uiComponents[]
    // tag::fileStorage[]
    @Autowired
    private FileStorageLocator fileStorageLocator;

    // end::fileStorage[]
    // tag::editableUserTable[]
    @ViewComponent
    private DataGrid<User> editableUserTable;

    // end::editableUserTable[]
    // tag::dblClickTable[]
    @ViewComponent
    private DataGrid<User> dblClickTable;

    // tag::dynamicContentHandler[]
    @ViewComponent
    private GridMenuItem<Object> emailItem;

    // end::dynamicContentHandler[]

    // end::dblClickTable[]
    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        dataGrid.setItems(new ContainerDataGridItems<>(usersDc));
        // end::setItems[]
        // tag::addJoiningDate[]
        usersDtGr.addColumn(new LocalDateRenderer<>(
                        User::getJoiningDate,
                        () -> DateTimeFormatter.ofLocalizedDate(
                                FormatStyle.MEDIUM)))
                .setHeader("Joining date");
        // end::addJoiningDate[]
        // tag::addComponentColumn[]
        Grid.Column<User> pictureColumn = usersDtGr.addComponentColumn(user -> { // <1>
            FileRef fileRef = user.getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class); // <2>
                image.setWidth("30px");
                image.setHeight("30px");
                image.setClassName("user-picture");
                InputStreamDownloadHandler handler = // <3>
                        DownloadHandler.fromInputStream(e -> {
                            InputStream inputStream = fileStorageLocator.getByName(
                                    fileRef.getStorageName()).openStream(fileRef);
                            return new DownloadResponse(
                                    inputStream, fileRef.getFileName(), fileRef.getContentType(), -1);
                        });
                image.setSrc(handler); // <4>

                return image; // <5>
            } else {
                return new Span();
            }
        })
                .setHeader("Picture")
                .setAutoWidth(true)
                .setFlexGrow(0);
        usersDtGr.setColumnPosition(pictureColumn,0);
        // end::addComponentColumn[]
        // tag::DataGridEditor[]
        DataGridEditor<User> editor = editableUserTable.getEditor(); // <1>

        editor.setColumnEditorComponent("timeZoneId", generationContext -> {
            //noinspection unchecked
            JmixComboBox<String> timeZoneField = uiComponents.create(JmixComboBox.class); // <2>
            timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
            timeZoneField.setValueSource(generationContext.getValueSourceProvider().getValueSource("timeZoneId"));
            timeZoneField.setWidthFull();
            timeZoneField.setClearButtonVisible(true);
            timeZoneField.setRequired(true);
            //noinspection unchecked,rawtypes
            timeZoneField.setStatusChangeHandler(((Consumer) generationContext.getStatusHandler())); // <3>

            return timeZoneField; // <4>
        });
        // end::DataGridEditor[]
        // tag::addItemDoubleClickListener[]
        DataGridEditor<User> tableEditor = dblClickTable.getEditor();
        dblClickTable.addItemDoubleClickListener(e -> {
            tableEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });
        // end::addItemDoubleClickListener[]
        // tag::onInit[]
    }
    // end::onInit[]
    @Supply(to = "usersDtGr.picture", subject = "renderer")
    private Renderer<User> usersDtGrPictureRenderer() {
        return new ComponentRenderer<>(user -> {
            FileRef fileRef = user.getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                InputStreamDownloadHandler handler =
                        DownloadHandler.fromInputStream(event -> {
                            InputStream inputStream = fileStorageLocator.getByName(
                                    fileRef.getStorageName()).openStream(fileRef);
                            return new DownloadResponse(
                                    inputStream, fileRef.getFileName(), fileRef.getContentType(), -1);
                        });
                image.setSrc(handler);
                image.setClassName("user-picture");

                return image;
            } else {
                return null;
            }
        });
    }

    // tag::textRenderer[]
    @Supply(to = "userStepsDataGrid.status", subject = "renderer")
    private Renderer<UserStep> userStepsDataGridStatusRenderer() {
        return new TextRenderer<>(userStep ->
                isOverdue(userStep) ? "Overdue!" : "");
    }
    // end::textRenderer[]

    private boolean isOverdue(UserStep userStep) {
        return true;
    }

    // tag::ComponentRenderer[]
    @Supply(to = "userStepsDataGrid.completed", subject = "renderer")
    private Renderer<UserStep> userStepsDataGridCompletedRenderer() {
        return new ComponentRenderer<>(userStep -> {
            JmixCheckbox checkbox = uiComponents.create(JmixCheckbox.class);
            checkbox.setValue(userStep.getCompletedDate() != null);
            checkbox.addValueChangeListener(e -> {
                // ...
            });
            return checkbox;
        });
    }
    // end::ComponentRenderer[]


    // tag::dynamicContentHandler[]
    @Install(to = "contextMenu", subject = "dynamicContentHandler")
    private boolean contextMenuDynamicContentHandler(final User user) {
        if (user == null) {
            return false;
        }
            emailItem.setText("Email: " + user.getEmail());
        return true;
    }
    // end::dynamicContentHandler[]
}
