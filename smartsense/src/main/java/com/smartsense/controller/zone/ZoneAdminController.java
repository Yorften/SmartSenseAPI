package com.smartsense.controller.zone;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartsense.dto.zone.UpdateZoneDTO;
import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.service.interfaces.ZoneService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing Zone entities by the admin.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/admin/zones")
@RequiredArgsConstructor
public class ZoneAdminController {

    private final ZoneService zoneService;

    @PostMapping
    public ZoneDTO saveZone(
            @RequestBody @Valid ZoneDTO zoneDTO) {
        return zoneService.addZone(zoneDTO);
    }

    @PutMapping("/{id}")
    public ZoneDTO updateZoneDTO(
            @RequestBody @Valid UpdateZoneDTO updateZoneDTO,
            @PathVariable("id") String zoneId) {
        return zoneService.updateZone(zoneId, updateZoneDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateZoneDTO(@PathVariable("id") String zoneId) {
        zoneService.deleteZoneById(zoneId);

        return ResponseEntity.noContent().build();
    }

}
