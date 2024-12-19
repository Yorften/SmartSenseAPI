package com.smartsense.controller.measurement;

import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.service.interfaces.MeasurementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing Device entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/user/mesures")
@RequiredArgsConstructor
public class MeasurementController {


    private final MeasurementService measurementService;

    @GetMapping
    public ResponseEntity<Page<MeasurementDTO>> getAllMeasurements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(measurementService.getAllMeasurements(pageable, "device"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementDTO> getMeasurementById(
            @PathVariable String id) {
        return ResponseEntity.ok(measurementService.getMeasurementById(id, "device"));
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<Page<MeasurementDTO>> getMeasurementsByDevice(
            @PathVariable String deviceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(measurementService.getMeasurementsByDevice(deviceId, pageable, "device"));
    }

    @PostMapping
    public ResponseEntity<MeasurementDTO> createMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        return ResponseEntity.ok(measurementService.saveMeasurement(measurementDTO));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportMeasurementsToCSV() {
        byte[] csvContent = measurementService.exportMeasurementsToCSV();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", "mesures.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }
}
