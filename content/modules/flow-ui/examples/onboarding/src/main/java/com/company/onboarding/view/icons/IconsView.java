package com.company.onboarding.view.icons;


import com.company.onboarding.icons.MyIcons;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.icon.JmixFontIcon;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "icons-view", layout = MainView.class)
@ViewController(id = "IconsView")
@ViewDescriptor(path = "icons-view.xml")
public class IconsView extends StandardView {
    @ViewComponent
    private JmixButton spriteIconButton;
    @ViewComponent
    private JmixButton standaloneIconButton;
    @ViewComponent
    private JmixButton helperButton;
    @ViewComponent
    private JmixButton editorButton;

    @Autowired
    protected Icons icons;

    @Subscribe
    public void onInit(final InitEvent event) {

        // tag::programmatic-1[]
        helperButton.setIcon(icons.get(JmixFontIcon.QUESTION_CIRCLE));
        // end::programmatic-1[]

        // tag::programmatic-2[]
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        editorButton.setIcon(LumoIcon.COG.create());
        // end::programmatic-2[]

        // tag::programmatic-sprite[]
        spriteIconButton.setIcon(MyIcons.STAR.create());
        // end::programmatic-sprite[]

        // tag::programmatic-standalone[]
        SvgIcon treeIcon = new SvgIcon();
        treeIcon.setSrc(DownloadHandler.forClassResource(
                getClass(),
                "/META-INF/resources/icons/tree.svg",
                "tree.svg"
        ));
        standaloneIconButton.setIcon(treeIcon);
        // end::programmatic-standalone[]
    }
}