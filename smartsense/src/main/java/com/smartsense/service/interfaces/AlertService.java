package com.smartsense.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.dto.alert.UpdateAlertDTO;

/**
 * Service interface for Alert entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface AlertService {

   AlertDTO getAlertById(String id);

   AlertDTO getAlertById(String id, String... with);

   Page<AlertDTO> getAllCategories(Pageable pageable, String title, String artist, Integer year);

   Page<AlertDTO> getAllCategories(Pageable pageable, String title, String artist, Integer year, String... with);

   AlertDTO addAlert(AlertDTO Alert);

   AlertDTO updateAlert(String AlertId, UpdateAlertDTO Alert);

   void deleteAlertById(String AlertId);
}
