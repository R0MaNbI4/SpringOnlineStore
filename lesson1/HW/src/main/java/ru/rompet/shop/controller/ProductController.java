package ru.rompet.shop.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rompet.shop.dto.ProductDto;
import ru.rompet.shop.mapper.ProductMapper;
import ru.rompet.shop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    ProductService productService;
    ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("products/all")
    public String getAllProducts(Model model) {
        List<ProductDto> products = productService.getAll().stream()
                .map(productMapper::productToProductDto).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "product_list";
    }

    @GetMapping(value = { "products"})
    public String getProductsPage(Model model, Pageable pageable) {
        List<ProductDto> products = productService.getPage(pageable).stream()
                .map(productMapper::productToProductDto).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "product_list";
    }

    @GetMapping("")
    public String redirectToProductsPage() {
        return "redirect:products";
    }

    @GetMapping("products/info/{id}")
    public String getProductInfo(@PathVariable Long id, Model model) {
        model.addAttribute("product", productMapper.productToProductDto(productService.findById(id).orElse(null)));
        return "product_info";
    }
}
