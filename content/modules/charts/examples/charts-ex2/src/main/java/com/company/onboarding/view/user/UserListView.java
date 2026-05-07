package com.company.onboarding.view.user;

import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class UserListView extends StandardListView<User> {

    // tag::viewComponent[]
    @ViewComponent
    protected Chart pie;
    // end::viewComponent[]
    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private DataManager dataManager;
    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::userLists[]
        final Integer notStarted = dataManager.loadValue(
                        "select count(u) from User u where u.onboardingStatus = :onboardingStatus",
                        Integer.class
                )
                .parameter("onboardingStatus", OnboardingStatus.NOT_STARTED)
                .one();

        final Integer inProgress = dataManager.loadValue(
                        "select count(u) from User u where u.onboardingStatus = :onboardingStatus",
                        Integer.class
                )
                .parameter("onboardingStatus", OnboardingStatus.IN_PROGRESS)
                .one();


        final Integer completed = dataManager.loadValue(
                        "select count(u) from User u where u.onboardingStatus = :onboardingStatus",
                        Integer.class
                )
                .parameter("onboardingStatus", OnboardingStatus.COMPLETED)
                .one();
        // end::userLists[]
        ListChartItems<MapDataItem> items = new ListChartItems<>(
                new MapDataItem(Map.of("category", "Completed", "value", notStarted)),
                new MapDataItem(Map.of("category", "Not Started", "value", inProgress)),
                new MapDataItem(Map.of("category", "In Progress", "value", completed))
            );


        pie.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("category")
                                        .withValueField("value")
                        )
        );
    }
    // end::onInit[]

    @Supply(to = "usersDataGrid.picture", subject = "renderer")
    private Renderer<User> usersDataGridPictureRenderer() {
        return new ComponentRenderer<>(user -> {
            FileRef fileRef = user.getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                InputStreamDownloadHandler handler = DownloadHandler.fromInputStream(event ->
                        new DownloadResponse(
                                fileStorage.openStream(fileRef),
                                fileRef.getFileName(),
                                fileRef.getContentType(),
                                -1
                        ));
                image.setSrc(handler);
                image.setClassName("user-picture");

                return image;
            } else {
                return null;
            }
        });
    }
}