package com.company.onboarding.report;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.user.UserListView;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.reports.annotation.*;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.yarg.loaders.ReportDataLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ReportDef(
        code = "list-of-users",
        name = "List Of Users"
)
@AvailableInViews(viewClasses = UserListView.class)
@TemplateDef(
        isDefault = true,
        code = "DEFAULT",
        filePath = "com/company/onboarding/report/list-of-users.xlsx",
        outputType = ReportOutputType.XLSX,
        outputNamePattern = "list-of-users.xlsx"
)

// >>> begin example code
@InputParameterDef(
        alias = "username",
        name = "Username contains (leave empty to select all)",
        type = ParameterType.TEXT
)
@BandDef(
        name = "Root",
        root = true
)
@BandDef(
        name = "Header",
        parent = "Root"
)
@BandDef(
        name = "Users",
        parent = "Root",
        dataSets = @DataSetDef(
                name = "users",
                type = DataSetType.DELEGATE
        )
)
@BandDef(
        name = "Roles",
        parent = "Users",
        dataSets = @DataSetDef(
                name = "roles",
                type = DataSetType.SQL,
                query = """
                        select ra.role_code as "role", ra.role_type as "type"
                         from sec_role_assignment ra
                         where ra.username = ${Users.username}"""
        )
)
// <<< end example code
public class ListOfUsers {
    private final DataManager dataManager;

    public ListOfUsers(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // >>> begin example code
    @DataSetDelegate(name = "users")
    public ReportDataLoader usersDataLoader() {
        return (reportQuery, parentBand, params) -> {
            List<User> users = dataManager.load(User.class)
                    .condition(PropertyCondition.contains("username", params.get("username")).skipNullOrEmpty())
                    .sort(Sort.by("username"))
                    .list();
            return users.stream()
                    .map(user -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("username", user.getUsername());
                        map.put("firstName", user.getFirstName());
                        map.put("lastName", user.getLastName());
                        return map;
                    })
                    .toList();
        };
    }
    // <<< end example code
}