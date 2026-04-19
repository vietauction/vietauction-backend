package vn.webapp.backend.auction.service.product;

import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.ProductCreateRequest;
import vn.webapp.backend.auction.dto.ProductUpdateRequest;
import vn.webapp.backend.auction.dto.SendProductFromUserRequest;
import vn.webapp.backend.auction.enums.ProductState;
import vn.webapp.backend.auction.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getProductById(Integer id);

    List<Product> getProductByUsername(String username);

    void deleteProduct(Integer id);

    List<Product> getJewelriesByCategoryId(Integer id);

    Page<Product> getProductPassed(String productName, String category, Pageable pageable);

    List<Product> getJewelriesByNameContain(String key);

    Page<Product> getAllJewelries(Pageable pageable);

    Page<Product> getJewelriesManager(ProductState state, String productName, String category, Pageable pageable);

    Page<Product> getJewelriesInWaitList(Pageable pageable);

    Page<Product> getProductByStateAndIsHolding(ProductState state, Boolean isHolding, String category, String productName, Pageable pageable);

    Page<Product> getProductReturnedViolator(String category, String productName, Pageable pageable);

    Page<Product> getJewelriesInHandOver(Pageable pageable);

    Page<Product> getJewelriesByUsername(String username, Pageable pageable);

    Page<Product> getJewelriesActiveByUserId(Integer userId, String productName, Pageable pageable);

    Product requestProduct(SendProductFromUserRequest request);

    Product getLatestProduct();

    Product updateProduct(ProductUpdateRequest product);

    Product createProduct(ProductCreateRequest product);

    Product setStateWithHolding(Integer id, boolean isHolding, ProductState state) throws MessagingException;

}