package pt.jp.purchasesservice.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pt.jp.purchasesservice.dto.PurchaseDto;
import pt.jp.purchasesservice.models.Details;
import pt.jp.purchasesservice.models.Purchase;
import pt.jp.purchasesservice.repositories.PurchaseRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Test
    public void getPurchaseById() {
        Purchase expectedPurchase = new Purchase(1L, "sb", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details("Super Bock 20 cl", 24, 7.94));
        doReturn(Optional.of(expectedPurchase)).when(purchaseRepository).findById(1L);
        Optional<PurchaseDto> actualPurchase = purchaseService.getPurchaseById(1L);
        assertThat(actualPurchase.get()).isEqualTo(PurchaseDto.create(expectedPurchase));
    }
}