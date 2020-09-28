package pt.jp.purchasesservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product type is required.")
    private String productType;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @NotNull(message = "Expiration date is required.")
    private LocalDateTime expires;

    @NotNull(message = "Details are required.")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private Details purchaseDetails;

    public Purchase(String productType, LocalDateTime expires, Details purchaseDetails) {
        this.productType = productType;
        this.expires = expires;
        this.purchaseDetails = purchaseDetails;
    }
}