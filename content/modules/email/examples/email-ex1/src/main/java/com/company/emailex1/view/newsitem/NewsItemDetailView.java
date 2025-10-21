package com.company.emailex1.view.newsitem;

import com.company.emailex1.entity.NewsItem;
import com.company.emailex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "news-items/:id", layout = MainView.class)
@ViewController(id = "NewsItem.detail")
@ViewDescriptor(path = "news-item-detail-view.xml")
@EditedEntityContainer("newsItemDc")
public class NewsItemDetailView extends StandardDetailView<NewsItem> {

}