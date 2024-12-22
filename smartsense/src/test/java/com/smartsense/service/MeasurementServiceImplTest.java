package com.smartsense.service;

import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.dto.measurement.MeasurementResponse;
import com.smartsense.exceptions.InactiveDeviceException;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.MeasurementMapper;
import com.smartsense.model.Alert;
import com.smartsense.model.Device;
import com.smartsense.model.Measurement;
import com.smartsense.model.enums.DeviceType;
import com.smartsense.model.enums.Status;
import com.smartsense.repository.AlertRepository;
import com.smartsense.repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private MeasurementMapper measurementMapper;

    @InjectMocks
    private MeasurementServiceImpl measurementService;

    private Measurement measurement;
    private MeasurementDTO measurementDTO;
    private Device device;

    @BeforeEach
    void setUp() {
        device = new Device();
        device.setStatus(Status.ACTIVE);
        device.setType(DeviceType.TEMPERATURE);

        measurement = new Measurement();
        measurement.setValue(25.0);
        measurement.setDevice(device);
        measurement.setTimestamp(LocalDateTime.now());

        measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(25.0);
    }

    @Test
    void getMeasurementById_ShouldReturnMeasurement_WhenExists() {
        when(measurementRepository.findById(any())).thenReturn(Optional.of(measurement));
        when(measurementMapper.toDto(any(Measurement.class), any(String[].class)))
                .thenReturn(measurementDTO);

        MeasurementDTO result = measurementService.getMeasurementById("1");

        assertNotNull(result);
        assertEquals(25.0, result.getValue());
        verify(measurementRepository).findById("1");
    }

    @Test
    void getMeasurementById_ShouldThrowException_WhenNotFound() {
        when(measurementRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> measurementService.getMeasurementById("1"));
    }

    @Test
    void saveMeasurement_ShouldSaveMeasurementAndCreateAlert_WhenDeviceActive() {
        when(measurementMapper.toEntity(any(MeasurementDTO.class))).thenReturn(measurement);
        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(alertRepository.save(any(Alert.class))).thenReturn(new Alert());

        MeasurementResponse response = measurementService.saveMeasurement(measurementDTO);

        assertNotNull(response);
        verify(measurementRepository).save(any(Measurement.class));
        verify(alertRepository).save(any(Alert.class));
    }

    @Test
    void saveMeasurement_ShouldThrowException_WhenDeviceInactive() {
        device.setStatus(Status.INACTIVE);
        measurement.setDevice(device);
        
        when(measurementMapper.toEntity(any(MeasurementDTO.class))).thenReturn(measurement);

        assertThrows(InactiveDeviceException.class,
                () -> measurementService.saveMeasurement(measurementDTO));
    }
}