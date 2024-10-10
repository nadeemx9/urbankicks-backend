package com.urbankicks.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbankicks.models.APIResponse;
import com.urbankicks.models.AddProductPayload;
import com.urbankicks.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("product")

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @PostMapping("/add-product")
    public ResponseEntity<APIResponse> addProduct(
            @RequestParam("productPayload") String productPayload,
            @RequestParam(value = "primaryImg", required = true) MultipartFile primaryImage,
            @RequestParam(value = "img2", required = false) MultipartFile img2,
            @RequestParam(value = "img3", required = false) MultipartFile img3,
            @RequestParam(value = "img4", required = false) MultipartFile img4,
            @RequestParam(value = "img5", required = false) MultipartFile img5) throws JsonProcessingException {

        APIResponse validationResponse = validateFile(primaryImage, "primaryImg");
        if (validationResponse != null) {
            return ResponseEntity.badRequest().body(validationResponse);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        AddProductPayload product = objectMapper.readValue(productPayload, AddProductPayload.class);
        System.out.println(product.toString());
        return ResponseEntity.ok().body(
                APIResponse.builder()
                        .status(1)
                        .build());
    }

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};

    public APIResponse validateFile(MultipartFile file, String fileFieldName) {
        Map<String, String> errors = new HashMap<>();

        if (file.isEmpty()) {
            String errorMsg = messageSource.getMessage("msg.blank.file", null, Locale.getDefault());
            errors.put(fileFieldName, errorMsg);
            return buildErrorResponse(errors);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            String errorMsg = messageSource.getMessage("msg.max.file.size.exceeds", null, Locale.getDefault());
            errors.put(fileFieldName, errorMsg);
            return buildErrorResponse(errors);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(fileExtension.toLowerCase())) {
            String errorMsg = messageSource.getMessage("msg.invalid.file.type", null, Locale.getDefault());
            errors.put(fileFieldName, errorMsg);
            return buildErrorResponse(errors);
        }

        // Check if the file name has multiple periods indicating multiple extensions
        if (file.getOriginalFilename().chars().filter(ch -> ch == '.').count() > 1) {
            String errorMsg = messageSource.getMessage("msg.file.multiple.extensions", null, Locale.getDefault());
            errors.put(fileFieldName, errorMsg);
            return buildErrorResponse(errors);
        }

        return null; // No errors, return null
    }

    private static String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }

    private APIResponse buildErrorResponse(Map<String, String> errors) {
        return APIResponse.builder()
                .status(400)
                .respCode("VALIDATION_ERROR")
                .errors(errors)
                .build();
    }
}