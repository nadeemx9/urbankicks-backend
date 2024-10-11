package com.urbankicks.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
public class AddProductPayload {
    private String productName;
    private String title;
    private String description;
    private Long brandId;
    private Long collectionId;
    private Long categoryId;
    private Long genderId;
    private Long sizeId;
    private Long colorId;
    private BigDecimal basePrice;
    private Integer quantity;
}
