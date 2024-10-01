package com.urbankicks.controllers;

import com.urbankicks.models.APIResponse;
import com.urbankicks.services.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
