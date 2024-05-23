package igor.kos.est.dto.response;

public record FoodResponse(
        Long id,
        String name,
        Double emissionFactor,
        String category
) {
}
