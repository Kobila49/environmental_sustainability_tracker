package igor.kos.est.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String firstName, String lastName,  String email) {
}
