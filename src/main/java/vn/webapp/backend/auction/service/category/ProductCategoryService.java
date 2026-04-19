package vn.webapp.backend.auction.service.category;

import vn.webapp.backend.auction.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAll();
    ProductCategory getById(int id);
    ProductCategory saveProductCategory(ProductCategory productCategory);
    void deleteProductCategory(Integer id);
}
