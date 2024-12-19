package com.smartsense.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.model.Device;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeviceMapper {

    private final AlertMapper alertMapper;
    private final MeasurementMapper measurementMapper;
    private final ZoneMapper zoneMapper;
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
        return Device.builder()
                .name(deviceDTO.getName())
                .type(deviceDTO.getType())
                .status(deviceDTO.getStatus())
                .lastCommunication(deviceDTO.getLastCommunication())
                .zone(zoneMapper.toEntity(deviceDTO.getZone()))
                .build();
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
            builder.zone(zoneMapper.toDto(device.getZone()));
        }

        if (includesList.contains("measurements") && !device.getMeasurements().isEmpty()) {
            builder.measurements(
                    device.getMeasurements().stream().map(measurementMapper::toDto).collect(Collectors.toList()));
        }

        if (includesList.contains("alerts") && !device.getAlerts().isEmpty()) {
            builder.alerts(device.getAlerts().stream().map(alertMapper::toDto).collect(Collectors.toList()));
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
