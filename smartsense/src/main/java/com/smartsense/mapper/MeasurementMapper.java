package com.smartsense.mapper;

import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.model.Measurement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MeasurementMapper {

    private final DeviceMapper deviceMapper;
    private final List<String> VALID_INCLUDES = Arrays.asList("device");

    public void verifyIncludes(String... with) throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Inclusion invalide: " + include);
            }
        }
    }

    public Measurement toEntity(MeasurementDTO measurementDTO) {
        return Measurement.builder()
                .value(measurementDTO.getValue())
                .timestamp(measurementDTO.getTimestamp())
                .device(measurementDTO.getDevice() != null ? deviceMapper.toEntity(measurementDTO.getDevice()) : null)
                .build();
    }

    public MeasurementDTO toDto(Measurement measurement) {
        return MeasurementDTO.builder()
                .value(measurement.getValue())
                .timestamp(measurement.getTimestamp())
                .device(deviceMapper.toDto(measurement.getDevice()))
                .build();
    }

    public MeasurementDTO toDto(Measurement measurement, String... with) {

        List<String> includesList = Arrays.asList(with);

        MeasurementDTO.MeasurementDTOBuilder builder = MeasurementDTO.builder()
                .value(measurement.getValue())
                .timestamp(measurement.getTimestamp());

        if (includesList.contains("device") && measurement.getDevice() != null) {
            builder.device(deviceMapper.toDto(measurement.getDevice()));
        }

        return builder.build();
    }

    public List<MeasurementDTO> toDtoList(List<Measurement> measurements) {
        return measurements.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<MeasurementDTO> toDtoList(List<Measurement> measurements, String... with) {

        return measurements.stream()
                .map(measurement -> toDto(measurement, with))
                .collect(Collectors.toList());
    }
}
