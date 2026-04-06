package com.company.onboarding.view.sample;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.department.DepartmentDetailView;
import com.company.onboarding.view.department.DepartmentListView;
import com.company.onboarding.view.fancymessage.FancyMessageView;
import com.company.onboarding.view.main.MainView;
import com.company.onboarding.view.myonboarding.MyOnboardingView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("SampleView")
@ViewDescriptor("sample-view.xml")
@Route(value = "sample", layout = MainView.class)
@DialogMode(width = "AUTO", height = "AUTO")
public class SampleView extends StandardView {

    @Autowired
    protected DataManager dataManager;
    // tag::viewNavigators[]
    @Autowired
    private ViewNavigators viewNavigators;

    // end::viewNavigators[]

    // tag::dialogWindows[]
    @Autowired
    private DialogWindows dialogWindows;

    // end::dialogWindows[]

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(InitEvent event) {
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
    }

    @Subscribe("navigateToViewBtn")
    protected void onNavigateToViewBtnClick(ClickEvent<Button> event) {
        navigateToViewThenBack();
    }

    // tag::navigateToView[]
    private void navigateToView() {
        viewNavigators.view(this, MyOnboardingView.class).navigate();
    }
    // end::navigateToView[]

    // tag::navigateToViewThenBack[]
    private void navigateToViewThenBack() {
        viewNavigators.view(this, MyOnboardingView.class)
                .withBackwardNavigation(true)
                .navigate();
    }
    // end::navigateToViewThenBack[]

    @Subscribe("navigateToListViewBtn")
    protected void onNavigateToListViewBtnClick(ClickEvent<Button> event) {
        navigateToListView();
    }

    // tag::navigateToListView[]
    private void navigateToListView() {
        viewNavigators.listView(this, Department.class).navigate();
    }
    // end::navigateToListView[]

    // tag::navigateToListViewWithClass[]
    private void navigateToListViewWithClass() {
        viewNavigators.listView(this, Department.class)
                .withViewClass(DepartmentListView.class)
                .navigate();
    }
    // end::navigateToListViewWithClass[]

    @Subscribe("navigateToDetailViewBtn")
    protected void onNavigateToDetailViewBtnClick(ClickEvent<Button> event) {
//        navigateToCreateEntity();
        Department department = dataManager.load(Department.class).all().one();
        navigateToEditEntity(department);
    }

    // tag::navigateToCreateEntity[]
    private void navigateToCreateEntity() {
        viewNavigators.detailView(this, Department.class)
                .newEntity()
                .navigate();
    }
    // end::navigateToCreateEntity[]

    // tag::navigateToEditEntity[]
    private void navigateToEditEntity(Department entity) {
        viewNavigators.detailView(this, Department.class)
                .editEntity(entity)
                .navigate();
    }
    // end::navigateToEditEntity[]

    @Subscribe("openViewBtn")
    protected void onOpenViewBtnClick(ClickEvent<Button> event) {
        openViewWithResults();
    }

    // tag::openView[]
    private void openView() {
        dialogWindows.view(this, MyOnboardingView.class).open();
    }
    // end::openView[]

    // tag::openViewWithParams[]
    private void openViewWithParams(String username) {
        DialogWindow<MyOnboardingView> window =
                dialogWindows.view(this, MyOnboardingView.class).build();
        window.getView().setUsername(username);
        window.open();
    }
    // end::openViewWithParams[]

    // tag::openViewWithParamsAndResults[]
    private void openViewWithParamsAndResults(String username) {
        DialogWindow<MyOnboardingView> window =
                dialogWindows.view(this, MyOnboardingView.class).build();
        window.getView().setUsername(username);
        window.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                // ...
            }
        });
        window.open();
    }
    // end::openViewWithParamsAndResults[]

    // tag::openViewWithResults[]
    private void openViewWithResults() {
        dialogWindows.view(this, MyOnboardingView.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        // ...
                    }
                })
                .open();
    }
    // end::openViewWithResults[]


    @Subscribe("openLookupView")
    protected void onOpenLookupViewClick(ClickEvent<Button> event) {
        openLookupView();
    }

    // tag::openLookupView[]
    private void openLookupView() {
        dialogWindows.lookup(this, Department.class)
                .withSelectHandler(departments -> {
                    // ...
                })
                .open();
    }
    // end::openLookupView[]

    @Subscribe("openDetailView")
    protected void onOpenDetailViewClick(ClickEvent<Button> event) {
        openDetailViewToCreateEntity();
    }

    // tag::openDetailViewToCreateEntity[]
    private void openDetailViewToCreateEntity() {
        dialogWindows.detail(this, Department.class)
                .withViewClass(DepartmentDetailView.class)
                .newEntity()
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        Department department = afterCloseEvent.getView().getEditedEntity();
                        // ...
                    }
                })
                .open();
    }
    // end::openDetailViewToCreateEntity[]

    // tag::openDetailViewToEditEntity[]
    private void openDetailViewToEditEntity(Department department) {
        dialogWindows.detail(this, Department.class)
                .withViewClass(DepartmentDetailView.class)
                .editEntity(department)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        Department editedDepartment = afterCloseEvent.getView().getEditedEntity();
                        // ...
                    }
                })
                .open();
    }
    // end::openDetailViewToEditEntity[]


    @Subscribe("withAfterNavigationHandlerBtn")
    public void onWithAfterNavigationHandlerBtnClick(final ClickEvent<JmixButton> event) {
        navigateToViewWithAfterNavigationHandler();
    }

    // tag::navigateToViewWithAfterNavigationHandler[]
    private void navigateToViewWithAfterNavigationHandler() {
        viewNavigators.view(this, FancyMessageView.class)
                .withAfterNavigationHandler(afterViewNavigationEvent -> {
                    FancyMessageView view = afterViewNavigationEvent.getView();
                    view.setMessage("Hello World!");
                })
                .navigate();
    }
    // end::navigateToViewWithAfterNavigationHandler[]

    @Subscribe("queryParametersBtn")
    public void onQueryParametersBtnClick(final ClickEvent<JmixButton> event) {
        navigateToViewWithQueryParameters();
    }

    // tag::navigateToViewWithQueryParameters[]
    private void navigateToViewWithQueryParameters() {
        viewNavigators.view(this, FancyMessageView.class)
                .withQueryParameters(QueryParameters.of("message", "Hello World!"))
                .navigate();
    }
    // end::navigateToViewWithQueryParameters[]

    @Subscribe(id = "openDialogWindow", subject = "clickListener")
    public void onOpenDialogWindowClick(final ClickEvent<JmixButton> event) {
        DialogWindow<DepartmentListView> window =
                dialogWindows.view(this, DepartmentListView.class).build();
        window.open();
    }

    @Subscribe(id = "dialogSizeWindow", subject = "clickListener")
    public void onDialogSizeWindowClick(final ClickEvent<JmixButton> event) {
        dialogWindows.view(this, MyOnboardingView.class).open();
    }

    @Subscribe(id = "dialogSizeWindow2", subject = "clickListener")
    public void onDialogSizeWindow2Click(final ClickEvent<JmixButton> event) {
        openViewWithSize();
    }

    // tag::openViewWithSize[]
    private void openViewWithSize() {
        DialogWindow<MyOnboardingView> window =
                dialogWindows.view(this, MyOnboardingView.class).build();
        window.setWidth("40em");
        window.setHeight("30em");
        window.open();
    }
    // end::openViewWithSize[]
}