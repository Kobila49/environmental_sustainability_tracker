package igor.kos.est.dto.response;

public record ApiResponse<T>(boolean success, T body) {}
