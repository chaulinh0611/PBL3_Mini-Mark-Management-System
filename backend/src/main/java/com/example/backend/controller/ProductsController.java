package com.example.backend.controller;

import com.example.backend.model.Products;
import com.example.backend.DTO.ProductRequestDTO;
import com.example.backend.model.Category;
import com.example.backend.model.Suppliers;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.SupplierRepository;
import com.example.backend.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping
    public List<Products> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        System.out.println("🟢 Tổng số sản phẩm: " + products.size());
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable String id) {
        return productsService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Products> searchByName(@RequestParam String name) {
        return productsService.searchByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<Products> getByCategory(@PathVariable String categoryId) {
        return productsService.getProductByCategory(categoryId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<Products> getBySupplier(@PathVariable String supplierId) {
        return productsService.getProductBySupplier(supplierId);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/suppliers")
    public ResponseEntity<List<Suppliers>> getSuppliers() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO dto) {
        try {
            Products product = new Products();
            product.setName(dto.getName());
            product.setQuantity(dto.getQuantity());
            // product.setPrice(dto.getPrice());
            // int randomStockPrice = (int) (dto.getPrice() * (0.70 + Math.random() * 0.10)); // 70% - 80%
            // product.setStock_price(randomStockPrice);
            product.setStock_price(dto.getPrice()); // giá nhập do người dùng nhập

            double markupRatio = 0.70 + Math.random() * 0.10; // random từ 70% đến 80%
            int calculatedPrice = (int) (dto.getPrice() / markupRatio); // tính giá bán

            int roundedPrice = (int) (Math.ceil(calculatedPrice / 1000.0) * 1000);
            product.setPrice(roundedPrice); // gán giá bán
            product.setImg(dto.getImg() != null && !dto.getImg().isEmpty() ? dto.getImg() : "default_image.png");
            product.setSell(Optional.ofNullable(dto.getSell()).orElse(0));
            product.setExpiration_date(dto.getExpirationDate());
            product.setStatus("ACTIVE"); // Mặc định là DRAFT khi mới tạo
            Category category = categoryRepository.findById(dto.getCategory_id())
                    .orElseThrow(() -> new RuntimeException("❌ Category not found"));
            Suppliers supplier = supplierRepository.findById(dto.getSupplier_id())
                    .orElseThrow(() -> new RuntimeException("❌ Supplier not found"));

            product.setCategory(category);
            product.setSupplier(supplier);

            return ResponseEntity.ok(productsService.saveProducts(product));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Server Error: " + ex.getMessage());
        }
    }
    @PostMapping("/importProduct")
    public ResponseEntity<?> importProduct(@RequestBody ProductRequestDTO dto) {
        try {
            Products product = new Products();
            product.setName(dto.getName());
            product.setQuantity(0); // ✅ luôn mặc định 0 khi mới nhập dự kiến
            product.setStock_price(dto.getPrice()); // giá nhập do người dùng nhập

            // ✅ Tính giá bán từ giá nhập
            double markupRatio = 0.70 + Math.random() * 0.10;
            int calculatedPrice = (int) (dto.getPrice() / markupRatio);
            int roundedPrice = (int) (Math.ceil(calculatedPrice / 1000.0) * 1000);
            product.setPrice(roundedPrice);

            product.setImg(dto.getImg() != null && !dto.getImg().isEmpty() ? dto.getImg() : "default_image.png");
            product.setSell(Optional.ofNullable(dto.getSell()).orElse(0));
            product.setExpiration_date(dto.getExpirationDate());

            product.setStatus("DRAFT");

            Category category = categoryRepository.findById(dto.getCategory_id())
                    .orElseThrow(() -> new RuntimeException("❌ Category not found"));
            Suppliers supplier = supplierRepository.findById(dto.getSupplier_id())
                    .orElseThrow(() -> new RuntimeException("❌ Supplier not found"));

            product.setCategory(category);
            product.setSupplier(supplier);

            return ResponseEntity.ok(productsService.saveProducts(product));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Server Error: " + ex.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductRequestDTO dto) {
        Optional<Products> existing = productsService.getProductById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Products product = existing.get();
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setImg(dto.getImg());
        product.setSell(Optional.ofNullable(dto.getSell()).orElse(0));
        product.setExpiration_date(dto.getExpirationDate());
        product.setStatus(dto.getStatus());

        // Giá nhập (user nhập từ frontend)
        product.setStock_price(dto.getPrice());

        // Giá bán = giá nhập * (1 + markup)
        double markupRatio = 0.20 + Math.random() * 0.10;
        int calculatedPrice = (int) (dto.getPrice() * (1 + markupRatio));
        int roundedPrice = (int) (Math.ceil(calculatedPrice / 1000.0) * 1000);
        product.setPrice(roundedPrice);

        Category category = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new RuntimeException("❌ Category not found"));
        Suppliers supplier = supplierRepository.findById(dto.getSupplier_id())
                .orElseThrow(() -> new RuntimeException("❌ Supplier not found"));

        product.setCategory(category);
        product.setSupplier(supplier);

        return ResponseEntity.ok(productsService.saveProducts(product));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productsService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("❌ Error: " + ex.getMessage());
        }
    }
}
