package pt.jp.purchasesservice.repositories;

        import org.springframework.data.repository.PagingAndSortingRepository;
        import org.springframework.stereotype.Repository;
        import pt.jp.purchasesservice.models.Purchase;

        import java.time.LocalDateTime;
        import java.util.List;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {
    List<Purchase> findByExpiresAfter(LocalDateTime currentDateTime);
}