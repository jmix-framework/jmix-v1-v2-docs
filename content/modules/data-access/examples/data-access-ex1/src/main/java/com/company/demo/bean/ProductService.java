package com.company.demo.bean;

import com.company.demo.entity.Product;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {

    @Autowired
    private DataManager dataManager;

    // tag::load-by-tag[]
    List<Product> getProductsByTag(String tag) {
        return dataManager.load(Product.class)
                .query("select p from sample_Product p join p.tags t where t = :tag")
                .parameter("tag", tag)
                .list();
    }
    // end::load-by-tag[]

    // tag::load-by-tag-like[]
    List<Product> getProductsByTagLike(String tag) {
        return dataManager.load(Product.class)
                .query("from sample_Product p join p.tags t where t like ?1", "%" + tag + "%")
                .list();
    }
    // end::load-by-tag-like[]

    // tag::load-by-empty-tags[]
    List<Product> getProductsWithoutTags() {
        return dataManager.load(Product.class)
                .query("e.tags is empty")
                .list();
    }
    // end::load-by-empty-tags[]

    // tag::load-values[]
    List<KeyValueEntity> getProductNameTagPairs(Product product) {
        return dataManager.loadValues(
                    "select p.name, t from sample_Product p join p.tags t where p = :product")
                .properties("name", "tag")
                .parameter("product", product)
                .list();
    }
    // end::load-values[]

    List<KeyValueEntity> getProductNamesWithTags_failing() {
        return dataManager.loadValues("select p.name, p.tags from sample_Product p")
                .properties("name", "tag")
                .list();
    }

    // tag::load-value[]
    List<String> getProductTags(Product product) {
        return dataManager.loadValue(
                    "select t from sample_Product p join p.tags t where p = :product", String.class)
                .parameter("product", product)
                .list();
    }
    // end::load-value[]

    // tag::load-by-tag-using-condition[]
    List<Product> getProductsByTagUsingCondition(String tag) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal("tags", tag))
                .list();
    }
    // end::load-by-tag-using-condition[]

    // tag::load-by-empty-tags-using-condition[]
    List<Product> getProductsWithoutTagsUsingCondition() {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.isCollectionEmpty("tags", true))
                .list();
    }
    // end::load-by-empty-tags-using-condition[]

}
