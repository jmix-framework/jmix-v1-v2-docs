package com.company.onboarding.view.main;
// tag::import-onboarding-status-changed-event[]
import com.company.onboarding.event.OnboardingStatusChangedEvent;
// end::import-onboarding-status-changed-event[]
import com.vaadin.flow.component.ClickEvent;
// tag::import-span[]
import com.vaadin.flow.component.html.Span;
// end::import-span[]
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
// tag::import-list-menu[]
import io.jmix.flowui.kit.component.main.ListMenu;
// end::import-list-menu[]
import io.jmix.flowui.kit.theme.ThemeUtils;
// tag::import-subscribe[]
import io.jmix.flowui.view.Subscribe;
// end::import-subscribe[]
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
// tag::import-event-listener[]
import org.springframework.context.event.EventListener;
// end::import-event-listener[]
import java.util.random.RandomGenerator;
// tag::class-declaration[]

// class declaration and annotations omitted

// end::class-declaration[]
// tag::main-view[]
@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    // end::main-view[]
    // tag::menu-component[]
    @ViewComponent
    private JmixListMenu menu;

    // end::menu-component[]

    // tag::initialLayout[]
    @ViewComponent
    private JmixImage<Object> urlImage;
    @Autowired
    private Notifications notifications;

    // tag::init-event[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::init-initialLayout[]
        urlImage.setSrc("https://www.jmix.io/uploads/framework_image_9efadbc372.svg");
        urlImage.setWidth("100%");
        urlImage.setHeight("100%");
        // end::init-initialLayout[]
        // tag::init-onBoardingStatus[]
        updateOnboardingStatus(); // <1>
        // end::init-onBoardingStatus[]
        // tag::get-menu-item[]
        ListMenu.MenuItem menuItem = menu.getMenuItem("ListMenuView");
        // Can be 'null' if menu item isn't permitted by security
        if (menuItem != null) {
            menuItem.setPrefixComponent(VaadinIcon.MENU.create());
        }
        // end::get-menu-item[]
        // tag::new-menu-item[]
        ListMenu.MenuBarItem rootItem = new ListMenu.MenuBarItem("help")
                .withTitle("Help")
                .withPrefixComponent(VaadinIcon.QUESTION.create()); // <1>
        ListMenu.MenuItem subItemNews = new ListMenu.MenuItem("news")
                .withTitle("News")
                .withClickHandler(  // <2>
                        item -> {
                            notifications.create("News menu item clicked")
                                    .show();
                        }
                );
        rootItem.addChildItem(subItemNews);
        ListMenu.MenuSeparatorItem sep =
                new ListMenu.MenuSeparatorItem("separator"); // <3>
        rootItem.addChildItem(sep);
        ListMenu.MenuItem subItemBlog = new ListMenu.MenuItem("blog")
                .withTitle("Blog");
        rootItem.addChildItem(subItemBlog);
        menu.addMenuItem(rootItem);
        // end::new-menu-item[]
    }
    // end::init-event[]

    @Subscribe(id = "urlImage", subject = "singleClickListener")
    public void onUrlImageClick(final ClickEvent<JmixImage<?>> event) {
        Notification.show("Clicked!");
    }
    // end::initialLayout[]

    // tag::ui-event-listener[]

    @EventListener
    private void onBoardingStatusChanged(OnboardingStatusChangedEvent event) { // <2>
        updateOnboardingStatus();
    }

    private void updateOnboardingStatus() {
        long number = getUncompletedStepsNumber(); // <3>

        Span badge = null; // <4>
        if (number > 0) {
            badge = new Span("" + number);
            badge.getElement().getThemeList().add("badge warning");
        }

        ListMenu.MenuItem menuItem = menu.getMenuItem("MyOnboardingView");
        // Can be 'null' if menu item isn't permitted by security
        if (menuItem != null) {
            menuItem.setSuffixComponent(badge);
        }
    }
    // end::ui-event-listener[]

    private long getUncompletedStepsNumber() {
        return RandomGenerator.getDefault().nextInt(1, 5);
    }

    // tag::theme-switcher[]
    @Subscribe("themeSwitcher.systemThemeItem.systemThemeAction")
    public void onThemeSwitcherSystemThemeItemSystemThemeAction(final ActionPerformedEvent event) {
        ThemeUtils.applySystemTheme();
    }

    @Subscribe("themeSwitcher.lightThemeItem.lightThemeAction")
    public void onThemeSwitcherLightThemeItemLightThemeAction(final ActionPerformedEvent event) {
        ThemeUtils.applyLightTheme();
    }

    @Subscribe("themeSwitcher.darkThemeItem.darkThemeAction")
    public void onThemeSwitcherDarkThemeItemDarkThemeAction(final ActionPerformedEvent event) {
        ThemeUtils.applyDarkTheme();
    }
    // end::theme-switcher[]

    // tag::main-view[]
}
// end::main-view[]