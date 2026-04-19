package vn.webapp.backend.auction.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.ProductCategory;
import vn.webapp.backend.auction.repository.ProductCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory getById(int id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND));
    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        productCategory.setId(0);
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteProductCategory(Integer id) {
            var existingBookCategory = productCategoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_CATEGORY_NOT_FOUND));
            existingBookCategory.getJewelries()
                    .forEach((book) -> book.setCategory(null));
        productCategoryRepository.deleteById(id);
    }
}
