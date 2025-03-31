package com.company.onboarding.view.icons;


import com.company.onboarding.icons.MyIcons;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "icons-view", layout = MainView.class)
@ViewController(id = "IconsView")
@ViewDescriptor(path = "icons-view.xml")
public class IconsView extends StandardView {
    @ViewComponent
    private JmixButton spriteIconButton;
    @ViewComponent
    private JmixButton standaloneIconButton;
    @ViewComponent
    private JmixButton iconButton1;
    @ViewComponent
    private JmixButton iconButton2;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::programmatic[]
        iconButton1.setIcon(VaadinIcon.USER.create());
        iconButton2.setIcon(LumoIcon.USER.create());
        // end::programmatic[]

        // tag::programmatic-sprite[]
        spriteIconButton.setIcon(MyIcons.STAR.create());
        // end::programmatic-sprite[]

        // tag::programmatic-standalone[]
        StreamResource iconResource = new StreamResource("tree.svg",
                () -> getClass().getResourceAsStream("/META-INF/resources/icons/tree.svg"));
        SvgIcon treeIcon = new SvgIcon(iconResource);
        standaloneIconButton.setIcon(treeIcon);
        // end::programmatic-standalone[]
    }
}