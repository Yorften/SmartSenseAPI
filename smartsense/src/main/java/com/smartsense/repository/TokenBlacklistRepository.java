package com.smartsense.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartsense.model.TokenBlacklist;

public interface TokenBlacklistRepository extends MongoRepository<TokenBlacklist, String> {

    boolean existsByToken(String token);

    long deleteByCreatedAtBefore(LocalDateTime date);

}
