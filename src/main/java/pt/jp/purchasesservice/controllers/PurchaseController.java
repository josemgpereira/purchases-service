package pt.jp.purchasesservice.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.jp.purchasesservice.dto.PurchaseDto;
import pt.jp.purchasesservice.exceptions.ResourceNotFoundException;
import pt.jp.purchasesservice.models.Purchase;
import pt.jp.purchasesservice.services.PurchaseService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/v1/purchases"})
public class PurchaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<PurchaseDto>> getPurchases() {
        List<PurchaseDto> purchases = purchaseService.getPurchases();
        if(purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseDto>> getAllPurchases(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<PurchaseDto> purchases = purchaseService.getAllPurchases(pageNo, pageSize, sortBy);
        if(purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("GET /api/v1/purchases/all");
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getPurchaseById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<PurchaseDto> purchase = purchaseService.getPurchaseById(id);
        if (!purchase.isPresent()) {
            throw new ResourceNotFoundException("Purchase with id " + id + " not found.");
        }
        return ResponseEntity.ok(purchase.get());
    }

    @PostMapping
    public ResponseEntity<Object> createPurchase(@Valid @RequestBody Purchase purchase) {
        PurchaseDto createdPurchase = purchaseService.savePurchase(purchase);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPurchase.getId()).toUri();
        return ResponseEntity.created(location).body(createdPurchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseDto> updatePurchase(@PathVariable Long id, @Valid @RequestBody Purchase purchase) throws ResourceNotFoundException {
        if (!purchaseService.getPurchaseById(id).isPresent()) {
            throw new ResourceNotFoundException("Purchase with id " + id + " not found.");
        }
        Long purchaseDetailsId = purchaseService.getPurchaseById(id).get().getPurchaseDetails().getId();
        purchase.setId(id);
        purchase.getPurchaseDetails().setId(purchaseDetailsId);
        PurchaseDto updatedPurchase = purchaseService.savePurchase(purchase);
        return ResponseEntity.ok(updatedPurchase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePurchase(@PathVariable Long id) throws ResourceNotFoundException {
        if (!purchaseService.getPurchaseById(id).isPresent()) {
            throw new ResourceNotFoundException("Purchase with id " + id + " not found.");
        }
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}