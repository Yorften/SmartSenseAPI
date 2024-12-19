package com.smartsense.dto.zone;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.smartsense.dto.device.DeviceDTO;

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
public class ZoneDTO {

    @NotNull(message = "Zone name cannot be null")
    @Size(min = 3, max = 100, message = "Zone name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Zone type cannot be null")
    @Size(min = 3, max = 100, message = "Zone type must be between 3 and 100 characters")
    private String type;

    @NotNull(message = "Zone location cannot be null")
    @Size(min = 3, max = 100, message = "Zone location must be between 3 and 100 characters")
    private String location;

    private List<DeviceDTO> devices;

}
