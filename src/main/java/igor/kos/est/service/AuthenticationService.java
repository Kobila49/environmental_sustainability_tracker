package igor.kos.est.service;

import igor.kos.est.dto.LoginUserDTO;
import igor.kos.est.dto.RegisterUserDTO;
import igor.kos.est.entity.User;
import igor.kos.est.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDTO input) {
        final var user = User.builder()
                .firstName(input.firstName())
                .lastName(input.lastName())
                .email(input.email())
                .password(passwordEncoder.encode(input.password()))
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return userRepository.findByEmail(input.email())
                .orElseThrow();
    }
}
