package com.urbankicks.config;

import com.urbankicks.entities.Brand;
import com.urbankicks.entities.Category;
import com.urbankicks.entities.Gender;
import com.urbankicks.entities.UserRegister;
import com.urbankicks.repositories.BrandRepository;
import com.urbankicks.repositories.CategoryRepository;
import com.urbankicks.repositories.GenderRepository;
import com.urbankicks.repositories.UserRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataPreInitialization {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final GenderRepository genderRepository;
    private final UserRegisterRepository userRegisterRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    public CommandLineRunner initDatabase() {
        return args -> {
            // Initialize genders
            Gender male = createGenderIfNotExists(Gender.GenderName.MALE);
            Gender female = createGenderIfNotExists(Gender.GenderName.FEMALE);
            Gender unisex = createGenderIfNotExists(Gender.GenderName.UNISEX);

            // Initialize brands
            List<String> brands = Arrays.asList(
                    "Nike", "Adidas", "Puma", "Reebok", "Converse", "Vans",
                    "Under Armour", "New Balance", "Asics", "Skechers"
            );
            for (String brandName : brands) {
                Brand brand = new Brand();
                brand.setBrandName(brandName);
                brand.setCreatedAt(LocalDateTime.now());
                brand.setIsActive(true);
                if (!brandExists(brand)) {
                    brandRepository.save(brand);
                }
            }

            // Initialize Men's Footwear Categories
            createCategoryIfNotExists("Casual Shoes", male);
            createCategoryIfNotExists("Formal Shoes", male);
            createCategoryIfNotExists("Loafers", male);
            createCategoryIfNotExists("Sneakers", male);
            createCategoryIfNotExists("Boots", male);
            createCategoryIfNotExists("Oxfords", male);
            createCategoryIfNotExists("Brogues", male);
            createCategoryIfNotExists("Sandals", male);
            createCategoryIfNotExists("Slippers", male);

            // Initialize Women's Footwear Categories
            createCategoryIfNotExists("Heels", female);
            createCategoryIfNotExists("Flats", female);
            createCategoryIfNotExists("Wedges", female);
            createCategoryIfNotExists("Ballet Flats", female);
            createCategoryIfNotExists("Pumps", female);
            createCategoryIfNotExists("Boots", female);
            createCategoryIfNotExists("Mules", female);
            createCategoryIfNotExists("Sneakers", female);
            createCategoryIfNotExists("Sandals", female);
            createCategoryIfNotExists("Slippers", female);

            // Initialize Kids' Footwear Categories (Unisex)
            createCategoryIfNotExists("Boys' Shoes", unisex);
            createCategoryIfNotExists("Girls' Shoes", unisex);
            createCategoryIfNotExists("School Shoes", unisex);
            createCategoryIfNotExists("Sneakers", unisex);
            createCategoryIfNotExists("Sandals", unisex);
            createCategoryIfNotExists("Boots", unisex);
            createCategoryIfNotExists("Slippers", unisex);

            // Initialize Sports and Outdoor Categories (Unisex)
            createCategoryIfNotExists("Running Shoes", unisex);
            createCategoryIfNotExists("Hiking Boots", unisex);
            createCategoryIfNotExists("Training Shoes", unisex);
            createCategoryIfNotExists("Basketball Shoes", unisex);
            createCategoryIfNotExists("Football Shoes", unisex);
            createCategoryIfNotExists("Tennis Shoes", unisex);
            createCategoryIfNotExists("Cleats", unisex);

            // Initialize Specialty Footwear Categories (Unisex)
            createCategoryIfNotExists("Orthopedic Shoes", unisex);
            createCategoryIfNotExists("Waterproof Shoes", unisex);
            createCategoryIfNotExists("Vegan Footwear", unisex);
            createCategoryIfNotExists("Slip-resistant Shoes", unisex);

            // Initialize Seasonal Footwear Categories (Unisex)
            createCategoryIfNotExists("Flip Flops", unisex);
            createCategoryIfNotExists("Winter Boots", unisex);
            createCategoryIfNotExists("Rain Boots", unisex);

            // Create an Admin User
            if (!userRegisterRepository.existsById(1)) {
                UserRegister adminUser = new UserRegister();
                adminUser.setUsername("nadeem");
                adminUser.setEmail("nadeempalkhiwala@urbankicks.com");
                adminUser.setPassword(passwordEncoder.encode("nadeem")); // Ensure to encode the password in a real application
                adminUser.setFirstname("Nadeem");
                adminUser.setLastname("Palkhiwala");
                adminUser.setRole(UserRegister.Role.ADMIN);
                adminUser.setCreatedAt(LocalDateTime.now());
                adminUser.setIsLoggedOut(false);
                userRegisterRepository.save(adminUser);
            }
        };
    }

    private Gender createGenderIfNotExists(Gender.GenderName genderName) {
        Gender gender = new Gender();
        gender.setGenderName(genderName);
        if (!genderExists(gender)) {
            genderRepository.save(gender);
        }
        // Return the managed instance
        return genderRepository.findByGenderName(genderName);
    }

    private boolean brandExists(Brand brand) {
        return brandRepository.existsByBrandName(brand.getBrandName());
    }

    private boolean genderExists(Gender gender) {
        return genderRepository.existsByGenderName(gender.getGenderName());
    }

    private void createCategoryIfNotExists(String categoryName, Gender gender) {
        if (!categoryExists(categoryName, gender)) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setGender(gender);
            category.setCreatedAt(LocalDateTime.now());
            category.setIsActive(true);
            categoryRepository.save(category);
        }
    }

    private boolean categoryExists(String categoryName, Gender gender) {
        return categoryRepository.existsByCategoryNameAndGender(categoryName, gender);
    }
}
