package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.ProductCategory;
import vn.webapp.backend.auction.service.category.ProductCategoryService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class ProductCategoryServiceTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void testGetAllReturnsWell(){
        //Act
        List<ProductCategory> jewelries = productCategoryService.getAll();

        //Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
    }

    @Test
    public void testGetProductCategoryByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        ProductCategory productCategory = productCategoryService.getById(id);

        // Assert
        assertNotNull(productCategory);
        assertEquals(productCategory.getId(), id);
    }

    @Test
    public void testGetProductCategoryByIdReturnNull(){
        // Expected
        Integer nonExistId = 99;

        //Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productCategoryService.getById(nonExistId));
    }

//    @Test
//    public void testSaveProductCategory(){
//        // Expected
//        ProductCategory productCategoryToSave = ProductCategory.builder().name("Dây chuyền").build();
//        ProductCategory savedProductCategory = ProductCategory.builder()
//                .id(1)
//                .name("Dây chuyền")
//                .build();
//        //Act
//        ProductCategory result = productCategoryService.saveProductCategory(productCategoryToSave);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(savedProductCategory.getId(), result.getId());
//        assertEquals(savedProductCategory.getName(), result.getName());
//
////        verify(productCategoryRepository, times(1)).save(any(ProductCategory.class));
//    }
}
