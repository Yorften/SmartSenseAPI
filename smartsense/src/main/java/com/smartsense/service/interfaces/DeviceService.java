package com.smartsense.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.device.UpdateDeviceDTO;

/**
 * Service interface for Device entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface DeviceService {

   DeviceDTO getDeviceById(String id);

   DeviceDTO getDeviceById(String id, String... with);

   Page<DeviceDTO> getAllDevices(Pageable pageable, String search, String albumId);

   Page<DeviceDTO> getAllDevices(Pageable pageable, String search, String albumId, String... with);

   Page<DeviceDTO> getAllZoneDevices(Pageable pageable, String id);
   
   DeviceDTO addDevice(DeviceDTO Device);

   public DeviceDTO updateDevice(String DeviceId, UpdateDeviceDTO Device);

   public void deleteDeviceById(String DeviceId);
}
