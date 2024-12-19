package com.smartsense.repository;

import com.smartsense.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {
    Page<Alert> findByRemovedAtNull(Pageable pageable);
    Page<Alert> findByMessageContainingIgnoreCaseAndRemovedAtNull(String message, Pageable pageable);
}
