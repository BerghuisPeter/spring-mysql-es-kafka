package io.github.peterberghuis.springmysqleskafka.controller;

import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.UserPurchaseDocument;
import io.github.peterberghuis.springmysqleskafka.entity.jpa.Purchase;
import io.github.peterberghuis.springmysqleskafka.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/purchase")
    public Purchase buyBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return purchaseService.buyBook(userId, bookId);
    }

    @GetMapping("/user/{userId}/purchases")
    public Optional<UserPurchaseDocument> getPurchases(@PathVariable Long userId) {
        return purchaseService.getUserPurchases(userId);
    }

    @GetMapping("/author/purchases")
    public List<UserPurchaseDocument> getUserPurchasesByBookAuthor(@RequestParam String bookAuthor) {
        return purchaseService.getUserPurchasesByBookAuthor(bookAuthor);
    }

    @GetMapping("/purchases")
    public List<Purchase> listPurchases() {
        return purchaseService.listAllPurchases();
    }
}
