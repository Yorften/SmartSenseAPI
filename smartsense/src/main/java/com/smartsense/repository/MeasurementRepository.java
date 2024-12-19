package com.smartsense.repository;

import com.smartsense.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, String> {
    Page<Measurement> findByDeviceId(String deviceId, Pageable pageable);
    Page<Measurement> findByRemovedAtNull(Pageable pageable);
    Page<Measurement> findByDeviceIdAndRemovedAtNull(String deviceId, Pageable pageable);
}
