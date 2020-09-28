package pt.jp.purchasesservice.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pt.jp.purchasesservice.dto.PurchaseDto;
import pt.jp.purchasesservice.models.Details;
import pt.jp.purchasesservice.models.Purchase;
import pt.jp.purchasesservice.services.PurchaseService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PurchaseService purchaseService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Before
    public void init() {
        Purchase purchase = new Purchase(1L, "sb", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details(1L, "Super Bock 20 cl", 24, 7.94));
        when(purchaseService.getPurchaseById(1L)).thenReturn(Optional.of(PurchaseDto.create(purchase)));
    }

    @Test
    public void getPurchases() throws Exception {

        List<Purchase> purchases = Arrays.asList(
                new Purchase(1L, "sb", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details(1L, "Super Bock 20 cl", 24, 7.94)),
                new Purchase(2L, "sg", LocalDateTime.parse("10-04-2020 13:00:00", formatter), new Details(2L, "Sagres 25 cl", 10, 8.59)));

        List<PurchaseDto> purchasesDto = new ArrayList<>();
        for (Purchase purchase : purchases) {
            purchasesDto.add(PurchaseDto.create(purchase));
        }

        when(purchaseService.getPurchases()).thenReturn(purchasesDto);

        mockMvc.perform(get("/api/v1/purchases"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productType", is("sb")))
                .andExpect(jsonPath("$[0].expires", is("10-02-2020 12:30:00")))
                .andExpect(jsonPath("$[0].purchaseDetails.id", is(1)))
                .andExpect(jsonPath("$[0].purchaseDetails.description", is("Super Bock 20 cl")))
                .andExpect(jsonPath("$[0].purchaseDetails.quantity", is(24)))
                .andExpect(jsonPath("$[0].purchaseDetails.value", is(7.94)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].productType", is("sg")))
                .andExpect(jsonPath("$[1].expires", is("10-04-2020 13:00:00")))
                .andExpect(jsonPath("$[1].purchaseDetails.id", is(2)))
                .andExpect(jsonPath("$[1].purchaseDetails.description", is("Sagres 25 cl")))
                .andExpect(jsonPath("$[1].purchaseDetails.quantity", is(10)))
                .andExpect(jsonPath("$[1].purchaseDetails.value", is(8.59)));
    }

    @Test
    public void getPurchaseById() throws Exception {

        mockMvc.perform(get("/api/v1/purchases/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productType", is("sb")))
                .andExpect(jsonPath("$.expires", is("10-02-2020 12:30:00")))
                .andExpect(jsonPath("$.purchaseDetails.id", is(1)))
                .andExpect(jsonPath("$.purchaseDetails.description", is("Super Bock 20 cl")))
                .andExpect(jsonPath("$.purchaseDetails.quantity", is(24)))
                .andExpect(jsonPath("$.purchaseDetails.value", is(7.94)));
    }

    @Test
    public void getPurchaseByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/purchases/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createPurchase() throws Exception {

        Purchase purchase = new Purchase(1L, "sb", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details(1L, "Super Bock 20 cl", 24, 7.94));
        PurchaseDto purchaseDto = PurchaseDto.create(purchase);

        when(purchaseService.savePurchase(any(Purchase.class))).thenReturn(purchaseDto);

        mockMvc.perform(post("/api/v1/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productType\":\"sb\",\"expires\":\"10-02-2020 12:30:00\",\"purchaseDetails\":{\"description\":\"Super Bock 20 cl\",\"quantity\":24,\"value\":7.94}}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrlPattern("http://*/api/v1/purchases/1"))
                //.andExpect(header().string("Location", "/api/v1/purchases/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productType").value("sb"))
                .andExpect(jsonPath("$.expires").value("10-02-2020 12:30:00"))
                .andExpect(jsonPath("$.purchaseDetails.id").value(1))
                .andExpect(jsonPath("$.purchaseDetails.description").value("Super Bock 20 cl"))
                .andExpect(jsonPath("$.purchaseDetails.quantity").value(24))
                .andExpect(jsonPath("$.purchaseDetails.value").value(7.94));
    }

    @Test
    public void updatePurchase() throws Exception {

        Purchase purchase = new Purchase(1L, "sb update", LocalDateTime.parse("10-02-2020 12:30:00", formatter), new Details(1L, "Super Bock 20 cl update", 24, 7.94));
        PurchaseDto purchaseDto = PurchaseDto.create(purchase);

        when(purchaseService.savePurchase(any(Purchase.class))).thenReturn(purchaseDto);

        mockMvc.perform(put("/api/v1/purchases/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productType\":\"sb update\",\"expires\":\"10-02-2020 12:30:00\",\"purchaseDetails\":{\"description\":\"Super Bock 20 cl update\",\"quantity\":24,\"value\":7.94}}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productType").value("sb update"))
                .andExpect(jsonPath("$.expires").value("10-02-2020 12:30:00"))
                .andExpect(jsonPath("$.purchaseDetails.id").value(1))
                .andExpect(jsonPath("$.purchaseDetails.description").value("Super Bock 20 cl update"))
                .andExpect(jsonPath("$.purchaseDetails.quantity").value(24))
                .andExpect(jsonPath("$.purchaseDetails.value").value(7.94));
    }

    @Test
    public void deletePurchase() throws Exception {
        doNothing().when(purchaseService).deletePurchase(1L);
        mockMvc.perform(delete("/api/v1/purchases/1"))
                .andExpect(status().isOk());
    }
}