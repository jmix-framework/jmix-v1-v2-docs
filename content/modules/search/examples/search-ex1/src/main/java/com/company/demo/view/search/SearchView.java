package com.company.demo.view.search;


import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.view.*;
import io.jmix.search.searching.SearchResult;
import io.jmix.searchflowui.component.SearchField;
import io.jmix.searchflowui.component.SearchFieldContext;
import io.jmix.searchflowui.view.result.SearchResultsView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "search-view", layout = MainView.class)
@ViewController("SearchView")
@ViewDescriptor("search-view.xml")
public class SearchView extends StandardView {

    // tag::SearchCompletedEvent[]
    @Autowired
    private DialogWindows dialogWindows;
    @ViewComponent
    private SearchField searchField;

    @Install(to = "searchField", subject = "searchCompletedHandler")
    private void searchFieldSearchCompletedHandler(
            final SearchField.SearchCompletedEvent event) {
        SearchResult searchResult = event.getSearchResult();
        DialogWindow<SearchResultsView> searchResultsDialog =
                dialogWindows.view(UiComponentUtils.getView(this),
                                SearchResultsView.class)
                        .build();
        SearchResultsView view = searchResultsDialog.getView();
        view.initView(new SearchFieldContext(searchField));
        searchResultsDialog.open();
    }
    // end::SearchCompletedEvent[]
}