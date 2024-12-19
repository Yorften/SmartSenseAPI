package com.smartsense.service.interfaces;

import com.smartsense.dto.measurement.MeasurementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Measurement entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface MeasurementService {

   Page<MeasurementDTO> getMeasurementsByDevice(String deviceId, Pageable pageable, String... with);
   Page<MeasurementDTO> getAllMeasurements(Pageable pageable, String... with);

   // Enregistrer une nouvelle mesure
   MeasurementDTO saveMeasurement(MeasurementDTO measurementDTO);

   // Exporter les mesures
   byte[] exportMeasurementsToCSV();

   MeasurementDTO getMeasurementById(String id, String... with);
}
