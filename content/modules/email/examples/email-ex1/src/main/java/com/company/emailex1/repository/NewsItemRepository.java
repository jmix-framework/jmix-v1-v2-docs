package com.company.emailex1.repository;

import com.company.emailex1.entity.NewsItem;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsItemRepository extends JmixDataRepository<NewsItem, UUID> {
}