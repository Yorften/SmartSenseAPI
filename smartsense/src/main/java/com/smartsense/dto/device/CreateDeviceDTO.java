package com.smartsense.dto.device;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.smartsense.dto.zone.ZoneDTO;
import com.smartsense.model.enums.DeviceType;
import com.smartsense.model.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDeviceDTO {

    @NotNull(message = "Device name cannot be null")
    @Size(min = 3, max = 100, message = "Device name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Device type cannot be null")
    private DeviceType type;

    @NotNull(message = "Device status cannot be null")
    private Status status;

    @NotNull(message = "Zone cannot be null")
    private ZoneDTO zone;

}
