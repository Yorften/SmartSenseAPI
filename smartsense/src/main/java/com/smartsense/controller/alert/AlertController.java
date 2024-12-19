package com.smartsense.controller.alert;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.service.interfaces.AlertService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController 
@RequestMapping("/api/user/alerts")
@RequiredArgsConstructor
public class AlertController {


    private final AlertService alertService;
    @GetMapping
    public ResponseEntity<Page<AlertDTO>> getAllAlerts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(alertService.getAllAlerts(pageable, "device"));
    }


}
