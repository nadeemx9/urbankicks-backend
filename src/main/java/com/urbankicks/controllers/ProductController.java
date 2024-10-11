package com.urbankicks.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbankicks.models.APIResponse;
import com.urbankicks.models.AddProductPayload;
import com.urbankicks.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("product")

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<APIResponse> addProduct(
            @RequestParam("productPayload") String productPayload,
            @RequestParam(value = "primaryImg") MultipartFile primaryImage,
            @RequestParam(value = "img2", required = false) MultipartFile img2,
            @RequestParam(value = "img3", required = false) MultipartFile img3,
            @RequestParam(value = "img4", required = false) MultipartFile img4,
            @RequestParam(value = "img5", required = false) MultipartFile img5) throws JsonProcessingException {

        System.out.println(primaryImage.getOriginalFilename());

        ObjectMapper objectMapper = new ObjectMapper();
        AddProductPayload product = objectMapper.readValue(productPayload, AddProductPayload.class);

        return ResponseEntity.ok().body(productService.addProduct(product, primaryImage, img2, img3, img4, img5));
    }
}