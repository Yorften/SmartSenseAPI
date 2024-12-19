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

    public MeasurementDTO toDto(Measurement measurement) {
        if (measurement == null) return null;

        return MeasurementDTO.builder()
                //.id(measurement.getId())
                .value(measurement.getValue())
                .timestamp(measurement.getTimestamp())
               // .device(deviceMapper.toDto(measurement.getDevice()))
                .build();
    }

    public Measurement toEntity(MeasurementDTO dto) {
        if (dto == null) return null;

        return Measurement.builder()
                .value(dto.getValue())
                .timestamp(dto.getTimestamp())
             //   .device(dto.getDevice() != null ? deviceMapper.toEntity(dto.getDevice()) : null)
                .build();
    }

    public MeasurementDTO toDto(Measurement measurement, String... with) {
        if (measurement == null) return null;

        List<String> includesList = Arrays.asList(with);

        MeasurementDTO.MeasurementDTOBuilder builder = MeasurementDTO.builder()
                .value(measurement.getValue())
                .timestamp(measurement.getTimestamp());

        if (includesList.contains("device") && measurement.getDevice() != null) {
          //  builder.device(deviceMapper.toDto(measurement.getDevice()));
        }

        return builder.build();
    }

    public List<MeasurementDTO> toDtoList(List<Measurement> measurements) {
        if (measurements == null) return null;
        return measurements.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<MeasurementDTO> toDtoList(List<Measurement> measurements, String... with) {
        if (measurements == null) return null;
        return measurements.stream()
                .map(measurement -> toDto(measurement, with))
                .collect(Collectors.toList());
    }
}
