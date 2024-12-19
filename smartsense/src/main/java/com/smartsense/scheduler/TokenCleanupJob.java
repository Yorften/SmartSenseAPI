package com.smartsense.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartsense.config.SecurityConstants;
import com.smartsense.repository.TokenBlacklistRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupJob {

    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final SecurityConstants securityConstants;

    /**
     * Scheduled task to clean up expired tokens.
     * Runs every 2 hours.
     */
    @Scheduled(cron = "0 0 */2 * * *")
    public void cleanUpExpiredTokens() {
        log.info("Starting token cleanup job...");

        long JwtExpirationInSeconds = securityConstants.getJwtExpiration() / 1000;

        if (JwtExpirationInSeconds < 1) {
            log.warn("JWT expiration is less than 1 second, skipping cleanup.");
            return;
        }

        LocalDateTime cutoffDate = LocalDateTime.now().minusSeconds(JwtExpirationInSeconds);

        try {
            long deletedCount = tokenBlacklistRepository.deleteByCreatedAtBefore(cutoffDate);
            log.info("Token cleanup job completed. {} expired tokens deleted.", deletedCount);
        } catch (Exception e) {
            log.error("Error during token cleanup job: {}", e.getMessage(), e);
        }
    }
}
