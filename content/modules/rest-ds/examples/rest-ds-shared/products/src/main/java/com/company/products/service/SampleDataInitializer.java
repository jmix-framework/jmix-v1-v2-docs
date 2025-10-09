package com.company.products.service;

import com.company.shared.entity.Product;
import com.company.shared.entity.ProductCategory;
import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SampleDataInitializer {

    private static final Logger log = LoggerFactory.getLogger(SampleDataInitializer.class);
    private final DataManager dataManager;

    public SampleDataInitializer(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @EventListener(ApplicationStartedEvent.class)
    @Authenticated
    public void init() {
        List<Product> products = dataManager.load(Product.class).all().maxResults(1).list();
        if (!products.isEmpty()) {
            log.info("Products already exist, skipping initialization");
        } else {
            List<ProductCategory> categories = createCategories();
            createProducts(categories);
        }
    }

    private List<ProductCategory> createCategories() {
        log.info("Creating categories");

        ProductCategory category = dataManager.create(ProductCategory.class);
        category.setName("Electronics");
        dataManager.save(category);

        log.info("Categories created");
        return List.of(category);
    }

    private void createProducts(List<ProductCategory> categories) {
        log.info("Creating products");

        Product product1 = dataManager.create(Product.class);
        product1.setName("iPhone 15");
        product1.setPrice(BigDecimal.valueOf(1000));
        product1.setCategory(categories.get(0));
        dataManager.save(product1);

        Product product2 = dataManager.create(Product.class);
        product2.setName("Galaxy S24");
        product2.setPrice(BigDecimal.valueOf(900));
        product2.setCategory(categories.get(0));
        dataManager.save(product2);

        log.info("Products created");
    }
}
