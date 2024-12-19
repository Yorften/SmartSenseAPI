package com.smartsense.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "token_blacklist")
public class TokenBlacklist {

    @Id
    private String id;

    private String token;

    @CreatedDate
    private LocalDateTime createdAt;

}
