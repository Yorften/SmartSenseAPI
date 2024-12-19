package com.smartsense.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.model.Device;
import com.smartsense.model.Zone;
import com.smartsense.repository.DeviceRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZoneMapper {

    private final DeviceMapper deviceMapper;
    private final DeviceRepository deviceRepository;
    private final List<String> VALID_INCLUDES = Arrays.asList("devices");

    public void verifyIncludes(String... with)
            throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }
    }

    public Zone toEntity(ZoneDTO zoneDTO) {
        return Zone.builder()
                .name(zoneDTO.getName())
                .type(zoneDTO.getType())
                .location(zoneDTO.getLocation())
                .build();
    }

    public ZoneDTO toDto(Zone zone) {
        return ZoneDTO.builder()
                .name(zone.getName())
                .type(zone.getType())
                .location(zone.getLocation())
                .build();
    }

    public List<ZoneDTO> toDtoList(List<Zone> zones) {
        return zones.stream()
                .map(zone -> toDto(zone))
                .collect(Collectors.toList());
    }

    public ZoneDTO toDto(Zone zone, String... with) {
        List<String> includesList = Arrays.asList(with);

        List<DeviceDTO> deviceDTOs = null;

        ZoneDTO.ZoneDTOBuilder builder = ZoneDTO.builder()
                .name(zone.getName())
                .type(zone.getType())
                .location(zone.getLocation());

        if (includesList.contains("devices") && !zone.getDevices().isEmpty()) {
            List<Device> devices = deviceRepository.findAllByZoneId(zone.getId()) ;
            builder.devices(devices.stream().map(deviceMapper::toDto).collect(Collectors.toList()));
        }

        return ZoneDTO.builder()
                .name(zone.getName())
                .type(zone.getType())
                .location(zone.getLocation())
                .devices(deviceDTOs)
                .build();

    }

    public List<ZoneDTO> toDtoList(List<Zone> zones, String... with) {
        return zones.stream()
                .map(zone -> toDto(zone, with))
                .collect(Collectors.toList());
    }
}
