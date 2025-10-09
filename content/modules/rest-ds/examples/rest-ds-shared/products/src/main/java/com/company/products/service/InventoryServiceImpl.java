package com.company.products.service;

import com.company.shared.entity.Product;
import com.company.shared.service.InventoryService;
import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;

// tag::service[]
@RestService("InventoryService")
public class InventoryServiceImpl implements InventoryService {

    @RestMethod
    @Override
    public Double getAvailableInStock(Product product) {
        return (double) Math.round(Math.random() * 100);
    }
}
// end::service[]
