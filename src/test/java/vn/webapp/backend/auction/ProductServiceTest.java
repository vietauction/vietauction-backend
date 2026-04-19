package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;

import vn.webapp.backend.auction.model.Product;
import vn.webapp.backend.auction.service.product.ProductService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProductService productService;

    @Test
    public void testGetProductByIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        Product product = productService.getProductById(id);

        // Assert
        assertNotNull(product);
        assertEquals(product.getId(), id);
    }

    @Test
    public void testGetJewelriesByUsernameReturnsWell(){
        // Expected
        String username = "phuuthanh2003";
        // Act
        List<Product> jewelries = productService.getProductByUsername(username);
        // Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
        // Verify that each returned Product item belongs to the expected username
        for (Product product : jewelries) {
            assertEquals(product.getUser().getUsername(), username);
        }
    }

    @Test
    public void TestGetJeweriesByCategoryIdReturnsWell(){
        // Expected
        Integer id = 1;
        // Act
        List<Product> jewelries = productService.getJewelriesByCategoryId(id);
        // Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
        // Verify that each returned Product item belongs to the expected username
        for (Product product : jewelries) {
            assertEquals(product.getCategory().getId(), id);
        }
    }

    @Test
    public void testGetJewelriesByNameContainReturnsWell(){
        // Expected
        String key = "Nhẫn";
        // Act
        List<Product> jewelries = productService.getJewelriesByNameContain(key);
        // Assert
        assertNotNull(jewelries);
        assertFalse(jewelries.isEmpty());
        // Verify that each returned Product item belongs to the expected username
        for (Product product : jewelries) {
            assertTrue(product.getDescription().toLowerCase().contains(key.toLowerCase()));
        }
    }

    @Test
    public void testGetProductByIdReturnsNull(){
        Integer nonExistId = 99;

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(nonExistId));
    }

//    @Test
//    public void testGetJewelriesByUsernameReturnNull(){
//        String nonExistUsername = "tam12345";
//        assertThrows(ResourceNotFoundException.class, () -> productService.getJewelriesByUsername(nonExistUsername));
////    }
//    @Test
//    public void TestGetJeweriesByCategoryIdReturnsNulll(){
//
//    }
//    @Test
//    public void testGetJewelriesByNameContainReturnsNull(){
//
//    }

}

