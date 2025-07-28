package io.github.peterberghuis.springmysqleskafka.service;

import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.UserPurchaseDocument;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.Book;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.Purchase;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.User;
import io.github.peterberghuis.springmysqleskafka.repository.BookRepository;
import io.github.peterberghuis.springmysqleskafka.repository.PurchaseRepository;
import io.github.peterberghuis.springmysqleskafka.repository.UserPurchaseSearchRepository;
import io.github.peterberghuis.springmysqleskafka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {


    private static final String TOPIC = "purchase-events";

    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final PurchaseRepository purchaseRepo;
    private final UserPurchaseSearchRepository esRepo;
    private final KafkaTemplate<String, Purchase> kafkaTemplate;


    /**
     * ✅ Handles the purchase process
     */
    public Purchase buyBook(Long userId, Long bookId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Save purchase in MySQL
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setBook(book);
        purchase.setDate(LocalDate.now());
        purchaseRepo.save(purchase);

        // Send purchase event to Kafka
        kafkaTemplate.send(TOPIC, purchase);

        return purchase;
    }

    /**
     * ✅ Get purchases from Elasticsearch
     */
    public Optional<UserPurchaseDocument> getUserPurchases(Long userId) {
        return esRepo.findById(userId.toString());
    }

    /**
     * ✅ Get purchases by book author
     */
    public List<UserPurchaseDocument> getUserPurchasesByBookAuthor(String bookAuthor) {
        return esRepo.findByPurchasesBookAuthor(bookAuthor);
    }

    /**
     * ✅ Get all purchases from MySQL
     */
    public List<Purchase> listAllPurchases() {
        return purchaseRepo.findAll();
    }

}
