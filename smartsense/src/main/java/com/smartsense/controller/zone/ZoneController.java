package com.smartsense.controller.zone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.service.interfaces.DeviceService;
import com.smartsense.service.interfaces.ZoneService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing Zone entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/user/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final DeviceService deviceService;
    private final ZoneService zoneService;

    @GetMapping()
    public Page<ZoneDTO> getAllZones(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String location) {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.by("ID").ascending());
        return zoneService.getAllZones(pageable, name, type, location, "devices");
    }

    @GetMapping("/{id}/devices")
    public Page<DeviceDTO> getAllZoneDevices(
            @PathVariable("id") String zoneId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.by("ID").ascending());
        return deviceService.getAllZoneDevices(pageable, zoneId);
    }

    @GetMapping("/{id}")
    public ZoneDTO getZone(
            @PathVariable("id") String zoneId) {
        return zoneService.getZoneById(zoneId, "devices");
    }

}
