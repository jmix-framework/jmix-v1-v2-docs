package com.company.onboarding.app;

import com.company.onboarding.entity.Department;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DepartmentService {
    @Autowired
    private DataManager dataManager;

    public List<Department> loadDepartments(Condition condition, Sort sort, int firstResult, int maxResults) {
        return dataManager.load(Department.class)
                .condition(condition)
                .firstResult(firstResult)
                .maxResults(maxResults)
                .sort(sort)
                .list();
    }

    public Set<Object> saveEntities(EntitySet entitiesToSave, EntitySet entitiesToRemove) {
        return null;
    }

    public Department saveEntity(Department entity) {
        return dataManager.save(entity);
    }
}