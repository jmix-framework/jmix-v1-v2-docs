package com.company.onboarding.listener;

import com.company.onboarding.entity.Department;
import com.company.onboarding.entity.Step;
import com.company.onboarding.entity.User;
import com.company.onboarding.entity.UserStep;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.SaveContext;
import io.jmix.core.security.Authenticated;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DemoDataInitializer {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorage fileStorage;

    @EventListener
    @Authenticated
    public void onApplicationStarted(ApplicationStartedEvent event) {
        if (dataManager.load(Step.class).all().maxResults(1).list().size() > 0) {
            return;
        }
        List<Step> steps = initSteps();
        List<Department> departments = initDepartments();
        List<User> users = initUsers(steps, departments);
        assignRoles(users);
    }

    private List<Step> initSteps() {
        Step step;
        ArrayList<Step> list = new ArrayList<>();

        step = dataManager.create(Step.class);
        step.setName("Safety briefing");
        step.setDuration(1);
        step.setSortValue(10);
        list.add(dataManager.save(step));

        step = dataManager.create(Step.class);
        step.setName("Fill in profile");
        step.setDuration(1);
        step.setSortValue(20);
        list.add(dataManager.save(step));

        step = dataManager.create(Step.class);
        step.setName("Check all functions");
        step.setDuration(2);
        step.setSortValue(30);
        list.add(dataManager.save(step));

        step = dataManager.create(Step.class);
        step.setName("Information security training");
        step.setDuration(3);
        step.setSortValue(40);
        list.add(dataManager.save(step));

        step = dataManager.create(Step.class);
        step.setName("Internal procedures studying");
        step.setDuration(5);
        step.setSortValue(50);
        list.add(dataManager.save(step));

        return list;
    }

    private List<Department> initDepartments() {
        Department department;
        List<Department> list = new ArrayList<>();

        department = dataManager.create(Department.class);
        department.setName("Human Resources");
        list.add(dataManager.save(department));

        department = dataManager.create(Department.class);
        department.setName("Marketing");
        list.add(dataManager.save(department));

        department = dataManager.create(Department.class);
        department.setName("Operations");
        list.add(dataManager.save(department));

        department = dataManager.create(Department.class);
        department.setName("Finance");
        list.add(dataManager.save(department));

        return list;
    }

    private List<User> initUsers(List<Step> steps, List<Department> departments) {
        User user;
        SaveContext saveContext;
        List<User> list = new ArrayList<>();

        saveContext = new SaveContext();
        user = dataManager.create(User.class);
        user.setUsername("alice");
        user.setPassword(createPassword());
        user.setFirstName("Alice");
        user.setLastName("Brown");
        user.setDepartment(departments.get(0));
        user.setJoiningDate(LocalDate.now().minusYears(2).minusWeeks(3));
        user.setPicture(uploadPicture("com/company/onboarding/demo/" , "alice.png"));
        saveContext.saving(user);
        list.add(user);
        for (Step step : steps) {
            UserStep userStep = dataManager.create(UserStep.class);
            userStep.setUser(user);
            userStep.setStep(step);
            userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
            userStep.setCompletedDate(user.getJoiningDate().plusDays(step.getDuration() - 1));
            userStep.setSortValue(step.getSortValue());
            saveContext.saving(userStep);
        }
        dataManager.save(saveContext);

        Department marketingDept = departments.get(1);
        marketingDept.setHrManager(user);
        dataManager.save(marketingDept);

        saveContext = new SaveContext();
        user = dataManager.create(User.class);
        user.setUsername("james");
        user.setPassword(createPassword());
        user.setFirstName("James");
        user.setLastName("Wilson");
        user.setDepartment(departments.get(0));
        user.setJoiningDate(LocalDate.now().minusYears(1).minusWeeks(5));
        user.setPicture(uploadPicture("com/company/onboarding/demo/" , "james.png"));
        saveContext.saving(user);
        list.add(user);
        for (Step step : steps) {
            UserStep userStep = dataManager.create(UserStep.class);
            userStep.setUser(user);
            userStep.setStep(step);
            userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
            userStep.setCompletedDate(user.getJoiningDate().plusDays(step.getDuration() - 1));
            userStep.setSortValue(step.getSortValue());
            saveContext.saving(userStep);
        }
        dataManager.save(saveContext);

        Department operationsDept = departments.get(2);
        operationsDept.setHrManager(user);
        dataManager.save(operationsDept);

        saveContext = new SaveContext();
        user = dataManager.create(User.class);
        user.setUsername("mary");
        user.setPassword(createPassword());
        user.setFirstName("Mary");
        user.setLastName("Jones");
        user.setDepartment(departments.get(1));
        user.setJoiningDate(LocalDate.now().minusDays(3));
        user.setPicture(uploadPicture("com/company/onboarding/demo/", "mary.png"));
        saveContext.saving(user);
        list.add(user);
        for (Step step : steps) {
            UserStep userStep = dataManager.create(UserStep.class);
            userStep.setUser(user);
            userStep.setStep(step);
            userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
            userStep.setCompletedDate(null);
            userStep.setSortValue(step.getSortValue());
            saveContext.saving(userStep);
        }
        dataManager.save(saveContext);

        saveContext = new SaveContext();
        user = dataManager.create(User.class);
        user.setUsername("linda");
        user.setPassword(createPassword());
        user.setFirstName("Linda");
        user.setLastName("Evans");
        user.setDepartment(departments.get(2));
        user.setJoiningDate(LocalDate.now().minusDays(2));
        user.setPicture(uploadPicture("com/company/onboarding/demo/", "linda.png"));
        saveContext.saving(user);
        list.add(user);
        for (Step step : steps) {
            UserStep userStep = dataManager.create(UserStep.class);
            userStep.setUser(user);
            userStep.setStep(step);
            userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
            userStep.setCompletedDate(null);
            userStep.setSortValue(step.getSortValue());
            saveContext.saving(userStep);
        }
        dataManager.save(saveContext);

        saveContext = new SaveContext();
        user = dataManager.create(User.class);
        user.setUsername("susan");
        user.setPassword(createPassword());
        user.setFirstName("Susan");
        user.setLastName("Baker");
        user.setDepartment(departments.get(2));
        user.setJoiningDate(LocalDate.now());
        user.setPicture(uploadPicture("com/company/onboarding/demo/", "susan.png"));
        saveContext.saving(user);
        list.add(user);
        for (Step step : steps) {
            UserStep userStep = dataManager.create(UserStep.class);
            userStep.setUser(user);
            userStep.setStep(step);
            userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
            userStep.setCompletedDate(null);
            userStep.setSortValue(step.getSortValue());
            saveContext.saving(userStep);
        }
        dataManager.save(saveContext);

        saveContext = new SaveContext();
        user = dataManager.create(User.class);
        user.setUsername("bob");
        user.setPassword(createPassword());
        user.setFirstName("Robert");
        user.setLastName("Taylor");
        user.setDepartment(departments.get(2));
        user.setJoiningDate(LocalDate.now().minusDays(1));
        user.setPicture(uploadPicture("com/company/onboarding/demo/", "bob.png"));
        saveContext.saving(user);
        list.add(user);
        for (Step step : steps) {
            UserStep userStep = dataManager.create(UserStep.class);
            userStep.setUser(user);
            userStep.setStep(step);
            userStep.setDueDate(user.getJoiningDate().plusDays(step.getDuration()));
            userStep.setCompletedDate(userStep.getDueDate().isBefore(LocalDate.now().plusDays(1)) ? userStep.getDueDate() : null);
            userStep.setSortValue(step.getSortValue());
            saveContext.saving(userStep);
        }
        dataManager.save(saveContext);

        return list;
    }

    private FileRef uploadPicture(String path, String fileName) {
        ClassPathResource resource = new ClassPathResource(path + fileName);
        try (InputStream stream = resource.getInputStream()) {
            return fileStorage.saveStream(fileName, stream);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read resource: " + path + fileName, e);
        }
    }

    private String createPassword() {
        return passwordEncoder.encode("1");
    }

    private void assignRoles(List<User> users) {
        for (User user : users) {
            boolean isHrManager = Arrays.asList("alice", "james").contains(user.getUsername());

            RoleAssignmentEntity roleAssignment;

            roleAssignment = dataManager.create(RoleAssignmentEntity.class);
            roleAssignment.setUsername(user.getUsername());
            roleAssignment.setRoleCode("ui-minimal");
            roleAssignment.setRoleType(RoleAssignmentRoleType.RESOURCE);
            dataManager.save(roleAssignment);

            roleAssignment = dataManager.create(RoleAssignmentEntity.class);
            roleAssignment.setUsername(user.getUsername());
            roleAssignment.setRoleCode(isHrManager ? "hr-manager" : "employee");
            roleAssignment.setRoleType(RoleAssignmentRoleType.RESOURCE);
            dataManager.save(roleAssignment);

            if (isHrManager) {
                roleAssignment = dataManager.create(RoleAssignmentEntity.class);
                roleAssignment.setUsername(user.getUsername());
                roleAssignment.setRoleCode("hr-manager-rl");
                roleAssignment.setRoleType(RoleAssignmentRoleType.ROW_LEVEL);
                dataManager.save(roleAssignment);
            }
        }
    }
}