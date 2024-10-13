package com.urbankicks.services;

import com.cloudinary.Cloudinary;
import com.urbankicks.models.APIResponse;
import com.urbankicks.models.AddProductPayload;
import com.urbankicks.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final MessageSource messageSource;
    private final Cloudinary cloudinary;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};

    /**
     * Adds a product with file validation for the required primary image
     *
     * @param payload      the product payload
     * @param primaryImage the required primary image
     * @param img2         optional image 2
     * @param img3         optional image 3
     * @param img4         optional image 4
     * @param img5         optional image 5
     * @return APIResponse
     */
    public APIResponse addProduct(
            AddProductPayload payload,
            MultipartFile primaryImage,
            MultipartFile img2,
            MultipartFile img3,
            MultipartFile img4,
            MultipartFile img5) {

        // Validate primary image (required)
        APIResponse primaryImageValidationResponse = validateFile(primaryImage, "primaryImg");
        if (primaryImageValidationResponse != null) {
            return primaryImageValidationResponse; // Return early if validation fails
        }

        // Validate other optional images (img2, img3, img4, img5)
        Map<String, APIResponse> otherImageValidations = new HashMap<>();
        otherImageValidations.put("img2", validateFile(img2, "img2"));
        otherImageValidations.put("img3", validateFile(img3, "img3"));
        otherImageValidations.put("img4", validateFile(img4, "img4"));
        otherImageValidations.put("img5", validateFile(img5, "img5"));

        // Check for any validation errors in the optional images
        for (Map.Entry<String, APIResponse> entry : otherImageValidations.entrySet()) {
            if (entry.getValue() != null) {
                return entry.getValue(); // Return the first validation error found
            }
        }

        // Proceed with the actual logic to save the product and its images
        log.info("Product and images validated successfully");
        return APIResponse.builder()
                .status(200)
                .respCode("SUCCESS")
                .respMsg("Product added successfully")
//                .data(uploadImageToCloudinary(primaryImage, payload.getProductName() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_hh:mm:ss"))))
                .build();
    }

    /**
     * Validates the provided file (check size, type, and extensions)
     *
     * @param file          the MultipartFile to validate
     * @param fileFieldName the field name of the file
     * @return APIResponse if there is a validation error, null if valid
     */
    public APIResponse validateFile(MultipartFile file, String fileFieldName) {
        // If the file is null or empty and is required, return an error
        if (file == null || file.isEmpty()) {
            if ("primaryImg".equals(fileFieldName)) { // Only return error for required primary image
                String errorMsg = messageSource.getMessage("msg.blank.file", null, Locale.getDefault());
                return buildValidationErrorResponse(fileFieldName, errorMsg);
            }
            return null; // For optional files, skip validation if not provided
        }

        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            String errorMsg = messageSource.getMessage("msg.max.file.size.exceeds", null, Locale.getDefault());
            return buildValidationErrorResponse(fileFieldName, errorMsg);
        }

        // Validate file extension
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(fileExtension.toLowerCase())) {
            String errorMsg = messageSource.getMessage("msg.invalid.file.type", null, Locale.getDefault());
            return buildValidationErrorResponse(fileFieldName, errorMsg);
        }

        // Validate file name for multiple extensions
        if (file.getOriginalFilename().chars().filter(ch -> ch == '.').count() > 1) {
            String errorMsg = messageSource.getMessage("msg.file.multiple.extensions", null, Locale.getDefault());
            return buildValidationErrorResponse(fileFieldName, errorMsg);
        }

        return null; // File is valid
    }

    /**
     * Retrieves the file extension from the filename
     *
     * @param filename the filename string
     * @return file extension
     */
    private static String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * Builds the validation error response
     *
     * @param fileFieldName the file field name (e.g., "primaryImg")
     * @param errorMsg      the error message to return
     * @return APIResponse containing the error details
     */
    private APIResponse buildValidationErrorResponse(String fileFieldName, String errorMsg) {
        Map<String, String> errors = new HashMap<>();
        errors.put(fileFieldName, errorMsg);
        return APIResponse.builder()
                .status(400)
                .respCode("VALIDATION_ERROR")
                .errors(errors)
                .build();
    }

    public String uploadImageToCloudinary(MultipartFile file, String productName) {
        try {
            String folderName = "urbankicks/products/" + productName + LocalDateTime.now();
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            options.put("use_filename", true);
            options.put("unique_filename", true);
            options.put("resource_type", "image");

            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
