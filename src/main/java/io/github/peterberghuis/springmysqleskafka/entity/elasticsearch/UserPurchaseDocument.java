package io.github.peterberghuis.springmysqleskafka.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "user_purchases")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPurchaseDocument {

    @Id
    private String userId;

    private String userName;
    private String userEmail;

    private List<PurchaseItemDocument> purchases;
}

