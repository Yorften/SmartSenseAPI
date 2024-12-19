package com.smartsense.dto.zone;

import javax.validation.constraints.Size;

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
public class UpdateZoneDTO {

    @Size(min = 3, max = 100, message = "Zone name must be between 3 and 100 characters")
    private String name;

    @Size(min = 3, max = 100, message = "Zone type must be between 3 and 100 characters")
    private String type;

    @Size(min = 3, max = 100, message = "Zone location must be between 3 and 100 characters")
    private String location;

}
