package pt.jp.purchasesservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import pt.jp.purchasesservice.models.Details;
import pt.jp.purchasesservice.models.Purchase;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseDto {
    private Long id;

    private String productType;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime expires;

    private Details purchaseDetails;

    public static PurchaseDto create(Purchase purchase) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(purchase, PurchaseDto.class);
    }
}