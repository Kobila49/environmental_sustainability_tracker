package igor.kos.est.dto.response;

public record FoodEmissionResponse(String foodName, Double consumption, Double emissionFactor, Double emission) {
}
