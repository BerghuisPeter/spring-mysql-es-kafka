package io.github.peterberghuis.springmysqleskafka.config;

import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.PurchaseItemDocument;
import io.github.peterberghuis.springmysqleskafka.entity.elasticsearch.UserPurchaseDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ElasticsearchIndexInitializer implements CommandLineRunner {

    private final ElasticsearchOperations elasticsearchOperations;
    private final Environment environment;

    @Override
    public void run(String... args) {
        // Create indexes for all entities (add more if you have)
        createIndexIfMissing(UserPurchaseDocument.class);

        if (isLocalProfileActive()) {
            insertDummyData();
        }
    }

    private void createIndexIfMissing(Class<?> clazz) {
        IndexOperations indexOps = elasticsearchOperations.indexOps(clazz);

        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping());
            log.info("✅ Created Elasticsearch index for {}", clazz.getSimpleName());
        } else {
            log.info("ℹ️ Index already exists: {}", clazz.getSimpleName());
        }
    }

    private void insertDummyData() {
        if (elasticsearchOperations.exists("1", UserPurchaseDocument.class)) {
            log.info("Dummy data already exists in Elasticsearch");
            return;
        }

        UserPurchaseDocument user1 = new UserPurchaseDocument();
        user1.setUserId("1");
        user1.setUserName("Alice");
        user1.setUserEmail("alice.carter@test.com");
        user1.setPurchases(List.of(
                new PurchaseItemDocument("Elasticsearch Guide", "Clinton Gormley", Instant.now(), 39.99)
        ));

        UserPurchaseDocument user2 = new UserPurchaseDocument();
        user2.setUserId("2");
        user2.setUserName("Bob");
        user2.setUserEmail("bob.doe@test.com");
        user2.setPurchases(List.of(
                new PurchaseItemDocument("Spring in Action", "Craig Walls", Instant.now(),44.50)
        ));

        elasticsearchOperations.save(user1);
        elasticsearchOperations.save(user2);

        log.info("✅ Inserted dummy data into Elasticsearch");
    }

    private boolean isLocalProfileActive() {
        String[] profiles = environment.getActiveProfiles();
        return java.util.Arrays.asList(profiles).contains("local");
    }
}