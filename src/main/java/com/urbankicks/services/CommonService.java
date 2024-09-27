package com.urbankicks.services;

import com.urbankicks.entities.Brand;
import com.urbankicks.entities.Collection;
import com.urbankicks.models.APIResponse;
import com.urbankicks.models.BrandDropDownResp;
import com.urbankicks.models.CollectionDto;
import com.urbankicks.repositories.BrandRepository;
import com.urbankicks.repositories.CategoryRepository;
import com.urbankicks.repositories.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommonService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final GenderRepository genderRepository;

    public APIResponse getBrands() {
        try {

            List<Brand> brands = brandRepository.findAllBrands();
            List<BrandDropDownResp> reponse = new ArrayList<>();

            for (Brand brand : brands) {
                List<CollectionDto> collectionDTOs = new ArrayList<>();
                for (Collection collection : brand.getCollections()) {
                    collectionDTOs.add(new CollectionDto(collection.getCollectionId(), collection.getCollectionName()));
                }
                reponse.add(new BrandDropDownResp(brand.getBrandId(), brand.getBrandName(), collectionDTOs));
            }

            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(reponse)
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
                    .data(categoryRepository.findAllCategories())
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
