package pt.jp.purchasesservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pt.jp.purchasesservice.models.Details;
import pt.jp.purchasesservice.models.Purchase;
import pt.jp.purchasesservice.repositories.PurchaseRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PurchaseRepository purchaseRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        List<Purchase> purchases = new LinkedList<>();
        purchases.add(new Purchase("sb", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details("Super Bock 20 cl", 24, 7.94)));
        purchases.add(new Purchase("sg", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("Sagres 25 cl", 10, 8.59)));
        purchases.add(new Purchase("1", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("1", 10, 8.59)));
        purchases.add(new Purchase("2", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("2", 10, 8.59)));
        purchases.add(new Purchase("3", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("3", 10, 8.59)));
        purchases.add(new Purchase("4", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("4", 10, 8.59)));
        purchases.add(new Purchase("5", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("5", 10, 8.59)));
        purchases.add(new Purchase("6", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("6", 10, 8.59)));
        purchases.add(new Purchase("7", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("7", 10, 8.59)));
        purchases.add(new Purchase("8", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("8", 10, 8.59)));
        purchases.add(new Purchase("9", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("9", 10, 8.59)));
        purchases.add(new Purchase("10", LocalDateTime.parse("10-11-2020 13:00:00", formatter), new Details("10", 10, 8.59)));
        purchaseRepository.saveAll(purchases);
    }
}