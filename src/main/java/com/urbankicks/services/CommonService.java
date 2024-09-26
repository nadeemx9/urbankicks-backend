package com.urbankicks.services;

import com.urbankicks.models.APIResponse;
import com.urbankicks.repositories.BrandRepository;
import com.urbankicks.repositories.CategoryRepository;
import com.urbankicks.repositories.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommonService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final GenderRepository genderRepository;

    public APIResponse getBrands() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(brandRepository.findAllByIsActiveTrue())
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getCategories() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(categoryRepository.findAllByIsActiveTrue())
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getGenders() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(genderRepository.findAll())
                    .build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }
}
