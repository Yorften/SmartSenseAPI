package com.smartsense.service;

import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.dto.zone.UpdateZoneDTO;
import com.smartsense.exceptions.DuplicateResourceException;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.ZoneMapper;
import com.smartsense.model.Zone;
import com.smartsense.repository.DeviceRepository;
import com.smartsense.repository.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZoneServiceImplTest {

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private ZoneMapper zoneMapper;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ZoneServiceImpl zoneService;

    private Zone zone;
    private ZoneDTO zoneDTO;

    @BeforeEach
    void setUp() {
        zone = new Zone();
        zone.setName("TestZone");
        zone.setType("Test");
        zone.setLocation("TestLocation");

        zoneDTO = new ZoneDTO();
        zoneDTO.setName("TestZone");
        zoneDTO.setType("Test");
        zoneDTO.setLocation("TestLocation");
    }

    @Test
    void getZoneById_ShouldReturnZone_WhenExists() {
        when(zoneRepository.findById("1")).thenReturn(Optional.of(zone));
        when(zoneMapper.toDto(any(Zone.class))).thenReturn(zoneDTO);

        ZoneDTO result = zoneService.getZoneById("1");

        assertNotNull(result);
        verify(zoneRepository).findById("1");
    }

    @Test
    void addZone_ShouldThrowException_WhenNameExists() {
        when(zoneRepository.findByName("TestZone")).thenReturn(Optional.of(zone));

        assertThrows(DuplicateResourceException.class,
                () -> zoneService.addZone(zoneDTO));
    }

    @Test
    void addZone_ShouldSaveAndReturnZone_WhenNameDoesNotExist() {
        when(zoneRepository.findByName("TestZone")).thenReturn(Optional.empty());
        when(zoneMapper.toEntity(any(ZoneDTO.class))).thenReturn(zone);
        when(zoneRepository.save(any(Zone.class))).thenReturn(zone);
        when(zoneMapper.toDto(any(Zone.class))).thenReturn(zoneDTO);

        ZoneDTO result = zoneService.addZone(zoneDTO);

        assertNotNull(result);
        verify(zoneRepository).save(any(Zone.class));
    }

    @Test
    void updateZone_ShouldUpdateAndReturnZone_WhenExists() {
        UpdateZoneDTO updateDTO = new UpdateZoneDTO();
        updateDTO.setName("UpdatedName");

        when(zoneRepository.findById("1")).thenReturn(Optional.of(zone));
        when(zoneRepository.save(any(Zone.class))).thenReturn(zone);
        when(zoneMapper.toDto(any(Zone.class))).thenReturn(zoneDTO);

        ZoneDTO result = zoneService.updateZone("1", updateDTO);

        assertNotNull(result);
        verify(zoneRepository).save(any(Zone.class));
    }
}