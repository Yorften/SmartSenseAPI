package com.smartsense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.device.UpdateDeviceDTO;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.DeviceMapper;
import com.smartsense.model.Device;
import com.smartsense.model.Zone;
import com.smartsense.repository.DeviceRepository;
import com.smartsense.service.interfaces.DeviceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Override
    public DeviceDTO getDeviceById(String id) {
        log.info("Fetching device with id: {}", id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return deviceMapper.toDto(device);
    }

    @Override
    public DeviceDTO getDeviceById(String id, String... with) {
        log.info("Fetching device with id: {} and includes: {}", id, with);
        deviceMapper.verifyIncludes(with);
        
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
                
        return deviceMapper.toDto(device, with);
    }

    // ... previous code ...

    @Override   public Page<DeviceDTO> getAllDevices(Pageable pageable, String search, String zoneId) {
        log.info("Fetching all devices with search: {} and zoneId: {}", search, zoneId);
        Page<Device> devices;
        if (search != null && !search.isEmpty() && zoneId != null && !zoneId.isEmpty()) {
            devices = deviceRepository.findByNameContainingAndZoneId(search, zoneId, pageable);
        } else if (search != null && !search.isEmpty()) {
            devices = deviceRepository.findByNameContaining(search, pageable);
        } else if (zoneId != null && !zoneId.isEmpty()) {
            devices = deviceRepository.findByZoneId(zoneId, pageable);
        } else {
            devices = deviceRepository.findAll(pageable);
        }
        return devices.map(deviceMapper::toDto);
    }
    @Override
    public Page<DeviceDTO> getAllDevices(Pageable pageable, String search, String zoneId, String... with) {
        log.info("Fetching all devices with search: {}, zoneId: {} and includes: {}", search, zoneId, with);
        deviceMapper.verifyIncludes(with);

        Page<Device> devices;
        if (search != null && !search.isEmpty() && zoneId != null && !zoneId.isEmpty()) {
            devices = deviceRepository.findByNameContainingAndZoneId(search, zoneId, pageable);
        } else if (search != null && !search.isEmpty()) {
            devices = deviceRepository.findByNameContaining(search, pageable);
        } else if (zoneId != null && !zoneId.isEmpty()) {
            devices = deviceRepository.findByZoneId(zoneId, pageable);
        } else {
            devices = deviceRepository.findAll(pageable);
        }
        return devices.map(device -> deviceMapper.toDto(device, with));
    }
// ... rest of the code ...

    @Override
    public Page<DeviceDTO> getAllZoneDevices(Pageable pageable, String zoneId) {
        log.info("Fetching all devices for zone: {}", zoneId);
        Page<Device> devices = deviceRepository.findByZoneId(zoneId, pageable);
        return devices.map(device -> deviceMapper.toDto(device));
    }

    @Override
    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        log.info("Adding new device: {}", deviceDTO.getName());
        Device device = deviceMapper.toEntity(deviceDTO);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toDto(savedDevice);
    }

    @Override
    public DeviceDTO updateDevice(String deviceId, UpdateDeviceDTO deviceDTO) {
        log.info("Updating device with id: {}", deviceId);
        Device existingDevice = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + deviceId));

        if (deviceDTO.getName() != null) {
            existingDevice.setName(deviceDTO.getName());
        }
        if (deviceDTO.getType() != null) {
            existingDevice.setType(deviceDTO.getType());
        }
        if (deviceDTO.getStatus() != null) {
            existingDevice.setStatus(deviceDTO.getStatus());
        }
        if (deviceDTO.getZone() != null) {
            Zone zone = Zone.builder()
                    .name(deviceDTO.getZone().getName())
                    .type(deviceDTO.getZone().getType())
                    .location(deviceDTO.getZone().getLocation())
                    .build();
            existingDevice.setZone(zone);
        }

        Device updatedDevice = deviceRepository.save(existingDevice);
        return deviceMapper.toDto(updatedDevice);
    }

    @Override
    public void deleteDeviceById(String deviceId) {
        log.info("Deleting device with id: {}", deviceId);
        if (!deviceRepository.existsById(deviceId)) {
            throw new ResourceNotFoundException("Device not found with id: " + deviceId);
        }
        deviceRepository.deleteById(deviceId);
    }
}
