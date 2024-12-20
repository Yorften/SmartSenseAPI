package com.smartsense.dto.device;

import java.time.LocalDateTime;
import java.util.List;

import com.smartsense.dto.alert.AlertDTO;
import com.smartsense.dto.measurement.MeasurementDTO;
import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.model.enums.DeviceType;
import com.smartsense.model.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDTO {

    private String id;

    @NotNull(message = "Device name cannot be null")
    @Size(min = 3, max = 100, message = "Device name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Device type cannot be null")
    private DeviceType type;

    @NotNull(message = "Device status cannot be null")
    private Status status;

    private LocalDateTime lastCommunication;

    @NotNull(message = "Zone cannot be null")
    private ZoneDTO zone;

    private List<MeasurementDTO> measurements;

    private List<AlertDTO> alerts;

}
