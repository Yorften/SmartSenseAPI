package com.smartsense.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.exceptions.DuplicateResourceException;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.ZoneMapper;
import com.smartsense.model.Device;
import com.smartsense.model.Zone;
import com.smartsense.repository.DeviceRepository;
import com.smartsense.repository.ZoneRepository;
import com.smartsense.dto.zone.UpdateZoneDTO;
import com.smartsense.service.interfaces.ZoneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Zone entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final DeviceRepository deviceRepository;
    private final ZoneMapper zoneMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public ZoneDTO getZoneById(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("zone not found"));
        return zoneMapper.toDto(zone);
    }

    @Override
    public ZoneDTO getZoneById(String id, String... with) {
        zoneMapper.verifyIncludes(with);
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("zone not found"));

        List<Device> devices = deviceRepository.findAllByZoneId(zone.getId());

        zone.setDevices(devices);

        return zoneMapper.toDto(zone, with);
    }

    @Override
    public Page<ZoneDTO> getAllZones(Pageable pageable, String name, String type, String location) {

        Query query = new Query();

        if (name != null && !name.isEmpty()) {
            log.info("name : " + name);
            query.addCriteria(Criteria.where("name").regex(name, "i"));
        }
        if (type != null && !type.isEmpty()) {
            log.info("type : " + type);
            query.addCriteria(Criteria.where("type").regex(type, "i"));
        }
        if (location != null && !location.isEmpty()) {
            log.info("location : " + location);
            query.addCriteria(Criteria.where("location").regex(location, "i"));
        }

        query.with(pageable);

        List<Zone> zones = mongoTemplate.find(query, Zone.class);
        long count = mongoTemplate.count(query.skip(pageable.getOffset()).limit(pageable.getPageSize()), Zone.class);

        List<ZoneDTO> zoneDTOs = zones.stream()
                .map(zone -> zoneMapper.toDto(zone))
                .collect(Collectors.toList());

        return new PageImpl<>(zoneDTOs, pageable, count);
    }

    @Override
    public Page<ZoneDTO> getAllZones(Pageable pageable, String title, String artist, String location,
            String... with) {
        zoneMapper.verifyIncludes(with);
        Query query = new Query();

        if (title != null && !title.isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(title, "i"));
        }
        if (artist != null && !artist.isEmpty()) {
            query.addCriteria(Criteria.where("artist").regex(artist, "i"));
        }
        if (location != null && !location.isEmpty()) {
            log.info("location : " + location);
            query.addCriteria(Criteria.where("location").regex(location, "i"));
        }

        query.with(pageable);

        List<Zone> zones = mongoTemplate.find(query, Zone.class);
        long count = mongoTemplate.count(query.skip(pageable.getOffset()).limit(pageable.getPageSize()), Zone.class);

        List<ZoneDTO> zoneDTOs = zones.stream()
                .map(zone -> zoneMapper.toDto(zone, with))
                .collect(Collectors.toList());

        return new PageImpl<>(zoneDTOs, pageable, count);
    }

    @Override
    public ZoneDTO addZone(ZoneDTO zoneDTO) {
        if (zoneRepository.findByName(zoneDTO.getName()).isPresent())
            throw new DuplicateResourceException("Zone with this name already exists");
        Zone zone = zoneMapper.toEntity(zoneDTO);
        return zoneMapper.toDto(zoneRepository.save(zone));
    }

    @Override
    public ZoneDTO updateZone(String zoneId, UpdateZoneDTO zoneDTO) {
        Zone zoneDB = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("zone not found"));

        if (zoneDTO.getName() != null && !zoneDTO.getName().isEmpty()) {
            zoneDB.setName(zoneDTO.getName());
        }
        if (zoneDTO.getType() != null && !zoneDTO.getType().isEmpty()) {
            zoneDB.setType(zoneDTO.getType());
        }
        if (zoneDTO.getLocation() != null && !zoneDTO.getLocation().isEmpty()) {
            zoneDB.setLocation(zoneDTO.getLocation());
        }

        return zoneMapper.toDto(zoneRepository.save(zoneDB));
    }

    @Override
    public void deleteZoneById(String zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
        zoneRepository.delete(zone);
    }

}
