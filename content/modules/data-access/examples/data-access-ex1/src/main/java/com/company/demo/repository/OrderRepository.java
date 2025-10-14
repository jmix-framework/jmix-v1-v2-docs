package com.company.demo.repository;

import com.company.demo.entity.Order;
import io.jmix.core.FetchPlan;
import io.jmix.core.repository.ApplyConstraints;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

// tag::ApplyConstraints[]
public interface OrderRepository extends JmixDataRepository<Order, UUID> {
    @Override
    List<Order> findAll(Sort sort, @Nullable FetchPlan fetchPlan);

    @Override
    @ApplyConstraints(false)
    List<Order> findAll(FetchPlan fetchPlan);

    @ApplyConstraints(false)
    List<Order> findByIdNotNull();
}
// end::ApplyConstraints[]