package igor.kos.est.service;

import igor.kos.est.TestUtil;
import igor.kos.est.dto.request.LoginUserRequest;
import igor.kos.est.dto.request.UserDataRequest;
import igor.kos.est.dto.response.JwtResponse;
import igor.kos.est.entity.RefreshToken;
import igor.kos.est.entity.User;
import igor.kos.est.repository.UserRepository;
import igor.kos.est.service.implementation.AuthenticationService;
import igor.kos.est.service.implementation.JwtService;
import igor.kos.est.service.implementation.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class AuthenticationServiceTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationServiceTest.class);


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        log.info("Testing signup");
        UserDataRequest input = TestUtil.getMockUserDataRequest();

        User mockUser = TestUtil.getMockUser();

        when(userRepository.existsByEmail(input.email())).thenReturn(false);
        when(passwordEncoder.encode(input.password())).thenReturn(mockUser.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User actual = authenticationService.signup(input);

        assertEquals(mockUser.getUsername(), actual.getUsername());
        assertEquals(mockUser.getPassword(), actual.getPassword());
    }

    @Test
    void testAuthenticate() {
        log.info("Testing authenticate");
        LoginUserRequest input = new LoginUserRequest("test@email.com", "password");

        JwtResponse jwtResponse = new JwtResponse("validToken", "refreshToken");

        User mockUser = TestUtil.getMockUser();

        Authentication authentication = new UsernamePasswordAuthenticationToken(input.email(), input.password());

        when(userRepository.findByEmail(input.email())).thenReturn(Optional.of(mockUser));
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(any())).thenReturn("validToken");
        when(refreshTokenService.createOrUpdateRefreshToken(anyString())).thenReturn(new RefreshToken("refreshToken", null, null));

        JwtResponse actual = authenticationService.authenticate(input);

        assertEquals(jwtResponse.token(), actual.token());
        assertEquals(jwtResponse.refreshToken(), actual.refreshToken());
    }
}
