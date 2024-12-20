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
import com.smartsense.repository.ZoneRepository;
import com.smartsense.service.interfaces.DeviceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final ZoneRepository zoneRepository;

    @Override
    public DeviceDTO getDeviceById(String id) {
        log.info("Fetching device with id: {}", id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return deviceMapper.toDto(device);
    }


    @Override
    public Page<DeviceDTO> getAllDevices(Pageable pageable, String search, String zoneId) {
        return deviceRepository.findAll(pageable).map(deviceMapper::toDto);
    }


    @Override
    public Page<DeviceDTO> getAllZoneDevices(Pageable pageable, String zoneId) {
        log.info("Fetching all devices for zone: {}", zoneId);
     return   deviceRepository.findByZoneId(zoneId, pageable).map(deviceMapper::toDto);
    
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
        if (deviceDTO.getZone() != null && deviceDTO.getZone().getId() != null) {
            Zone zone = zoneRepository.findById(deviceDTO.getZone().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone not found with id: " + deviceDTO.getZone().getId()));
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
