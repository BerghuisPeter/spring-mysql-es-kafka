package io.github.peterberghuis.springmysqleskafka.repository;

import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.UserPurchaseDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPurchaseSearchRepository extends ElasticsearchRepository<UserPurchaseDocument, String> {
    @Query("""
    {
      "nested": {
        "path": "purchases",
        "query": {
          "match": {
            "purchases.bookAuthor": "?0"
          }
        }
      }
    }
    """)
    List<UserPurchaseDocument> findByPurchasesBookAuthor(String authorName);
}