package com.company.onboarding.view.htmlcomponent.image;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "ImageResourcesView", layout = MainView.class)
@ViewController("ImageResourcesView")
@ViewDescriptor("image-resources-view.xml")
public class ImageResourcesView extends StandardView {

    @ViewComponent
    private JmixImage<?> image;


    @Subscribe("staticBtn")
    public void onStaticBtnClick(final ClickEvent<JmixButton> event) {
        //tag::image-static[]
        image.setSrc("icons/icon.png");
        //end::image-static[]
    }

    @Subscribe("urlBtn")
    public void onUrlBtnClick(final ClickEvent<JmixButton> event) {
        //tag::image-url[]
        image.setSrc("https://www.jmix.io/uploads/framework_image_9efadbc372.svg");
        //end::image-url[]
    }

    @Subscribe("streamBtn")
    public void onStreamBtnClick(final ClickEvent<JmixButton> event) {
        //tag::image-stream[]
        image.setSrc(DownloadHandler.forClassResource(getClass(), "/META-INF/resources/icons/icon.png"));
        //end::image-stream[]
    }

}