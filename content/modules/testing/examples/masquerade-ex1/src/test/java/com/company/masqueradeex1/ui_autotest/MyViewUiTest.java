package com.company.masqueradeex1.ui_autotest;

import com.company.masqueradeex1.test_support.view.MyView;
import com.company.masqueradeex1.test_support.view.user.UserListDialog;
import io.jmix.masquerade.component.EntityComboBox;
import io.jmix.masquerade.component.HasActions;
import org.junit.jupiter.api.Test;
import com.company.masqueradeex1.test_support.view.sys.MainView;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.sleep;
import static io.jmix.masquerade.JConditions.*;
import static io.jmix.masquerade.JConditions.value;

public class MyViewUiTest extends AbstractUiTest {


    @Test
    public void buttonTest() {
        openMyView().getButton()
                .shouldBe(VISIBLE)
                .shouldHave(text("Button"))
                .click();
    }



    // tag::EntityComboBoxTest[]
    @Test
    public void testEntityComboBox() {
        EntityComboBox entityComboBox = openMyView().getEntityComboBox();

        entityComboBox.shouldHave(label("EntityComboBox"))
                .setValue("[admin]")
                .shouldHave(value("[admin]"))
                .clickItemsOverlay()
                .shouldHave(visibleItems("[admin]", "[test]", "[test1]"))
                .shouldHave(visibleItemsCount(3))
                .shouldHave(visibleItemsContains("[test]"));

        sleep(3000);

        entityComboBox.getItemsOverlay()
                .select("[test]");
        sleep(3000);

        entityComboBox.shouldHave(value("[test]"))
                .triggerActionWithView(UserListDialog.class, HasActions.LOOKUP)
                .selectAdmin();

        sleep(3000);
        entityComboBox.shouldHave(value("[admin]"));
    }
    // end::EntityComboBoxTest[]


    protected MyView openMyView() {
        MainView mainView = loginAsAdmin();
        return mainView.openItem(MyView.class, "myViewListItem");
    }
}
