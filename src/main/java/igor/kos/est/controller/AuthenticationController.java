package igor.kos.est.controller;

import igor.kos.est.dto.request.LoginUserRequest;
import igor.kos.est.dto.request.RefreshTokenRequest;
import igor.kos.est.dto.request.UserDataRequest;
import igor.kos.est.dto.response.JwtResponse;
import igor.kos.est.dto.response.UserResponse;
import igor.kos.est.entity.User;
import igor.kos.est.service.implementation.AuthenticationService;
import igor.kos.est.service.implementation.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static igor.kos.est.util.MapUtil.getUserResponse;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(@RequestBody UserDataRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(getUserResponse(registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginUserRequest loginUserDto) {
        final var jwtResponse = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        final var jwtResponse = refreshTokenService.getNewTokenViaRefreshToken(request.token());
        return ResponseEntity.ok(jwtResponse);
    }
}
