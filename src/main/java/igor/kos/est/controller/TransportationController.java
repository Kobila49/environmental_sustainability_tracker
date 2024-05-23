package igor.kos.est.controller;

import igor.kos.est.dto.request.TransportationRequest;
import igor.kos.est.dto.response.TransportationResponse;
import igor.kos.est.service.TransportationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transportation")
@RestController
@RequiredArgsConstructor
public class TransportationController {
    private final TransportationService service;


    @GetMapping
    public ResponseEntity<List<TransportationResponse>> getAllTransportations() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportationResponse> getTransportationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TransportationResponse> createTransportation(@RequestBody @Valid TransportationRequest request) {
        return new ResponseEntity<>(service.saveTransportation(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportationResponse> updateTransportation(@RequestBody @Valid TransportationRequest request, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.updateTransportation(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransportation(@PathVariable("id") Long id) {
        service.deleteTransportation(id);
        return ResponseEntity.noContent().build();
    }
}
