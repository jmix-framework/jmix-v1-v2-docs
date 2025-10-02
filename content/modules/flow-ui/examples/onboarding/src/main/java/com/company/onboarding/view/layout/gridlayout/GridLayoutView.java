package com.company.onboarding.view.layout.gridlayout;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.card.JmixCard;
import io.jmix.flowui.component.gridlayout.GridLayout;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "grid-layout-view", layout = MainView.class)
@ViewController(id = "GridLayoutView")
@ViewDescriptor(path = "grid-layout-view.xml")
public class GridLayoutView extends StandardView {
    // tag::gridLayout[]
    @ViewComponent
    private GridLayout<Object> gridLayout;

    // end::gridLayout[]
    // tag::uiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::uiComponents[]
    @Autowired
    private FileStorage fileStorage;

    // tag::add-example[]
    @Subscribe
    public void onInit(final InitEvent event) {
        Checkbox checkbox = uiComponents.create(Checkbox.class);
        checkbox.setLabel("I verify that all information is accurate");
        checkbox.setValue(false);
        gridLayout.add(checkbox);
    }
    // end::add-example[]

    @Install(to = "gridLayoutUsers", subject = "itemLabelGenerator")
    private String gridLayoutUsersItemLabelGenerator(final User item) {
        return item.getFirstName() + " " + item.getLastName();
    }

    // tag::renderer[]
    @Supply(to = "gridLtUsers", subject = "renderer")
    private ComponentRenderer<JmixCard, User> gridLtUsersRenderer() { // <1>
        return new ComponentRenderer<>(this::createCard, this::initCard);
    }

    // end::renderer[]

    // tag::renderer[]
    private JmixCard createCard() { // <2>
        JmixCard card = uiComponents.create(JmixCard.class);
        card.setWidthFull();
        card.addThemeVariants(CardVariant.LUMO_OUTLINED, CardVariant.LUMO_ELEVATED);
        return card;
    }

    // end::renderer[]
    // tag::renderer[]
    private void initCard(JmixCard card, User user) { // <3>
        card.setHeaderPrefix(createAvatar(user));
        card.setTitle(user.getFirstName() + " " + user.getLastName());
    }

    // end::renderer[]
// tag::renderer[]
    private Image createAvatar(User user) { // <4>
        Image image = uiComponents.create(Image.class);
        FileRef fileRef = user.getPicture();
        if (fileRef != null) {
            image.setWidth("50px");
            image.setHeight("50px");
            StreamResource streamResource = new StreamResource(
                    fileRef.getFileName(),
                    () -> fileStorage.openStream(fileRef));
            image.setSrc(streamResource);
        }
        return image;
    }
    // end::renderer[]
}