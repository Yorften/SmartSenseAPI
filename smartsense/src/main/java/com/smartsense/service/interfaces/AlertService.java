package com.smartsense.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartsense.dto.alert.AlertDTO;

/**
 * Service interface for Alert entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface AlertService {

   AlertDTO getAlertById(String id);

   AlertDTO getAlertById(String id, String... with);

   AlertDTO addAlert(AlertDTO Alert);

   Page<AlertDTO> getAllAlerts(Pageable pageable);

   Page<AlertDTO> getAllAlerts(Pageable pageable, String... with);

   Page<AlertDTO> searchAlerts(String query, Pageable pageable);
}
