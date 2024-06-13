package igor.kos.est.controller;

import igor.kos.est.dto.request.DailyEmissionRequest;
import igor.kos.est.dto.response.DailyEmissionResponse;
import igor.kos.est.service.EmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/emission")
@RestController
@RequiredArgsConstructor
public class EmissionController {

    private final EmissionService service;

    @PostMapping
    public ResponseEntity<DailyEmissionResponse> createOrUpdateDailyEmission(@RequestBody @Valid DailyEmissionRequest request) {
        return new ResponseEntity<>(service.createOrUpdateDailyEmission(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DailyEmissionResponse> getDailyEmission(@RequestParam(name = "date") LocalDate date) {
        return new ResponseEntity<>(service.getDailyEmissionForDate(date), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DailyEmissionResponse>> getDailyEmissions() {
        return new ResponseEntity<>(service.getDailyEmissions(), HttpStatus.OK);
    }
}
