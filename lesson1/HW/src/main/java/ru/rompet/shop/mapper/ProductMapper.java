package ru.rompet.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rompet.shop.dto.ProductDto;
import ru.rompet.shop.dto.cart.CartItem;
import ru.rompet.shop.model.Category;
import ru.rompet.shop.model.Product;
import ru.rompet.shop.repository.CategoryRepository;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected CategoryRepository categoryRepository;

    @Mappings({
            @Mapping(source = "category", target = "category", qualifiedByName = "categoryTitleToCategory"),
            @Mapping(source = "price", target = "price", qualifiedByName = "bigDecimalToString")
    })
    public abstract Product productDtoToProduct(ProductDto productDto);

    @Mappings({
            @Mapping(source = "price", target = "price", qualifiedByName = "stringToBigDecimal"),
            @Mapping(source = "category.title", target = "category")
    })
    public abstract ProductDto productToProductDto(Product product);

    @Mappings({
            @Mapping(source = "id", target = "productId"),
            @Mapping(source = "price", target = "pricePerOne")
    })
    public abstract CartItem productToCartItem(Product product);


    @Named("bigDecimalToString")
    public String bigDecimalToString(BigDecimal price) {
        return price.toString();
    }

    @Named("stringToBigDecimal")
    public BigDecimal stringToBigDecimal(String price) {
        return new BigDecimal(price);
    }

    @Named("categoryTitleToCategory")
    public Category categoryTitleToCategory(String categoryTitle) {
        return categoryRepository.findByTitle(categoryTitle).orElseThrow(() -> new RuntimeException("Категория не найдена " + categoryTitle));
    }
}
