package io.github.peterberghuis.springmysqleskafka.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemDocument {

    @Field(type = FieldType.Text)
    private String bookTitle;

    @Field(type = FieldType.Text)
    private String bookAuthor;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant purchaseDate;

    @Field(type = FieldType.Double)
    private Double price;
}
