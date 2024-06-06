package igor.kos.est.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(Long id, String firstName, String lastName,  String email, String role, LocalDate dateOfBirth) {
}
