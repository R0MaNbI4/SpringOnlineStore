package ru.rompet.shop.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rompet.shop.dto.ProductDto;
import ru.rompet.shop.mapper.ProductMapper;
import ru.rompet.shop.model.Product;
import ru.rompet.shop.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    ProductService productService;
    ProductMapper productMapper;

    public AdminController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("all")
    public String getAllProducts(Model model) {
        List<ProductDto> products = productService.getAll().stream()
                .map(productMapper::productToProductDto).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin/index";
    }

    @GetMapping("")
    public String getProductsPage(Model model, Pageable pageable) {
        List<ProductDto> products = productService.getPage(pageable).stream()
                .map(productMapper::productToProductDto).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin/index";
    }

    @PostMapping("delete/{id}") // Delete
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String getProductUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "admin/update_form";
    }

    @PostMapping("update/{id}") // Put
    public String updateProduct(@PathVariable Long id, @Valid ProductDto productDto) {
        productDto.setId(id);
        productService.save(productMapper.productDtoToProduct(productDto));
        return "redirect:/admin";
    }

    @GetMapping("add")
    public String getProductAddForm() {
        return "admin/add_form";
    }

    @PostMapping("add")
    public String saveProduct(@Valid Product product) {
        productService.save(product);
        return "redirect:/admin";
    }
}
