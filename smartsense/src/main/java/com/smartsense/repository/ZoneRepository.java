package com.smartsense.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smartsense.model.Zone;

/**
 * Repository interface for Zone entity.
 * Provides CRUD operations and custom query methods through MongoRepository.
 */
@Repository
public interface ZoneRepository extends MongoRepository<Zone, String> {

   Optional<Zone> findByName(String name);

   Page<Zone> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
