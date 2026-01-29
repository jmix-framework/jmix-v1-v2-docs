package com.company.bpmex1.app;

import io.jmix.bpm.data.form.FormData;
import io.jmix.bpm.processform.ProcessFormDataExtractor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


// tag::form-data-controller[]
@RestController
@RequestMapping("rest/bpm/process-form-data")
public class FormDataController {

    @Autowired
    private ProcessFormDataExtractor processFormDataExtractor;

    @GetMapping(produces = "application/json")
    public FormData getTaskFormData(
            @RequestParam(required = false) String taskId,
            @RequestParam(required = false) String processDefinitionId) {

        if (Strings.isEmpty(taskId) && Strings.isEmpty(processDefinitionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The taskId or processDefinitionId parameter has to be provided");
        }

        if (!Strings.isEmpty(taskId) && !Strings.isEmpty(processDefinitionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request cannot contain both taskId and processDefinitionId.");
        }

        if (Strings.isEmpty(taskId)) {
            return processFormDataExtractor.getStartFormData(processDefinitionId); // <1>
        } else {
            return processFormDataExtractor.getTaskFormData(taskId); // <2>
        }
    }
}
// end::form-data-controller[]