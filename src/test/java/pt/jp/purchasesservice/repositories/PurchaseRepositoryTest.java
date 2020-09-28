package pt.jp.purchasesservice.repositories;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pt.jp.purchasesservice.configuration.RepositoryConfiguration;
import pt.jp.purchasesservice.models.Details;
import pt.jp.purchasesservice.models.Purchase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void findAll() {
        //List<Purchase> purchases = purchaseRepository.findAll();
        //assertThat(purchases).hasSize(1);
        Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
        Page<Purchase> pagedResult = purchaseRepository.findAll(paging);
        assertThat(pagedResult).hasSize(1);

    }

    @Test
    public void findById() {
        Purchase purchase = purchaseRepository.findById(1L).get();
        assertThat(purchase.getProductType()).isEqualTo("cb");
    }

    @Test
    public void save() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Purchase purchase = new Purchase("sb", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details("Super Bock 20 cl", 24, 7.94));
        assertNull(purchase.getId());
        purchaseRepository.save(purchase);
        assertNotNull(purchase.getId());
    }
}