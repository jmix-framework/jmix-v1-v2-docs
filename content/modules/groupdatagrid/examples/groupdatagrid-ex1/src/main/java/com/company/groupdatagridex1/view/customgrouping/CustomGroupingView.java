package com.company.groupdatagridex1.view.customgrouping;


import com.company.groupdatagridex1.entity.Customer;
import com.company.groupdatagridex1.view.main.MainView;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.groupgrid.data.GroupDataGridItems;
import io.jmix.flowui.view.*;
import io.jmix.groupgridflowui.component.GroupDataGrid;
import io.jmix.groupgridflowui.data.BaseGroupPropertyDescriptor;

import java.util.List;

@Route(value = "custom-grouping-view", layout = MainView.class)
@ViewController(id = "CustomGroupingView")
@ViewDescriptor(path = "custom-grouping-view.xml")
public class CustomGroupingView extends StandardView {

    // tag::custom-grouping[]
    @ViewComponent
    private GroupDataGrid<Customer> customersGroupDataGrid;

    @Subscribe
    public void onInit(InitEvent event) {
        GroupDataGridItems<Customer> items = customersGroupDataGrid.getItems();

        if (items != null) {
            items.addGroupPropertyDescriptor( // <1>
                    new BaseGroupPropertyDescriptor<Customer>("fullName",
                            context -> context.getItem().getFirstName() + " " + context.getItem().getLastName())
                            .withSortProperties(List.of("firstName", "lastName")));

            customersGroupDataGrid.groupByKeys("fullName"); // <2>
        }
    }
    // end::custom-grouping[]

    // tag::renderer[]
    @Supply(to = "customersGroupDataGrid.fullName", subject = "renderer") // <3>
    protected Renderer<Customer> supplyRendererToFullNameColumn() {
        return new TextRenderer<>(item -> item.getFirstName() + " " + item.getLastName());
    }
    // end::renderer[]
}