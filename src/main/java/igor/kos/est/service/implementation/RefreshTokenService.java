package igor.kos.est.service.implementation;

import igor.kos.est.entity.RefreshToken;
import igor.kos.est.exceptions.RefreshTokenExpiredException;
import igor.kos.est.repository.RefreshTokenRepository;
import igor.kos.est.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${service.security.jwt.refresh-expiration-time}")
    private long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createOrUpdateRefreshToken(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(STR."User not found with email: %s\{email}"));

        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUser(user);

        RefreshToken refreshToken;
        if (existingRefreshToken.isPresent()) {
            refreshToken = existingRefreshToken.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpiration));
        } else {
            refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(refreshExpiration))
                    .build();
        }
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(String token) {
        RefreshToken refreshToken = findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(STR."Refresh token not found: %s\{token}"));
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException(STR."Refresh token is expired. Please make a new login..!\{refreshToken.getToken()}");
        }
        return refreshToken;
    }
}
