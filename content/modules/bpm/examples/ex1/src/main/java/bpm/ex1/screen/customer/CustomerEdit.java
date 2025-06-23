package bpm.ex1.screen.customer;

import io.jmix.bpm.service.BpmTaskService;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import bpm.ex1.entity.Customer;
import org.flowable.engine.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@UiController("smpl_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {

    // tag::runtime-service[]
    @Autowired
    private RuntimeService runtimeService;

    // end::runtime-service[]

    // tag::inject-service[]
    @Autowired
    private BpmTaskService bpmTaskService;
    // end::inject-service[]

    public void showServices() {
        // tag::services[]
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        TaskService taskService = processEngine.getTaskService();
        // end::services[]
    }

    public void showBpmTaskService(){
        //tag::get-service[]
        BpmTaskService bpmTaskService = (BpmTaskService) ProcessEngines
                .getDefaultProcessEngine()
                .getTaskService();
        //end::get-service[]
    }

    // tag::commit[]
    @Subscribe("commitAndCloseBtn")
    public void onCommitAndCloseBtnClick(Button.ClickEvent event) {
        Customer customer = getEditedEntity();
        String name = customer.getName();
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customer); // <1>
        params.put("name", name); // <2>
        runtimeService.startProcessInstanceByKey( // <3>
                "new-customer",
                params);
    }
    // end::commit[]

}