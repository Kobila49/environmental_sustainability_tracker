package igor.kos.est.controller;

import igor.kos.est.dto.request.UtilityRequest;
import igor.kos.est.dto.response.UtilityResponse;
import igor.kos.est.service.UtilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/utility")
@RestController
@RequiredArgsConstructor
public class UtilityController {

    private final UtilityService service;

    @GetMapping
    public ResponseEntity<List<UtilityResponse>> getAllUtilities() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilityResponse> getUtilityById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UtilityResponse> createUtility(@RequestBody @Valid UtilityRequest request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilityResponse> updateUtility(@RequestBody @Valid UtilityRequest request, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtility(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
