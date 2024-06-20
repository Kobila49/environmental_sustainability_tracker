package igor.kos.est.service.implementation;

import igor.kos.est.dto.response.JwtResponse;
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

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${service.security.jwt.refresh-expiration-time}")
    private long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshToken createOrUpdateRefreshToken(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(format("User not found with email: {0}", email)));

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

    public JwtResponse getNewTokenViaRefreshToken(String token) {
        RefreshToken refreshToken = findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException(format("Refresh token not found for token: {0}", token)));
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException("Refresh token is expired. Please make a new login..!");
        }

        String newToken = jwtService.generateToken(refreshToken.getUser());
        return new JwtResponse(newToken, refreshToken.getToken());
    }
}
