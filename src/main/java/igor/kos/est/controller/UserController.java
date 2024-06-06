package igor.kos.est.controller;

import igor.kos.est.dto.response.UserResponse;
import igor.kos.est.entity.User;
import igor.kos.est.service.implementation.UserServiceImpl;
import igor.kos.est.util.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static igor.kos.est.util.MapUtil.getUserResponse;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(getUserResponse(currentUser));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> allUsers() {
        List<UserResponse> users = userService.allUsers().stream().map(MapUtil::getUserResponse).toList();
        return ResponseEntity.ok(users);
    }
}
