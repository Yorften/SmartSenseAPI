package com.smartsense.controller.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.service.interfaces.DeviceService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing Device entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/user/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(
            @PathVariable String id,
            @RequestParam(required = false) String[] with) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DeviceDTO>> getAllDevices(
            Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String zoneId,
            @RequestParam(required = false) String[] with) {
        return ResponseEntity.ok(deviceService.getAllDevices(pageable, search, zoneId));
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<Page<DeviceDTO>> getAllZoneDevices(
            Pageable pageable,
            @PathVariable String zoneId) {
        return ResponseEntity.ok(deviceService.getAllZoneDevices(pageable, zoneId));
    }

}
