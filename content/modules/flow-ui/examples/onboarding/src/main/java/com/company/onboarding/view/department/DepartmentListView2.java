package com.company.onboarding.view.department;

import com.company.onboarding.app.CustomCollectionContainerSorter;
import com.company.onboarding.app.DepartmentService;
import com.company.onboarding.entity.Department;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.Copier;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.SaveContext;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.Sorter;
import io.jmix.flowui.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route(value = "departments2", layout = MainView.class)
@ViewController("Department.list2")
@ViewDescriptor("department-list-view-2.xml")
@LookupComponent("departmentsTable")
@DialogMode(width = "50em", height = "37.5em")
// tag::custom-sort[]
public class DepartmentListView2 extends StandardListView<Department> {

    // tag::data-context[]
    @ViewComponent
    private CollectionContainer<Department> departmentsDc;
    // end::data-context[]

    // tag::condition[]
    @ViewComponent
    private CollectionLoader<Department> departmentsDl;
    // end::condition[]

    @Autowired
    private BeanFactory beanFactory;

    @Subscribe
    public void onInit(final InitEvent event) {
        Sorter sorter = new CustomCollectionContainerSorter(departmentsDc, departmentsDl, beanFactory);
        departmentsDc.setSorter(sorter);
    }
    // end::custom-sort[]

    private static final Logger log = LoggerFactory.getLogger(DepartmentListView2.class);

    @Autowired
    private DialogWindows dialogWindows;

    // tag::condition[]

    @Subscribe("nameFilterField")
    public void onNameFilterFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        departmentsDl.setParameter("name", "(?i)%" + event.getValue() + "%");
        departmentsDl.load();
    }

    @Subscribe("hrManagerFilterField")
    public void onHrManagerFilterFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<User>, User> event) {
        departmentsDl.setParameter("hrManager", event.getValue());
        departmentsDl.load();
    }
    // end::condition[]

    // tag::data-context[]

    @Autowired
    private DataManager dataManager;

    // tag::data-context-inject[]
    // tag::save-custom[]
    @ViewComponent
    private DataContext dataContext;
    // end::data-context-inject[]
    // end::save-custom[]

    private void loadDepartment(Id<Department> departmentId) {
        Department department = dataManager.load(departmentId).one();
        Department trackedDepartment = dataContext.merge(department);
        departmentsDc.getMutableItems().add(trackedDepartment);
    }
    // end::data-context[]

    // tag::sample-method[]
    private void sampleMethod(View sampleView) {
        DataContext dataContext = ViewControllerUtils.getViewData(sampleView).getDataContext();
        // ...
    }
    // end::sample-method[]

    // tag::department-edit[]
    private void detailViewWithCurrentDataContextAsParent() {
        DialogWindow<DepartmentDetailView> dialogWindow = dialogWindows.detail(this, Department.class)
                .withViewClass(DepartmentDetailView.class)
                .withParentDataContext(dataContext)
                .build();
        dialogWindow.open();
    }
    // end::department-edit[]

    // tag::change-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onChange(final DataContext.ChangeEvent event) {
        log.debug("Changed entity: " + event.getEntity());
    }
    // end::change-event[]

    private Department department;

    // tag::pre-save-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(final DataContext.PreSaveEvent event) {
        event.getModifiedInstances().add(department);
    }
    // end::pre-save-event[]

    // tag::prevent-save[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave2(DataContext.PreSaveEvent event) {
        if (checkSomeCondition()) {
            event.preventSave();
        }
    }
    // end::prevent-save[]

    private boolean checkSomeCondition() {
        return true;
    }

    // tag::post-save-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostSave(final DataContext.PostSaveEvent event) {
        log.debug("Saved: " + event.getSavedInstances());
    }
    // end::post-save-event[]

    // tag::save-custom[]
    // tag::save-delegate[]
    @Autowired
    private DepartmentService departmentService;
    // end::save-custom[]

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        return departmentService.saveEntities(
                saveContext.getEntitiesToSave(),
                saveContext.getEntitiesToRemove());
    }
    // end::save-delegate[]

    // tag::save-custom[]
    @Autowired
    private Copier copier;

    private void saveToCustomService(Department entity) {
        Department entityCopy = copier.copy(entity); // <1>

        Department savedEntity = departmentService.saveEntity(entityCopy); // <2>

        Department ignored = dataContext.merge(savedEntity);// <3>
    }
    // end::save-custom[]

}