package ru.rompet.shop.service;

import org.springframework.stereotype.Service;
import ru.rompet.shop.mapper.ProductMapper;

import ru.rompet.shop.dto.cart.Cart;

import javax.annotation.PostConstruct;

@Service
public class CartService {

    private ProductService productService;
    private ProductMapper productMapper;
    private Cart cart;

    public CartService(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCartForCurrentUser() {
        return cart;
    }

    public Cart addProductById(Long id) {
        productService.findById(id).ifPresent(product -> cart.addItem(productMapper.productToCartItem(product)));
        return cart;
    }

    public Cart removeProductById(Long id) {
        cart.removeItem(id);
        return cart;
    }
}