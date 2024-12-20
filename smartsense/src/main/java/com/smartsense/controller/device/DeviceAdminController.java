package com.smartsense.controller.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.device.UpdateDeviceDTO;
import com.smartsense.service.interfaces.DeviceService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

/**
 * REST controller for managing Device entities by the admin.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/admin/devices")
@RequiredArgsConstructor
public class DeviceAdminController {

    private final DeviceService deviceService;


  
    @PostMapping
    public ResponseEntity<DeviceDTO> addDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        return new ResponseEntity<>(deviceService.addDevice(deviceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(
            @PathVariable String id,
            @RequestBody UpdateDeviceDTO deviceDTO) {
        return ResponseEntity.ok(deviceService.updateDevice(id, deviceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }
}
