package com.smartsense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smartsense.model.Device;

/**
 * Repository interface for Device entity.
 * Provides CRUD operations and custom query methods through MongoRepository.
 */
@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

    Page<Device> findByZoneId(String albumId, Pageable pageable);

}
