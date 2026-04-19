package vn.webapp.backend.auction.service.product;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.ProductCreateRequest;
import vn.webapp.backend.auction.dto.ProductUpdateRequest;
import vn.webapp.backend.auction.dto.SendProductFromUserRequest;
import vn.webapp.backend.auction.enums.ProductMaterial;
import vn.webapp.backend.auction.enums.ProductState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.Product;
import vn.webapp.backend.auction.model.ProductCategory;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.ProductCategoryRepository;
import vn.webapp.backend.auction.repository.ProductRepository;
import vn.webapp.backend.auction.repository.UserRepository;
import vn.webapp.backend.auction.service.email.EmailService;
import vn.webapp.backend.auction.service.jwt.JwtService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final EmailService emailService;
    private final JwtService jwtService;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));
    }

    @Override
    public Page<Product> getJewelriesByUsername(String username, Pageable pageable) {
        return productRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Page<Product> getJewelriesActiveByUserId(Integer userId, String productName,Pageable pageable) {
        return productRepository.findProductActiveByUserId(userId, productName, pageable );
    }

    @Override
    public Product requestProduct(SendProductFromUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.userId());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + request.userId() + " not found");
        }

        Optional<ProductCategory> optionalCategory = productCategoryRepository.findByName(request.category());

        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("Category with ID " + request.category() + " not found");
        }

        User user = optionalUser.get();
        ProductCategory category = optionalCategory.get();

        Product product = new Product();
        product.setUser(user);
        product.setBuyNowPrice(request.buyNowPrice());
        product.setDescription(request.description());
        product.setMaterial(request.material());
        product.setWeight(request.weight());
        product.setCategory(category);
        product.setBrand(request.brand());
//        product.setIsHolding(false);
        product.setName(request.name());
        product.setCreateDate(Timestamp.from(Instant.now()));
        product.setState(ProductState.APPROVING);

        productRepository.save(product);

        return product;
    }

    @Override
    public Product getLatestProduct() {
        return productRepository.findLatestProduct().get(0);
    }

    @Override
    public Product setStateWithHolding(Integer id,boolean isHolding, ProductState state) throws MessagingException {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));
        var exitingUser = existingProduct.getUser();
        System.out.println("Holding: " + isHolding );
        existingProduct.setState(state);
        existingProduct.setIsHolding(isHolding);
        if(isHolding) {
            existingProduct.setReceivedDate(Timestamp.from(Instant.now()));
            emailService.sendConfirmHoldingEmail(exitingUser.getEmail(), exitingUser.getFullName(), existingProduct.getName());
        }else {
            existingProduct.setDeliveryDate(Timestamp.from(Instant.now()));
        }
        return  existingProduct;
    }

    @Override
    public List<Product> getProductByUsername(String username) {
        List<Product> productList = productRepository.findProductByUsername(username);
        if (productList.isEmpty()) {
            throw new ResourceNotFoundException("User '" + username + "' does not have any product items.");
        }
        return productList;
    }

    @Override
    public void deleteProduct(Integer id) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));
        existingProduct.setState(ProductState.HIDDEN);
    }

    @Override
    public List<Product> getJewelriesByCategoryId(Integer id) {
        return productRepository.findProductByCategoryId(id);
    }

    @Override
    public List<Product> getJewelriesByNameContain(String key) {
        return productRepository.getJewelriesByNameContaining(key);
    }

    @Override
    public Page<Product> getAllJewelries(Pageable pageable) {
        return productRepository.findByState(ProductState.ACTIVE, pageable);
    }

    @Override
    public Page<Product> getJewelriesManager(ProductState state, String productName, String category, Pageable pageable) {
        if (category.equals("Tất cả"))
            return productRepository.findJewelriesManager(state,productName,null,pageable);
        return productRepository.findJewelriesManager(state, productName, category, pageable);
    }

    @Override
    public Page<Product> getJewelriesInWaitList(Pageable pageable) {
        return productRepository.findProductInWaitlist(pageable);
    }

    @Override
    public Page<Product> getProductPassed(String productName, String category, Pageable pageable) {
        if (category.equals("Tất cả")) {
            return productRepository.getPassedProduct(productName, null, pageable);
        }
        return productRepository.getPassedProduct(productName, category, pageable);
    }

    @Override
    public Page<Product> getProductByStateAndIsHolding(ProductState state, Boolean isHolding,String category, String productName, Pageable pageable) {
        if (category.equals("Tất cả"))
            return productRepository.findProductByStateAndIsHolding(state,isHolding,null,productName,pageable);
        return productRepository.findProductByStateAndIsHolding(state,isHolding,category,productName,pageable);
    }

    @Override
    public Page<Product> getProductReturnedViolator(String category, String productName, Pageable pageable) {
        if (category.equals("Tất cả"))
            return productRepository.findProductReturnedViolator(null,productName,pageable);
        return productRepository.findProductReturnedViolator(category,productName,pageable);
    }

    @Override
    public Page<Product> getJewelriesInHandOver(Pageable pageable) {
        return productRepository.findProductInHandOver(pageable);
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product) {
        var existingProduct = productRepository.findById(product.id())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND));

        var category = productCategoryRepository.findByName(product.category())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND));

        existingProduct.setName(product.name());
        existingProduct.setBuyNowPrice(product.buyNowPrice());
        existingProduct.setCategory(category);
        existingProduct.setDescription(product.description());
        existingProduct.setMaterial(product.material());
        existingProduct.setBrand(product.brand());
        existingProduct.setWeight(product.weight());
        existingProduct.setCreateDate(product.createDate());

        return existingProduct;
    }

    @Override
    public Product createProduct(ProductCreateRequest request) {
        var username = jwtService.extractUsername(request.token());
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        ProductCategory category = productCategoryRepository.findByName(request.category())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND));

        Product product = Product.builder()
                .name(request.name())
                .user(user)
                .brand(request.brand())
                .material(ProductMaterial.valueOf(request.material()))
                .description(request.description())
                .buyNowPrice(request.buyNowPrice())
                .weight(request.weight())
                .state(ProductState.ACTIVE)
                .createDate(Timestamp.from(Instant.now()))
                .receivedDate(Timestamp.from(Instant.now()))
                .isHolding(true)
                .brand(request.brand())
                .category(category)
                .build();
        productRepository.save(product);
        return product;
    }
}
