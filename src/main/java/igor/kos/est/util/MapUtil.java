package igor.kos.est.util;

import igor.kos.est.dto.response.UserResponse;
import igor.kos.est.entity.User;

public class MapUtil {

    private MapUtil() {
    }

    public static UserResponse getUserResponse(User registeredUser) {
        return UserResponse.builder()
                .id(registeredUser.getId())
                .firstName(registeredUser.getFirstName())
                .lastName(registeredUser.getLastName())
                .email(registeredUser.getEmail())
                .role(registeredUser.getRole().name())
                .build();
    }
}
