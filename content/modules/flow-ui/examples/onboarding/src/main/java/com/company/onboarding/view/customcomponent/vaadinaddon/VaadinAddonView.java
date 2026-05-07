package com.company.onboarding.view.customcomponent.vaadinaddon;


import com.company.onboarding.view.main.MainView;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "vaadin-addon-view", layout = MainView.class)
@ViewController("VaadinAddonView")
@ViewDescriptor("vaadin-addon-view.xml")
public class VaadinAddonView extends StandardView {
    // tag::vaadin-addon-usage[]
    @Subscribe
    public void onInit(final InitEvent event) {
        PdfViewer pdfViewer = new PdfViewer();
        pdfViewer.setSizeFull();

        pdfViewer.setSrc(
                DownloadHandler.forClassResource(getClass(),
                        "/META-INF/resources/pdf/example.pdf", "example.pdf"));

        getContent().add(pdfViewer);
    }
    // end::vaadin-addon-usage[]
}