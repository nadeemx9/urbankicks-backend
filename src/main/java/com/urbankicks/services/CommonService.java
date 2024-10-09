package com.urbankicks.services;

import com.urbankicks.entities.Brand;
import com.urbankicks.entities.Collection;
import com.urbankicks.models.*;
import com.urbankicks.repositories.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CommonService {
    private static final Logger log = LoggerFactory.getLogger(CommonService.class);
    private final BrandRepository brandRepository;
    private final CollectionRepository collectionRepository;
    private final CategoryRepository categoryRepository;
    private final GenderRepository genderRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    public APIResponse getBrandsDropdown() {
        try {

            List<Brand> brands = brandRepository.getBrandsDropdown();
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
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getCategoriesDropdown() {
        try {
            // Fetch the list of categories with their gender data
            List<Map<String, Object>> categories = categoryRepository.getCategoriesDropdown();
            List<CategoryDropdownResp> resp = new ArrayList<>();

            // A map to track gender and associated categories
            Map<Integer, CategoryDropdownResp> genderMap = new HashMap<>();

            // Iterate over the fetched categories
            categories.forEach(c -> {
                Integer genderId = (Integer) c.get("genderId");
                String genderName = (String) c.get("genderName");
                Integer categoryId = (Integer) c.get("categoryId");
                String categoryName = (String) c.get("categoryName");

                // If this gender is not yet added, create a new CategoryDropdownResp
                CategoryDropdownResp categoryDropdownResp = genderMap.get(genderId);
                if (categoryDropdownResp == null) {
                    categoryDropdownResp = new CategoryDropdownResp(genderId, genderName, new ArrayList<>());
                    genderMap.put(genderId, categoryDropdownResp);
                    resp.add(categoryDropdownResp);
                }

                // If the category exists (i.e., not null), add it to the categories list
                if (categoryId != null && categoryName != null) {
                    categoryDropdownResp.getCategories().add(new CategoryDto(categoryId, categoryName));
                }
            });
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(resp)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getCategoriesByGender(int genderId) {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(categoryRepository.getCategoriesByGender(genderId))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getCollectionsByBrand(int brandId) {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(collectionRepository.getCollectionsByBrand(brandId))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getCategoriesSection() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(categoryRepository.getCategoriesSection())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getCountries() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(countryRepository.findAll())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getStates() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(stateRepository.findAll())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getDistricts(int stateId) {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(districtRepository.findByState_StateId(stateId))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }
    public APIResponse getColors() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(colorRepository.findAll())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }

    public APIResponse getSizes() {
        try {
            return APIResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(sizeRepository.findAll())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return APIResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .respMsg(e.getMessage())
                    .build();
        }
    }
}
