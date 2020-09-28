package pt.jp.purchasesservice.services;

import pt.jp.purchasesservice.dto.PurchaseDto;
import pt.jp.purchasesservice.models.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    List<PurchaseDto> getPurchases();
    List<PurchaseDto> getAllPurchases(Integer pageNo, Integer pageSize, String sortBy);
    Optional<PurchaseDto> getPurchaseById(Long id);
    PurchaseDto savePurchase(Purchase purchase);
    void deletePurchase(Long id);
}