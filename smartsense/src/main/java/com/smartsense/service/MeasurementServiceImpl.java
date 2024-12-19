package com.smartsense.service;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.MeasurementMapper;
import com.smartsense.model.Measurement;
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
    private final MeasurementMapper measurementMapper;

    private final AlertServiceImpl alertService;


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
    public MeasurementDTO saveMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = measurementMapper.toEntity(measurementDTO);
        measurement = measurementRepository.save(measurement);
        AlertDTO alertDTO;
        // if condtion for Device
        alertDTO  = evaluateTemperature(measurement.getValue());

         //else
        alertDTO= evaluateHumidity(measurement.getValue());

        alertService.addAlert(alertDTO);

        return measurementMapper.toDto(measurement);
    }

    @Override
    public byte[] exportMeasurementsToCSV() {
        List<Measurement> measurements = measurementRepository.findAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // En-têtes CSV
        writer.println("ID,Date et Heure,Valeur,Device ID");

        // Données
        measurements.forEach(m -> {
            if (m.getRemovedAt() == null) {
                writer.println(String.format("%s,%s,%.2f,%s",
                    m.getId(),
                    m.getTimestamp().format(formatter),
                    m.getValue(),
                    m.getDevice() != null ? m.getDevice().getName() : ""));
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
