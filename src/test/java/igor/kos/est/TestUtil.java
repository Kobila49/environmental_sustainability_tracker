package igor.kos.est;

import igor.kos.est.dto.request.*;
import igor.kos.est.entity.*;
import igor.kos.est.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    private TestUtil() {
    }

    public static FoodEmissionRequest getMockFoodEmissionRequest() {
        return new FoodEmissionRequest(1L, new BigDecimal("1.5"));
    }

    public static DailyEmissionRequest getMockDailyEmissionRequest() {
        return new DailyEmissionRequest(LocalDate.now(),
                List.of(new TransportationEmissionRequest(1L, new BigDecimal("22"))),
                List.of(new FoodEmissionRequest(1L, new BigDecimal("1.5"))),
                List.of(new UtilityEmissionRequest(1L, new BigDecimal("33"))));
    }

    public static UserDataRequest getMockUserDataRequest() {
        return new UserDataRequest("test@email.com", "password", "John", "Doe", LocalDate.now().minusYears(30));
    }

    public static User getMockUser() {
        var user = User.builder()
                .email("test@email.com")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now().minusYears(30))
                .role(Role.USER)
                .build();
        user.setId(1L);
        return user;
    }

    public static DailyEmission getMockDailyEmission() {
        var dailyEmission = DailyEmission.builder()
                .emissionDate(LocalDate.now())
                .user(getMockUser())
                .totalEmission(BigDecimal.ZERO)
                .utilityEmissions(new ArrayList<>())
                .foodEmissions(new ArrayList<>())
                .transportationEmissions(new ArrayList<>())
                .build();
        dailyEmission.setId(1L);
        return dailyEmission;
    }

    public static DailyEmission getMockDailyEmissionFull() {
        var dailyEmission = DailyEmission.builder()
                .emissionDate(LocalDate.now())
                .user(getMockUser())
                .totalEmission(new BigDecimal("69.00"))
                .utilityEmissions(getModifiableUtilityList())
                .foodEmissions(getModifiableFoodList())
                .transportationEmissions(getModifiableTransportationList())
                .build();
        dailyEmission.setId(1L);
        return dailyEmission;
    }

    public static FoodEmission getMockFoodEmission() {
        var foodEmission = FoodEmission.builder()
                .dailyEmission(getMockDailyEmission())
                .foodId(1L)
                .consumptionInKg(new BigDecimal("1.5"))
                .emissionFactor(new BigDecimal("2.0"))
                .emission(new BigDecimal("3.00"))
                .build();
        foodEmission.setId(1L);
        return foodEmission;
    }

    public static TransportationEmission getMockTransportationEmission() {
        var transportationEmission = TransportationEmission.builder()
                .dailyEmission(getMockDailyEmission())
                .distanceTravelled(new BigDecimal("22"))
                .emissionFactor(new BigDecimal("2.0"))
                .emission(new BigDecimal("44.00"))
                .transportationId(1L)
                .build();
        transportationEmission.setId(1L);
        return transportationEmission;
    }

    public static UtilityEmission getMockUtilityEmission() {
        var utilityEmission = UtilityEmission.builder()
                .dailyEmission(getMockDailyEmission())
                .emissionFactor(new BigDecimal("2.0"))
                .usage(new BigDecimal("11"))
                .emission(new BigDecimal("22.00"))
                .utilityId(1L)
                .build();
        utilityEmission.setId(1L);
        return utilityEmission;
    }

    public static List<TransportationEmission> getModifiableTransportationList() {
        final var list = new ArrayList<TransportationEmission>();
        list.add(getMockTransportationEmission());
        return list;
    }

    public static List<FoodEmission> getModifiableFoodList() {
        final var list = new ArrayList<FoodEmission>();
        list.add(getMockFoodEmission());
        return list;
    }

    public static List<UtilityEmission> getModifiableUtilityList() {
        final var list = new ArrayList<UtilityEmission>();
        list.add(getMockUtilityEmission());
        return list;
    }

    public static Food getMockFood() {
        final var food = Food.builder()
                .name("Apple")
                .category("Fruit")
                .emissionFactor(new BigDecimal("2.0"))
                .build();
        food.setId(1L);
        return food;
    }
}
