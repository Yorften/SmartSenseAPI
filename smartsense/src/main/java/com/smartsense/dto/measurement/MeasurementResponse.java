package com.smartsense.dto.measurement;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.model.enums.Severity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementResponse {
    
    
    private Double value;
        
    private Severity severity;

    private String message;

    private LocalDateTime timestamp;

    private DeviceDTO device;
    
}
    

