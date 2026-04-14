package com.company.demo.repository;

import com.company.demo.entity.Product;
import io.jmix.core.FetchPlan;
import io.jmix.core.repository.ApplyConstraints;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

// tag::ApplyConstraints[]
@ApplyConstraints(false)
public interface ProductRepository extends JmixDataRepository<Product, UUID> {
    @Override
    List<Product> findAll(Sort sort, @Nullable FetchPlan fetchPlan);

    @Override
    @ApplyConstraints
    Page<Product> findAll(Pageable pageable);

    List<Product> getByIdNotNull();

    @ApplyConstraints
    List<Product> searchByIdNotNull();

    List<Product> searchById(UUID id);
}
// end::ApplyConstraints[]