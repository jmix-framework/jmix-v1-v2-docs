package com.company.onboarding.view.department;

import com.company.onboarding.app.DepartmentService;
import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionChangeType;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// tag::annotations[]
// common annotations
@ViewController("Department.list")
@ViewDescriptor("department-list-view.xml")
@Route(value = "departments", layout = MainView.class)
@DialogMode(width = "50em", height = "37.5em")
// list view annotations
@LookupComponent("departmentsTable")
@PrimaryLookupView(Department.class)
public class DepartmentListView extends StandardListView<Department> {
// end::annotations[]

    // tag::table[]
    @ViewComponent
    private DataGrid<Department> departmentsTable;

    // end::table[]

    // tag::data[]
    @ViewComponent
    private CollectionContainer<Department> departmentsDc;

    // end::data[]

    @Autowired
    private Metadata metadata;

    // tag::view-data[]
    @Subscribe
    public void onReady(final ReadyEvent event) {
        getViewData().loadAll();
    }
    // end::view-data[]

    // tag::find-by-name[]
    private Optional<Department> findByName(String name) {
        return departmentsDc.getItems().stream()
                .filter(department -> Objects.equals(department.getName(), name))
                .findFirst();
    }
    // end::find-by-name[]

    // tag::create-department[]
    private void createDepartment() {
        Department department = metadata.create(Department.class);
        department.setName("Operations");
        departmentsDc.getMutableItems().add(department);
    }
    // end::create-department[]

    // tag::select-first[]
    private void selectFirstRow() {
        departmentsTable.getSelectionModel()
                .select(departmentsDc.getItems().get(0));
    }
    // end::select-first[]

    // tag::collection-change[]
    @Subscribe(id = "departmentsDc", target = Target.DATA_CONTAINER)
    public void onDepartmentsDcCollectionChange(
            final CollectionContainer.CollectionChangeEvent<Department> event) {
        CollectionChangeType changeType = event.getChangeType(); // <1>
        Collection<? extends Department> changes = event.getChanges(); // <2>
        // ...
    }
    // end::collection-change[]

    // tag::delegate[]
    @Autowired
    private DepartmentService departmentService;

    @Install(to = "departmentsDl", target = Target.DATA_LOADER) // <1>
    private List<Department> departmentsDlLoadDelegate(final LoadContext<Department> loadContext) { // <2>
        LoadContext.Query query = loadContext.getQuery();
        return departmentService.loadDepartments( // <3>
                query.getCondition(),
                query.getSort(),
                query.getFirstResult(),
                query.getMaxResults()
        );
    }
    // end::delegate[]

    // tag::pre-load[]
    @Subscribe(id = "departmentsDl", target = Target.DATA_LOADER)
    public void onDepartmentsDlPreLoad(final CollectionLoader.PreLoadEvent<Department> event) {
        // some actions before loading
    }
    // end::pre-load[]

    // tag::post-load[]
    @Subscribe(id = "departmentsDl", target = Target.DATA_LOADER)
    public void onDepartmentsDlPostLoad(final CollectionLoader.PostLoadEvent<Department> event) {
        // some actions after loading
    }
    // end::post-load[]


    @Autowired
    private UiComponents uiComponents;
    // tag::dialog-header[]
    @Override
    protected void configureDialogWindowHeader(DialogWindowHeader header) {
        H3 title = uiComponents.create(H3.class);
        title.setText("- Opened in Dialog Mode!");
        header.add(title);
    }
    // end::dialog-header[]
    // tag::dialog-footer[]
    @Override
    protected void configureDialogWindowFooter(DialogWindowFooter footer) {
        JmixButton saveButton = uiComponents.create(JmixButton.class);
        saveButton.setText("Save");
        footer.add(saveButton);
    }
    // end::dialog-footer[]
}