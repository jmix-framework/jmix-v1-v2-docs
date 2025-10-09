package com.company.shared.service;

import com.company.shared.entity.Product;

public interface InventoryService {

    Double getAvailableInStock(Product product);
}
