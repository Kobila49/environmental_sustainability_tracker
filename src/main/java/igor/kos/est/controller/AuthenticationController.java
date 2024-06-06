package igor.kos.est.controller;

import igor.kos.est.dto.request.LoginUserRequest;
import igor.kos.est.dto.request.RefreshTokenRequest;
import igor.kos.est.dto.request.UserDataRequest;
import igor.kos.est.dto.response.JwtResponse;
import igor.kos.est.dto.response.UserResponse;
import igor.kos.est.entity.RefreshToken;
import igor.kos.est.entity.User;
import igor.kos.est.service.implementation.AuthenticationService;
import igor.kos.est.service.implementation.JwtService;
import igor.kos.est.service.implementation.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static igor.kos.est.util.MapUtil.getUserResponse;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(
            JwtService jwtService,
            AuthenticationService authenticationService,
            RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody UserDataRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(getUserResponse(registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginUserRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(authenticatedUser.getEmail());

        JwtResponse jwtResponse = new JwtResponse(jwtToken, refreshToken.getToken());


        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(request.token());
        String token = jwtService.generateToken(refreshToken.getUser());
        return ResponseEntity.ok(new JwtResponse(token, refreshToken.getToken()));
    }
}
