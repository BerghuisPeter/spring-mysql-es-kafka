package io.github.peterberghuis.springmysqleskafka.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

    @Field(type = FieldType.Nested)
    private List<PurchaseItemDocument> purchases;
}

