package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.repository.DepartmentRepository;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.repository.JmixDataRepositoryContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@Route(value = "departments3", layout = MainView.class)
@ViewController(id = "Department.list3")
@ViewDescriptor(path = "department-list-view-3.xml")
@LookupComponent("departmentsDataGrid")
@DialogMode(width = "64em")
public class DepartmentListView3 extends StandardListView<Department> {

    // tag::repository[]
    @Autowired
    private DepartmentRepository repository;

    // end::repository[]

    // tag::delegate[]
    @Install(to = "departmentsDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
    private List<Department> departmentsDlLoadDelegate(
            final Pageable pageable, final JmixDataRepositoryContext context) {
        return repository.findAll(pageable, context).getContent();
    }
    // end::delegate[]

    @Install(to = "departmentsDataGrid.removeAction", subject = "delegate")
    private void departmentsDataGridRemoveDelegate(final Collection<Department> collection) {
        repository.deleteAll(collection);
    }

    // tag::totalCountByRepositoryDelegate[]
    @Install(to = "pagination", subject = "totalCountByRepositoryDelegate")
    private Long paginationTotalCountByRepositoryDelegate(
            final JmixDataRepositoryContext context) {
        return repository.count(context);
    }
    // end::totalCountByRepositoryDelegate[]
}