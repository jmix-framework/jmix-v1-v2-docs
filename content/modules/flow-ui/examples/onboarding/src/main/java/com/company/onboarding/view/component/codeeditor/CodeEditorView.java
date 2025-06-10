package com.company.onboarding.view.component.codeeditor;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.Metadata;
import io.jmix.flowui.kit.component.codeeditor.autocomplete.Suggester;
import io.jmix.flowui.kit.component.codeeditor.autocomplete.Suggestion;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "CodeEditorView", layout = MainView.class)
@ViewController("CodeEditorView")
@ViewDescriptor("code-editor-view.xml")
public class CodeEditorView extends StandardView {

    @Autowired
    private Metadata metadata;

    // tag::code-editor-with-custom-suggestions[]
    @Install(to = "codeEditorWithCustomSuggestions", subject = "suggester")
    private List<Suggestion> codeEditorWithCustomSuggestionsSuggester(Suggester.SuggestionContext context) {
        String text = context.getText();
        int cursorPosition = context.getCursorPosition();
        // If autocompletion is requested after "user."
        if (cursorPosition == text.indexOf("user.") + 5) {
            // Return a list of suggestions for properties of the User entity
            return metadata.getClass(User.class).getProperties().stream()
                    .map(property -> new Suggestion(property.getName(), property.getName()))
                    .toList();
        } else {
            return List.of();
        }
    }
    // end::code-editor-with-custom-suggestions[]
}