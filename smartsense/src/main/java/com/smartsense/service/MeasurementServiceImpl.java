package com.smartsense.service;

import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.dto.measurement.MeasurementResponse;
import com.smartsense.exceptions.InactiveDeviceException;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.MeasurementMapper;
import com.smartsense.model.Alert;
import com.smartsense.model.Device;
import com.smartsense.model.Measurement;
import com.smartsense.model.enums.Status;
import com.smartsense.repository.AlertRepository;
import com.smartsense.repository.MeasurementRepository;
import com.smartsense.service.interfaces.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.smartsense.util.AlertHelper.evaluateTemperature;
import static com.smartsense.util.AlertHelper.evaluateHumidity;

/**
 * Service implementation for Measurement entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final AlertRepository alertRepository;
    private final MeasurementMapper measurementMapper;

    @Override
    public Page<MeasurementDTO> getAllMeasurements(Pageable pageable, String... with) {
        measurementMapper.verifyIncludes(with);
        return measurementRepository.findByRemovedAtNull(pageable)
                .map(measurement -> measurementMapper.toDto(measurement, with));
    }

    @Override
    public Page<MeasurementDTO> getMeasurementsByDevice(String deviceId, Pageable pageable, String... with) {
        measurementMapper.verifyIncludes(with);
        return measurementRepository.findByDeviceIdAndRemovedAtNull(deviceId, pageable)
                .map(measurement -> measurementMapper.toDto(measurement, with));
    }

    @Override
    public MeasurementDTO getMeasurementById(String id, String... with) {
        measurementMapper.verifyIncludes(with);
        return measurementMapper.toDto(findMeasurementById(id), with);
    }

    @Override
    public MeasurementResponse saveMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = measurementMapper.toEntity(measurementDTO);
        Device device = measurement.getDevice();
        Alert alert = null;

        if (device.getStatus() == Status.ACTIVE) {
            switch (device.getType()) {
                case TEMPERATURE:
                    alert = evaluateTemperature(measurement.getValue(), device);
                    break;
                case HUMIDITY:
                    alert = evaluateHumidity(measurement.getValue(), device);
                    break;
            }
        } else {
            throw new InactiveDeviceException("Inactive devices can't take measurements");
        }

        alert = alertRepository.save(alert);

        return measurementMapper.toDto(measurementRepository.save(measurement), alert);
    }

    @Override
    public byte[] exportMeasurementsToCSV() {
        List<Measurement> measurements = measurementRepository.findAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // En-têtes CSV
        writer.println("id,timestamp,value,device_id");

        // Données
        measurements.forEach(m -> {
            if (m.getRemovedAt() == null) {
                writer.println(String.format("%s,%s,%.2f,%s",
                        m.getId(),
                        m.getTimestamp().format(formatter),
                        m.getValue(),
                        m.getDevice() != null ? m.getDevice().getId() : ""));
            }
        });

        writer.flush();
        return out.toByteArray();
    }

    private Measurement findMeasurementById(String id) {
        return measurementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesure non trouvée avec l'id: " + id));
    }
}
