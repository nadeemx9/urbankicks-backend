package com.urbankicks.config;

import com.urbankicks.entities.*;
import com.urbankicks.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DataPreInitialization {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CollectionRepository collectionRepository;
    private final GenderRepository genderRepository;
    private final UserRegisterRepository userRegisterRepository;
    private final PasswordEncoder passwordEncoder;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Bean
    @Transactional
    public CommandLineRunner initDatabase() {
        return args -> {
            initializeGenders();
            initializeBrandsAndCollections();
            initializeCategories();
            initializeColors();
            initializeSizes();
            createAdminUser();
        };
    }

    private void initializeGenders() {
        List<Gender.GenderName> genders = Arrays.asList(Gender.GenderName.MALE, Gender.GenderName.FEMALE, Gender.GenderName.UNISEX);
        genders.forEach(this::createGenderIfNotExists);
    }

    private void initializeBrandsAndCollections() {
        List<String> brands = Arrays.asList("Nike", "Adidas", "Puma", "Reebok", "Converse", "Vans", "Under Armour", "New Balance", "Asics", "Skechers");
        brands.forEach(this::createBrandIfNotExists);

        Brand nike = brandRepository.findByBrandName("Nike");
        if (nike != null) {
            createCollectionsIfNotExists(nike, Arrays.asList("Jordan", "Dunk", "Air Max", "Air Force 1"));
        }

        Brand converse = brandRepository.findByBrandName("Converse");
        if (converse != null) {
            createCollectionsIfNotExists(converse, Arrays.asList("Chuck", "One Star"));
        }
    }

    private void initializeCategories() {
        Map<Gender.GenderName, List<String>> categoriesByGender = Map.of(
                Gender.GenderName.MALE, Arrays.asList("Casual Shoes", "Formal Shoes", "Loafers", "Sneakers", "Boots", "Sandals", "Slippers", "Boys' Shoes"),
                Gender.GenderName.FEMALE, Arrays.asList("Heels", "Flats", "Wedges", "Ballet Flats", "Pumps", "Boots", "Mules", "Sneakers", "Sandals", "Slippers", "Girls' Shoes"),
                Gender.GenderName.UNISEX, Arrays.asList("School Shoes", "Sneakers", "Sandals", "Boots", "Slippers",
                        "Running Shoes", "Hiking Boots", "Training Shoes", "Basketball Shoes", "Football Shoes", "Cleats", "Flip Flops")
        );

        categoriesByGender.forEach((genderName, categories) -> {
            Gender gender = genderRepository.findByGenderName(genderName);
            categories.forEach(category -> createCategoryIfNotExists(category, gender));
        });
    }

    private void initializeColors() {
        List<Color> colors = Arrays.asList(
                new Color(null, "Red", "#FF0000"),
                new Color(null, "Green", "#008000"),
                new Color(null, "Blue", "#0000FF"),
                new Color(null, "White", "#FFFFFF"),
                new Color(null, "Black", "#000000"),
                new Color(null, "Purple", "#800080"),
                new Color(null, "Pink", "#FFC0CB")
        );

        // Save colors if not exists, using the `getColorName` method as the key
        saveEntitiesIfNotExists(colors, Color::getColorName, colorRepository::existsByColorName, colorRepository::saveAll);
    }

    private void initializeSizes() {
        List<String> ukSizes = Arrays.asList(
                "UK 5", "UK 5.5", "UK 6", "UK 6.5", "UK 7", "UK 7.5", "UK 8", "UK 8.5", "UK 9",
                "UK 9.5", "UK 10", "UK 10.5", "UK 11", "UK 11.5", "UK 12", "UK 12.5", "UK 13",
                "UK 13.5", "UK 14", "UK 14.5", "UK 15", "UK 15.5", "UK 16"
        );
        ukSizes.forEach(this::createSizeIfNotExists);
    }

    private void createAdminUser() {
        if (!userRegisterRepository.existsById(1)) {
            UserRegister adminUser = new UserRegister();
            adminUser.setUsername("nadeem");
            adminUser.setEmail("nadeempalkhiwala@urbankicks.com");
            adminUser.setPassword(passwordEncoder.encode("nadeem"));
            adminUser.setFirstname("Nadeem");
            adminUser.setLastname("Palkhiwala");
            adminUser.setRole(UserRegister.Role.ADMIN);
            adminUser.setCreatedAt(LocalDateTime.now());
            adminUser.setIsLoggedOut(false);
            userRegisterRepository.save(adminUser);
        }
    }

    private void createGenderIfNotExists(Gender.GenderName genderName) {
        if (!genderRepository.existsByGenderName(genderName)) {
            Gender gender = new Gender();
            gender.setGenderName(genderName);
            genderRepository.save(gender);
        }
    }

    private void createBrandIfNotExists(String brandName) {
        if (!brandRepository.existsByBrandName(brandName)) {
            Brand brand = new Brand();
            brand.setBrandName(brandName);
            brand.setCreatedAt(LocalDateTime.now());
            brand.setIsActive(true);
            brandRepository.save(brand);
        }
    }

    private void createCategoryIfNotExists(String categoryName, Gender gender) {
        if (!categoryRepository.existsByCategoryNameAndGender(categoryName, gender)) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setGender(gender);
            category.setCreatedAt(LocalDateTime.now());
            category.setIsActive(true);
            categoryRepository.save(category);
        }
    }

    private void createCollectionsIfNotExists(Brand brand, List<String> collectionNames) {
        collectionNames.forEach(collectionName -> {
            if (!collectionRepository.existsByCollectionNameAndBrand(collectionName, brand)) {
                Collection collection = new Collection();
                collection.setCollectionName(collectionName);
                collection.setBrand(brand);
                collection.setCreatedAt(LocalDateTime.now());
                collection.setIsActive(true);
                collectionRepository.save(collection);
            }
        });
    }

    private void createSizeIfNotExists(String sizeName) {
        if (!sizeRepository.existsBySizeName(sizeName)) {
            Size size = new Size(null, sizeName);
            sizeRepository.save(size);
        }
    }

    // Helper method for batch save if not exists
    private <T> void saveEntitiesIfNotExists(List<T> entities, java.util.function.Function<T, String> nameExtractor, java.util.function.Predicate<String> existsCheck, java.util.function.Consumer<List<T>> saveAction) {
        List<T> toSave = entities.stream()
                .filter(entity -> !existsCheck.test(nameExtractor.apply(entity)))
                .toList();
        if (!toSave.isEmpty()) {
            saveAction.accept(toSave);
        }
    }
}
