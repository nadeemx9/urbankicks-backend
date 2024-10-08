package com.urbankicks.controllers;

import com.urbankicks.models.APIResponse;
import com.urbankicks.services.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("Test Endpoint!");
    }

    @GetMapping("/get-brands-dropdown")
    ResponseEntity<APIResponse> getBrandsDropdown() {
        return new ResponseEntity<>(commonService.getBrandsDropdown(), HttpStatus.OK);
    }

    @GetMapping("/get-categories-dropdown")
    ResponseEntity<APIResponse> getCategoriesDropdown() {
        return new ResponseEntity<>(commonService.getCategoriesDropdown(), HttpStatus.OK);
    }

    @GetMapping("/get-genders")
    ResponseEntity<APIResponse> getGenders() {
        return new ResponseEntity<>(commonService.getGenders(), HttpStatus.OK);
    }

    @GetMapping("/get-categories-section")
    public ResponseEntity<APIResponse> getCategoriesSection() {
        return new ResponseEntity<>(commonService.getCategoriesSection(), HttpStatus.OK);
    }

    @GetMapping("/get-countries")
    public ResponseEntity<APIResponse> getCountries() {
        return new ResponseEntity<>(commonService.getCountries(), HttpStatus.OK);
    }

    @GetMapping("/get-states")
    public ResponseEntity<APIResponse> getStates() {
        return new ResponseEntity<>(commonService.getStates(), HttpStatus.OK);
    }

    @GetMapping("/get-districts/{stateId}")
    public ResponseEntity<APIResponse> getDistricts(@PathVariable int stateId) {
        return new ResponseEntity<>(commonService.getDistricts(stateId), HttpStatus.OK);
    }

    @GetMapping("/get-categories-by-gender/{genderId}")
    public ResponseEntity<APIResponse> getCategoriesByGender(@PathVariable int genderId) {
        return new ResponseEntity<>(commonService.getCategoriesByGender(genderId), HttpStatus.OK);
    }

    @GetMapping("/get-collections-by-brand/{brandId}")
    public ResponseEntity<APIResponse> getCollectionsByBrand(@PathVariable int brandId) {
        return new ResponseEntity<>(commonService.getCollectionsByBrand(brandId), HttpStatus.OK);
    }
}
