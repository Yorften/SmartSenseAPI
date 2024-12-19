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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDTO {

    private String name;

    private DeviceType type;

    private Status status;

    private LocalDateTime lastCommunication;

    private ZoneDTO zone;

    private List<MeasurementDTO> measurements;

    private List<AlertDTO> alerts;

}
