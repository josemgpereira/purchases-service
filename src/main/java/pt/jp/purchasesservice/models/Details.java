package pt.jp.purchasesservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{description.is.required}")
    private String description;

    @NotNull(message = "{quantity.is.required}")
    Integer quantity;

    @NotNull(message = "{value.is.required}")
    @Column(name = "purchase_value")
    Double value;

    @JsonIgnore
    @OneToOne(mappedBy = "purchaseDetails")
    private Purchase purchase;

    public Details(Long id, String description, Integer quantity, Double value) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.value = value;
    }

    public Details(String description, Integer quantity, Double value) {
        this.description = description;
        this.quantity = quantity;
        this.value = value;
    }
}