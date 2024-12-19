package com.smartsense.mapper;

import com.smartsense.exceptions.InvalidDataException;
import org.springframework.stereotype.Component;
import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.model.Alert;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlertMapper {

    private final DeviceMapper deviceMapper;
    private final List<String> VALID_INCLUDES = Arrays.asList("device");
    
    public void verifyIncludes(String... with) throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);
        
        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }
    }
    
    public AlertDTO toDto(Alert alert) {
        if (alert == null) return null;
        
        return AlertDTO.builder()
            .severity(alert.getSeverity())
            .message(alert.getMessage())
            .build();
    }
    
    public Alert toEntity(AlertDTO dto) {
        if (dto == null) return null;
        
        return Alert.builder()
         //   .id(dto.getId())
            .severity(dto.getSeverity())
            .message(dto.getMessage())
            .timestamp(dto.getTimestamp())
            .build();
    }
    
    public AlertDTO toDto(Alert alert, String... with) {
        if (alert == null) return null;
        
        List<String> includesList = Arrays.asList(with);

        AlertDTO.AlertDTOBuilder builder = AlertDTO.builder()
            .severity(alert.getSeverity())
            .message(alert.getMessage())
            .timestamp(alert.getTimestamp());

        if (includesList.contains("device") && alert.getDevice() != null) {
            // builder.device(deviceMapper.toDto(alert.getDevice()));
        }

        return builder.build();
    }
    
    public List<AlertDTO> toDtoList(List<Alert> alerts) {
        return alerts.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    public List<AlertDTO> toDtoList(List<Alert> alerts, String... with) {
        return alerts.stream()
            .map(alert -> toDto(alert, with))
            .collect(Collectors.toList());
    }
}
