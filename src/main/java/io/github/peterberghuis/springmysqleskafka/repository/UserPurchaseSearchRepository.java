package io.github.peterberghuis.springmysqleskafka.repository;

import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.UserPurchaseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPurchaseSearchRepository extends ElasticsearchRepository<UserPurchaseDocument, String> {
}