package com.urbankicks.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDropDownResp {
    private Integer brandId;
    private String brandName;
    private List<CollectionDto> collections;
}