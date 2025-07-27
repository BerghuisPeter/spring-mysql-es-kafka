package io.github.peterberghuis.springmysqleskafka.service;

import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.PurchaseItemDocument;
import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.UserPurchaseDocument;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.Book;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.Purchase;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseEventConsumer {

    private final ElasticsearchOperations elasticsearchOperations;

    @KafkaListener(topics = "purchase-events", groupId = "bookstore-group")
    public void handlePurchaseEvent(Purchase purchase) {
        try {
            // Transform Purchase entity → Elasticsearch document
            User user = purchase.getUser();
            Book book = purchase.getBook();

            PurchaseItemDocument itemDoc = new PurchaseItemDocument(
                    book.getTitle(),
                    book.getAuthor(),
                    purchase.getDate().atStartOfDay().toInstant(ZoneOffset.UTC),
                    book.getPrice().doubleValue()
            );

            String userId = String.valueOf(user.getId());

            // Retrieve existing document if present
            UserPurchaseDocument userDoc = elasticsearchOperations.get(userId, UserPurchaseDocument.class);
            if (userDoc == null) {
                userDoc = new UserPurchaseDocument(userId, user.getName(), user.getEmail(), new ArrayList<>());
            }

            userDoc.getPurchases().add(itemDoc);

            log.info("user purchases: {}", userDoc.getPurchases());
            // Index updated document
            elasticsearchOperations.save(userDoc);

        } catch (Exception e) {
            // Log and handle failure gracefully
            log.error("Error handling purchase event: {}", e.getMessage());
        }
    }
}
