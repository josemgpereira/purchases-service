package pt.jp.purchasesservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pt.jp.purchasesservice.dto.PurchaseDto;
import pt.jp.purchasesservice.models.Purchase;
import pt.jp.purchasesservice.repositories.PurchaseRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements  PurchaseService{

    private final PurchaseRepository purchaseRepository;

    @Override
    public List<PurchaseDto> getPurchases() {
        List<Purchase> purchases = purchaseRepository.findByExpiresAfter(LocalDateTime.now());
        List<PurchaseDto> purchasesDto = new ArrayList<>();
        for (Purchase employee : purchases) {
            purchasesDto.add(PurchaseDto.create(employee));
        }
        return purchasesDto;
    }

    @Override
    public List<PurchaseDto> getAllPurchases(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Purchase> pagedResult = purchaseRepository.findAll(paging);
        List<PurchaseDto> purchasesDto = new ArrayList<>();
        for (Purchase employee : pagedResult.getContent()) {
            purchasesDto.add(PurchaseDto.create(employee));
        }
        return purchasesDto;
    }

    @Override
    public Optional<PurchaseDto> getPurchaseById(Long id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (!purchase.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(PurchaseDto.create(purchase.get()));
    }

    @Override
    public PurchaseDto savePurchase(Purchase purchase) {
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return PurchaseDto.create(savedPurchase);
    }

    @Override
    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}