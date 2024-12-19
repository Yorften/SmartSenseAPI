package com.smartsense.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartsense.model.TokenBlacklist;

public interface TokenBlacklistRepository extends MongoRepository<TokenBlacklist, String> {

    boolean existsByToken(String token);

}
