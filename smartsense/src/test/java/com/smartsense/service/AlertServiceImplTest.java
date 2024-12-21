package com.smartsense.service;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.AlertMapper;
import com.smartsense.model.Alert;
import com.smartsense.model.enums.Severity;
import com.smartsense.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceImplTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private AlertMapper alertMapper;

    @InjectMocks
    private AlertServiceImpl alertService;

    private Alert alert;
    private AlertDTO alertDTO;
    private Pageable pageable;
    private DeviceDTO deviceDTO;
    private LocalDateTime timestamp;

    @BeforeEach
    void setUp() {
        timestamp = LocalDateTime.now();
        deviceDTO = DeviceDTO.builder().build();

        alert = new Alert();
        alert.setMessage("Test Alert");
        alert.setSeverity(Severity.NORMAL);
        alert.setTimestamp(timestamp);

        alertDTO = new AlertDTO();
        alertDTO.setMessage("Test Alert");
        alertDTO.setSeverity(Severity.NORMAL);
        alertDTO.setTimestamp(timestamp);
        alertDTO.setDevice(deviceDTO);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAlertById_ShouldReturnAlert() {
        when(alertRepository.findById(any())).thenReturn(Optional.of(alert));
        when(alertMapper.toDto(alert)).thenReturn(alertDTO);

        AlertDTO result = alertService.getAlertById("1");

        assertNotNull(result);
        assertEquals("Test Alert", result.getMessage());
        assertEquals(Severity.NORMAL, result.getSeverity());
        assertEquals(timestamp, result.getTimestamp());
        verify(alertRepository).findById("1");
    }

    @Test
    void getAlertByIdWithIncludes_ShouldReturnAlert() throws InvalidDataException {
        when(alertRepository.findById(any())).thenReturn(Optional.of(alert));
        when(alertMapper.toDto(alert, "device")).thenReturn(alertDTO);

        AlertDTO result = alertService.getAlertById("1", "device");

        assertNotNull(result);
        assertEquals("Test Alert", result.getMessage());
        assertEquals(Severity.NORMAL, result.getSeverity());
        assertEquals(timestamp, result.getTimestamp());
        verify(alertMapper).verifyIncludes(new String[]{"device"});
    }

    @Test
    void getAllAlerts_ShouldReturnPageOfAlerts() {
        Page<Alert> alertPage = new PageImpl<>(Collections.singletonList(alert));
        when(alertRepository.findByRemovedAtNull(pageable)).thenReturn(alertPage);
        when(alertMapper.toDto(alert)).thenReturn(alertDTO);

        Page<AlertDTO> result = alertService.getAllAlerts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        AlertDTO firstAlert = result.getContent().get(0);
        assertEquals("Test Alert", firstAlert.getMessage());
        assertEquals(Severity.NORMAL, firstAlert.getSeverity());
        assertEquals(timestamp, firstAlert.getTimestamp());
    }

    @Test
    void searchAlerts_ShouldReturnMatchingAlerts() {
        Page<Alert> alertPage = new PageImpl<>(Collections.singletonList(alert));
        when(alertRepository.findByMessageContainingIgnoreCaseAndRemovedAtNull("test", pageable))
            .thenReturn(alertPage);
        when(alertMapper.toDto(alert)).thenReturn(alertDTO);

        Page<AlertDTO> result = alertService.searchAlerts("test", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        AlertDTO firstAlert = result.getContent().get(0);
        assertEquals("Test Alert", firstAlert.getMessage());
        assertEquals(Severity.NORMAL, firstAlert.getSeverity());
        assertEquals(timestamp, firstAlert.getTimestamp());
    }

    @Test
    void getAlertById_ShouldThrowException_WhenNotFound() {
        when(alertRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, 
            () -> alertService.getAlertById("1"));
    }

    @Test
    void addAlert_ShouldReturnSavedAlert() {
        when(alertMapper.toEntity(any(AlertDTO.class))).thenReturn(alert);
        when(alertRepository.save(any(Alert.class))).thenReturn(alert);
        when(alertMapper.toDto(any(Alert.class), any(String[].class))).thenReturn(alertDTO);

        AlertDTO result = alertService.addAlert(alertDTO);

        assertNotNull(result);
        assertEquals("Test Alert", result.getMessage());
        assertEquals(Severity.NORMAL, result.getSeverity());
        assertEquals(timestamp, result.getTimestamp());
        verify(alertRepository).save(any(Alert.class));
    }
}