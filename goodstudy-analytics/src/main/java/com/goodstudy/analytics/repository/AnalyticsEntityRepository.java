package com.goodstudy.analytics.repository;

import com.goodstudy.analytics.model.AnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsEntityRepository extends JpaRepository<AnalyticsEntity, Long> {
}
