package com.company.onboarding.view.dialogsandnotifications.dialogs;

import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.google.common.base.Strings;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.DataManager;
import io.jmix.core.metamodel.datatype.DatatypeRegistry;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.app.inputdialog.DialogActions;
import io.jmix.flowui.app.inputdialog.DialogOutcome;
import io.jmix.flowui.app.inputdialog.InputParameter;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.sidedialog.SideDialog;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static io.jmix.flowui.app.inputdialog.InputParameter.*;

@Route(value = "DialogsSampleView", layout = MainView.class)
@ViewController("DialogsSampleView")
@ViewDescriptor("dialogs-sample-view.xml")
public class DialogsSampleView extends StandardListView {
    // tag::inject-dialogs[]
    @Autowired
    private Dialogs dialogs;
    // end::inject-dialogs[]

    @Autowired
    private DatatypeRegistry datatypeRegistry;
    @Autowired
    private Notifications notifications;

    // tag::inject-message-bundle[]
    @ViewComponent
    private MessageBundle messageBundle;
    // end::inject-message-bundle[]

    // tag::messageDialog-1[]
    @Subscribe("messageDialogButton")
    public void onHelloButtonClick(ClickEvent<Button> event) {
        dialogs.createMessageDialog()
                .withHeader("Success") // <1>
                .withText("Invitation sent successfully") // <2>
                .open();
    }
    // end::messageDialog-1[]

    // tag::messageDialog-custom[]
    @Subscribe("customDialogButton")
    public void onSanitizeButtonClick(ClickEvent<Button> event) {
        dialogs.createMessageDialog()
                .withHeader("Information")
                .withText("This is a custom dialog")
                .withCloseOnOutsideClick(true)
                .withWidth("600px")
                .withHeight("200px")
                .open();
    }
    // end::messageDialog-custom[]

    // tag::messageDialog-html[]
    Html htmlContent = new Html("<p>Here starts a paragraph. A new line starts after this.<br />" +
            "<b>This text is bold.</b> <i>This text is italic.</i></p>");

    @Subscribe("htmlContentButton")
    public void onHtmlContentButtonClick(ClickEvent<Button> event) {
        dialogs.createMessageDialog()
                .withHeader("HTML Formatting")
                .withContent(htmlContent)
                .open();
    }
    // end::messageDialog-html[]

    // tag::optionsDialog-1[]
    @Subscribe("selectOptionButton")
    public void onSelectOptionButtonClick(ClickEvent<Button> event) {
        dialogs.createOptionDialog()
                .withHeader("Please confirm")
                .withText("Do you really want to add a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> addCustomer()), // <1>
                        new DialogAction(DialogAction.Type.NO)
                )
                .open();
    }
    // end::optionsDialog-1[]

    private void addCustomer() {

    }

    //     tag::inputDialog-standard-parameters[]
    @Subscribe("standardParametersButton")
    public void onStandardParametersButtonClick(ClickEvent<Button> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter values")
                .withParameters(
                        stringParameter("name").withLabel("Name").withRequired(true), // <1>
                        intParameter("amount").withLabel("Amount").withDefaultValue(1), // <2>
                        entityParameter("user", User.class).withLabel("User"), // <3>
                        enumParameter("status", OnboardingStatus.class).withLabel("Status") // <4>
                )
                .withActions(DialogActions.OK_CANCEL) // <5>
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) { // <6>
                        String name = closeEvent.getValue("name"); // <7>
                        int amount = closeEvent.getValue("amount");
                        User user = closeEvent.getValue("user");
                        OnboardingStatus status = closeEvent.getValue("status");
                        // process entered values...
                    }
                })
                .open();

    }
    // end::inputDialog-standard-parameters[]

    // tag::inputDialog-custom-parameters[]
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe("customParameterButton")
    public void onCustomParameterButtonClick(ClickEvent<Button> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter values")
                .withParameters(
                        stringParameter("name").withLabel("Name").withRequired(true),
                        intParameter("amount").withLabel("Amount").withDefaultValue(1),
                        parameter("user") // <1>
                                .withLabel("User")
                                .withField(() -> {
                                    EntityComboBox<User> field = uiComponents.create(EntityComboBox.class); // <2>
                                    field.setItems(dataManager.load(User.class).all().list()); // <3>
                                    field.setWidthFull();
                                    return field;
                                }),
                        enumParameter("status", OnboardingStatus.class).withLabel("Status")
                )
                .withActions(DialogActions.OK_CANCEL).withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String name = closeEvent.getValue("name");
                        int amount = closeEvent.getValue("amount");
                        User user = closeEvent.getValue("user");
                        OnboardingStatus status = closeEvent.getValue("status");
                        // process entered values...
                    }
                })
                .open();
    }
    // end::inputDialog-custom-parameters[]


    // tag::inputDialog-custom-validator[]
    @Subscribe("validationButton")
    public void onValidationButtonClick(ClickEvent<Button> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter at least one value")
                .withParameters(
                        stringParameter("name").withLabel("Name").withRequired(true),
                        entityParameter("User", User.class).withLabel("User")
                )
                .withValidator(context -> { // <1>
                    String name = context.getValue("name"); // <2>
                    User user = context.getValue("user");
                    if (Strings.isNullOrEmpty(name) && user == null) {
                        return ValidationErrors.of("Enter name or select a customer"); // <3>
                    }
                    return ValidationErrors.none();
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String name = closeEvent.getValue("name");
                        User user = closeEvent.getValue("user");
                        // process entered values...
                    }
                })
                .open();
    }
    // end::inputDialog-custom-validator[]

    // tag::backgroundTaskDialog[]
    @Subscribe(id = "backgroundTaskButton", subject = "singleClickListener")
    public void onBackgroundTaskClick(final ClickEvent<JmixButton> event) {
        dialogs.createBackgroundTaskDialog(new SampleTask(15, this, 10)) // <1>
                .withHeader("Background task running")
                .withText("Please wait until the task is complete")
                .withTotal(10) // <2>
                .withCancelAllowed(true) // <3>
                .open();
    }

    protected class SampleTask extends BackgroundTask<Integer, Void> {
        int count;

        public SampleTask(long timeoutSeconds, View<?> view, int count) {
            super(timeoutSeconds, view);
            this.count = count;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            for (int i = 1; i < count + 1; i++) {
                Thread.sleep(1000);
                taskLifeCycle.publish(i);
            }
            return null;
        }
    }
    // end::backgroundTaskDialog[]

    @Subscribe(id = "customParameterRequiredButton", subject = "clickListener")
    public void onCustomParameterRequiredButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter values")
                .withParameters(
                        // tag::required-custom-parameter[]
                        parameter("passedDate")
                                .withLabel("Date")
                                .withField(() -> {
                                    TypedDatePicker<LocalDate> datePicker = uiComponents.create(TypedDatePicker.class);
                                    datePicker.setDatatype(datatypeRegistry.get(LocalDate.class));
                                    datePicker.setRequired(true);
                                    return datePicker;
                                })
                        // end::required-custom-parameter[]
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        LocalDate passedDate = closeEvent.getValue("passedDate");
                        // process entered values...
                    }
                })
                .open();
    }

    @Subscribe(id = "customParamRequiredButton", subject = "clickListener")
    public void onCustomParamRequiredButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter values")
                .withParameters(
                        // tag::required-custom-parameter-2[]
                        InputParameter.parameter("passedDate")
                                .withLabel("Date")
                                .withRequired(true)
                                .withDatatype(datatypeRegistry.get(LocalDate.class))
                        // end::required-custom-parameter-2[]
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        LocalDate passedDate = closeEvent.getValue("passedDate");
                        // process entered values...
                    }
                })
                .open();
    }

    // tag::side-dialog[]

    @Subscribe(id = "sideDialogButton", subject = "clickListener")
    public void onSimpleSideDialogButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createSideDialog()
                .withHorizontalSize("14em")
                .withHeaderProvider(this::createHeader)
                .withContentComponents(createContent())
                .open();
    }

    private HorizontalLayout createHeader(SideDialog sideDialog) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.add(new H2(messageBundle.getMessage("sideDialogHeader")));
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        Button closeButton = new Button(LumoIcon.CROSS.create(), event -> sideDialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);
        header.add(closeButton);

        return header;
    }

    private Component createContent() {
        VirtualList<String> list = new VirtualList<>();
        list.setWidthFull();
        list.setItems("Item 1", "Item 2", "Item 3");
        list.setRenderer(new ComponentRenderer<>((item -> {
            Card root = new Card();
            root.setTitle(item);
            root.setHeaderSuffix(LumoIcon.CROSS.create());
            root.add(messageBundle.getMessage("activityDescription"));
            root.addThemeVariants(CardVariant.LUMO_HORIZONTAL);
            root.addClassName(LumoUtility.Margin.Bottom.MEDIUM);
            return root;
        })));
        return list;
    }
    // end::side-dialog[]

    // tag::basic-config[]
    @Subscribe(id = "configDialogButton", subject = "clickListener")
    public void onConfigDialogButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createMessageDialog()
                .withHeader("Information")
                .withWidth("600px")
                .withHeight("200px")
                .withTop("100px")
                .open();
    }

    // end::basic-config[]
    // tag::withDraggedListener[]
    @Subscribe(id = "dragDialogButton", subject = "clickListener")
    public void onDragDialogButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createMessageDialog()
                .withHeader("Drag this dialog")
                .withDraggedListener(dialogDraggedEvent -> {
                    String left = dialogDraggedEvent.getLeft();
                    String top = dialogDraggedEvent.getTop();

                    try {
                        int leftValue = Integer.parseInt(left.replace("px", ""));
                        int topValue = Integer.parseInt(top.replace("px", ""));

                        if (leftValue < 300 && topValue < 200) {
                            notifications.create("Dialog is in the upper left corner").show();
                        } else if (leftValue > 800 && topValue > 500) {
                            notifications.create("Dialog is in the lower right corner").show();
                        } else {
                            notifications.create("Dialog is in a neutral area").show();
                        }
                    } catch (NumberFormatException e) {
                        notifications.create("Error: Invalid coordinates")
                                .withType(Notifications.Type.WARNING)
                                .show();
                    }
                })
                .open();
    }

    // end::withDraggedListener[]
    // tag::withResizeListener[]
    @Subscribe(id = "resizeDialogButton", subject = "clickListener")
    public void onResizeDialogButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createMessageDialog()
                .withHeader("Resize this dialog")
                .withResizable(true)
                .withResizeListener(dialogResizeEvent -> {
                    String width = dialogResizeEvent.getWidth();
                    String height = dialogResizeEvent.getHeight();
                    try {
                        int widthValue = Integer.parseInt(width);
                        int heightValue = Integer.parseInt(height);

                        if (widthValue < 400 || heightValue < 300) {
                            notifications.create("Minimum size: 400×300")
                                    .withType(Notifications.Type.WARNING)
                                    .show();
                        }
                    } catch (NumberFormatException e) {
                        notifications.create("Error: Invalid coordinates")
                                .withType(Notifications.Type.WARNING)
                                .show();
                    }
                })
                .open();
    }
// end::withResizeListener[]
}