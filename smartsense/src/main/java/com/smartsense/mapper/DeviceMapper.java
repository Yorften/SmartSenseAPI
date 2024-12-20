package com.smartsense.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.model.Alert;
import com.smartsense.model.Device;
import com.smartsense.model.Measurement;
import com.smartsense.model.Zone;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeviceMapper {

    private final List<String> VALID_INCLUDES = Arrays.asList("measurements", "alerts", "zone");

    public void verifyIncludes(String... with) throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Inclusion invalide: " + include);
            }
        }
    }

    public Device toEntity(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            return null;
        }

        Device device = Device.builder()
                .name(deviceDTO.getName())
                .type(deviceDTO.getType())
                .status(deviceDTO.getStatus())
                .lastCommunication(deviceDTO.getLastCommunication())
                .build();

        if (deviceDTO.getZone() != null) {
            Zone zone = Zone.builder()
                    .id(deviceDTO.getZone().getId())
                    .name(deviceDTO.getZone().getName())
                    .type(deviceDTO.getZone().getType())
                    .location(deviceDTO.getZone().getLocation())
                    .build();
            device.setZone(zone);
        }

        return device;
    }

    public DeviceDTO toDto(Device device) {
        return DeviceDTO.builder()
                .name(device.getName())
                .type(device.getType())
                .status(device.getStatus())
                .lastCommunication(device.getLastCommunication())
                .build();
    }

    public DeviceDTO toDto(Device device, String... with) {
        List<String> includesList = Arrays.asList(with);

        DeviceDTO.DeviceDTOBuilder builder = DeviceDTO.builder()
                .name(device.getName())
                .type(device.getType())
                .status(device.getStatus())
                .lastCommunication(device.getLastCommunication());

        if (includesList.contains("zone") && device.getZone() != null) {
            Zone zone = device.getZone();
            builder.zone(ZoneDTO.builder()
                    .name(zone.getName())
                    .type(zone.getType())
                    .location(zone.getLocation())
                    .build());
        }

        if (includesList.contains("measurements") && !device.getMeasurements().isEmpty()) {
            List<Measurement> measurements = device.getMeasurements();
            builder.measurements(
                    measurements.stream()
                            .map(measurement -> MeasurementDTO.builder().value(measurement.getValue())
                                    .build())
                            .collect(Collectors.toList()));
        }

        if (includesList.contains("alerts") && !device.getAlerts().isEmpty()) {
            List<Alert> alerts = device.getAlerts();
            builder.alerts(alerts.stream()
                    .map(alert -> AlertDTO.builder().severity(alert.getSeverity()).message(alert.getMessage())
                            .timestamp(alert.getTimestamp()).build())
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    public List<DeviceDTO> toDtoList(List<Device> devices) {
        return devices.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> toDtoList(List<Device> devices, String... with) {
        return devices.stream()
                .map(device -> toDto(device, with))
                .collect(Collectors.toList());
    }

}
