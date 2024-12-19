package com.smartsense.service;

import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.service.interfaces.AlertService;
import com.smartsense.repository.AlertRepository;
import com.smartsense.mapper.AlertMapper;
import com.smartsense.model.Alert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    @Override
    public AlertDTO getAlertById(String id) {
        return alertMapper.toDto(findAlertById(id));
    }

    @Override
    public AlertDTO getAlertById(String id, String... with) throws InvalidDataException {
        alertMapper.verifyIncludes(with);
        
        Alert alert = alertRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));
            
        return alertMapper.toDto(alert, with);
    }

    @Override
    public AlertDTO addAlert(AlertDTO Alert) {
         Alert alert=alertRepository.save( alertMapper.toEntity(Alert));

         return alertMapper.toDto(alert,"device");
    }

    @Override
    public Page<AlertDTO> getAllAlerts(Pageable pageable) {
        return alertRepository.findByRemovedAtNull(pageable)
            .map(alertMapper::toDto);
    }

    @Override
    public Page<AlertDTO> getAllAlerts(Pageable pageable, String... with) throws InvalidDataException {
        alertMapper.verifyIncludes(with);
        
        return alertRepository.findByRemovedAtNull(pageable)
            .map(alert -> alertMapper.toDto(alert, with));
    }

    @Override
    public Page<AlertDTO> searchAlerts(String query, Pageable pageable) {
        return alertRepository.findByMessageContainingIgnoreCaseAndRemovedAtNull(query, pageable)
            .map(alertMapper::toDto);
    }

    private Alert findAlertById(String id) {
        return alertRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));
    }

}
