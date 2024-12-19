package com.smartsense.dto.alert;

import com.smartsense.dto.device.DeviceDTO;
import com.smartsense.model.enums.Severity;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDTO {
    

    @NotNull(message = "La sévérité ne peut pas être nulle")
    private Severity severity;

    @NotNull(message = "Le message ne peut pas être nul")
    @Size(min = 5, max = 255, message = "Le message doit contenir entre 5 et 255 caractères")
    private String message;

    @NotNull(message = "Le timestamp ne peut pas être nul")
    private LocalDateTime timestamp;
  
    @NotNull(message = "Le device ne peut pas être nul")

    private DeviceDTO device;

}
