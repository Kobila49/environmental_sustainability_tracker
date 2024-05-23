package igor.kos.est.controller;

import igor.kos.est.dto.request.FoodRequest;
import igor.kos.est.dto.response.FoodResponse;
import igor.kos.est.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/food")
@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;


    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAllFood() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FoodResponse> createTransportation(@RequestBody @Valid FoodRequest request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodResponse> updateTransportation(@RequestBody @Valid FoodRequest request, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransportation(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
