package com.smartsense.repository;

import java.util.List;

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

    Page<Device> findByZoneId(String zoneId, Pageable pageable);
    Page<Device> findByNameContainingAndZoneId(String name, String zoneId, Pageable pageable);
    Page<Device> findByNameContaining(String name, Pageable pageable);
    List<Device> findAllByZoneId(String zoneId);

}
