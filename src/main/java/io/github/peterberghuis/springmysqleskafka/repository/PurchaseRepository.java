package io.github.peterberghuis.springmysqleskafka.repository;

import io.github.peterberghuis.springmysqleskafka.entity.jpa.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}