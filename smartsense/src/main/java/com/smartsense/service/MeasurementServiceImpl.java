package com.smartsense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.dto.measurement.UpdateMeasurementDTO;
import com.smartsense.service.interfaces.MeasurementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Measurement entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    @Override
    public MeasurementDTO getMeasurementById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMeasurementById'");
    }

    @Override
    public MeasurementDTO getMeasurementById(String id, String... with) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMeasurementById'");
    }

    @Override
    public Page<MeasurementDTO> getAllCategories(Pageable pageable, String title, String artist, Integer year) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCategories'");
    }

    @Override
    public Page<MeasurementDTO> getAllCategories(Pageable pageable, String title, String artist, Integer year,
            String... with) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCategories'");
    }

    @Override
    public MeasurementDTO addMeasurement(MeasurementDTO Measurement) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMeasurement'");
    }

    @Override
    public MeasurementDTO updateMeasurement(String MeasurementId, UpdateMeasurementDTO Measurement) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMeasurement'");
    }

    @Override
    public void deleteMeasurementById(String MeasurementId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMeasurementById'");
    }

}
