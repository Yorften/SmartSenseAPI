package com.smartsense.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.dto.measurement.UpdateMeasurementDTO;

/**
 * Service interface for Measurement entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface MeasurementService {

   MeasurementDTO getMeasurementById(String id);

   MeasurementDTO getMeasurementById(String id, String... with);

   Page<MeasurementDTO> getAllCategories(Pageable pageable, String title, String artist, Integer year);

   Page<MeasurementDTO> getAllCategories(Pageable pageable, String title, String artist, Integer year, String... with);

   MeasurementDTO addMeasurement(MeasurementDTO Measurement);

   MeasurementDTO updateMeasurement(String MeasurementId, UpdateMeasurementDTO Measurement);

   void deleteMeasurementById(String MeasurementId);
}
