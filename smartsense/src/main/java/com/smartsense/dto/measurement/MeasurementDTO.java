package com.smartsense.dto.measurement;

import com.smartsense.dto.device.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementDTO {
    
    
    @NotNull(message = "La valeur ne peut pas être nulle")
    private Double value;
    
    @NotNull(message = "Le timestamp ne peut pas être nul")
    private LocalDateTime timestamp;
    @NotNull(message = "Le device ne peut pas être nul")
    private DeviceDTO device;
}
    

