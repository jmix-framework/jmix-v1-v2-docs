package com.company.onboarding.view.user;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.DataGridHelper;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.facet.UrlQueryParametersFacet;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersTable")
@DialogMode(width = "50em", height = "37.5em")
public class UserListView extends StandardListView<User> {

    @ViewComponent
    private DataGrid<User> usersTable;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;

    @ViewComponent
    private UrlQueryParametersFacet urlQueryParameters;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        Grid.Column<User> pictureColumn = usersTable.addColumn(new ComponentRenderer<>(user -> {
            FileRef fileRef = user.getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                image.setClassName("user-picture");

                InputStreamDownloadHandler handler = DownloadHandler.fromInputStream(e ->
                        new DownloadResponse(
                                fileStorage.openStream(fileRef),
                                fileRef.getFileName(),
                                fileRef.getContentType(),
                                -1
                        ));
                image.setSrc(handler);

                return image;
            } else {
                return new Span();
            }
        }));

        DataGridHelper.setDataGridColumnPosition(usersTable, pictureColumn, 0);
    }

    @Subscribe("generateUsersBtn")
    public void onGenerateUsersBtnClick(final ClickEvent<Button> event) {
        for (int i = 0; i < 160; i++) {
            User user = dataManager.create(User.class);
            user.setUsername("user-" + StringUtils.leftPad(String.valueOf(i), 3, '0'));
            user.setActive(true);
            user.setPassword("{noop}1");
            dataManager.save(user);
        }
    }
}