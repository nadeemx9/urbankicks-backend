package com.urbankicks.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDropdownResp {
    private Integer genderId;
    private String genderName;
    private List<CategoryDto> categories = new ArrayList<>();
}