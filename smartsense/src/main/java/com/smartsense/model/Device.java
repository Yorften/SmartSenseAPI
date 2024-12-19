package com.smartsense.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.smartsense.model.enums.DeviceType;
import com.smartsense.model.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Device entity in the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "devices")
public class Device {

    @Id
    @Indexed
    private String id;

    private String name;

    private DeviceType type;

    private Status status;

    @LastModifiedDate
    private LocalDateTime lastCommunication;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime removedAt;

    @DBRef
    private Zone zone;

    @Builder.Default
    private List<Measurement> measurements = new ArrayList<>();

    @Builder.Default
    private List<Alert> alerts = new ArrayList<>();

}
