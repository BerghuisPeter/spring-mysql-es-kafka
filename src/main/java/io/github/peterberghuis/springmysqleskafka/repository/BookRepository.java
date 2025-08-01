package io.github.peterberghuis.springmysqleskafka.repository;

import io.github.peterberghuis.springmysqleskafka.entity.jpa.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String keyword);
}
