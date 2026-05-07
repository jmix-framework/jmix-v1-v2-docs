package com.company.onboarding.view.component.avatar;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "AvatarView", layout = MainView.class)
@ViewController("AvatarView")
@ViewDescriptor("avatar-view.xml")
public class AvatarView extends StandardView {
    @ViewComponent
    private Avatar avatar;

    @Subscribe("staticBtn")
    public void onStaticBtnClick(final ClickEvent<JmixButton> event) {
        //tag::static-image[]
        avatar.setImage("/icons/mary.png");
        //end::static-image[]
    }

    @Subscribe("urlBtn")
    public void onUrlBtnClick(final ClickEvent<JmixButton> event) {
        //tag::url-image[]
        avatar.setImage("https://www.jmix.io/_nuxt/img/banner-img.d30b0fa.svg");
        //end::url-image[]
    }

    @Subscribe("streamBtn")
    public void onStreamBtnClick(final ClickEvent<JmixButton> event) {
        //tag::stream-image[]
        avatar.setImageHandler(
                DownloadHandler.forClassResource(
                        getClass(), "/META-INF/resources/icons/mary.png")
        );
        //end::stream-image[]
    }

}