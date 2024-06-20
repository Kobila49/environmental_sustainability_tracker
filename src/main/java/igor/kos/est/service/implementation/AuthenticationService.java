package igor.kos.est.service.implementation;

import igor.kos.est.dto.request.LoginUserRequest;
import igor.kos.est.dto.request.UserDataRequest;
import igor.kos.est.dto.response.JwtResponse;
import igor.kos.est.entity.RefreshToken;
import igor.kos.est.entity.User;
import igor.kos.est.enums.Role;
import igor.kos.est.exceptions.NoEntityFoundException;
import igor.kos.est.exceptions.UserAlreadyExistsException;
import igor.kos.est.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public User signup(UserDataRequest input) {
        log.info("Registering new user with email: {}", input.email());
        if (userRepository.existsByEmail(input.email())) {
            throw new UserAlreadyExistsException(format("User with email {0} already exists.", input.email()));
        }
        User user = createUser(input);
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with email: {}", savedUser.getEmail());
        return user;
    }

    public JwtResponse authenticate(LoginUserRequest input) {
        log.info("Authenticating user with email: {}", input.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new NoEntityFoundException(format("User not found with email: {0}", input.email())));
        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(user.getEmail());
        log.info("User authenticated successfully with email: {}", user.getEmail());
        return new JwtResponse(jwtToken, refreshToken.getToken());
    }

    private User createUser(UserDataRequest input) {
        return User.builder()
                .firstName(input.firstName())
                .lastName(input.lastName())
                .email(input.email())
                .dateOfBirth(input.dateOfBirth())
                .role(Role.USER)
                .password(passwordEncoder.encode(input.password()))
                .build();
    }
}
