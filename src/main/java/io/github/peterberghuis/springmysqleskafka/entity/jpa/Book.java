package io.github.peterberghuis.springmysqleskafka.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Purchase> purchases;
}
