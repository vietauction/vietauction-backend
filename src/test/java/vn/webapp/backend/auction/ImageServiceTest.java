package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.service.image.ImageService;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class ImageServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private ImageService imageService;

    @Test
    public void testGetImagesByProductIdReturnsWell(){
        // Expected
        Integer id = 1;

        //Act
        List<Image> images = imageService.getImagesByProductId(id);

        //Assert
        assertNotNull(images);
        assertFalse(images.isEmpty());

        for (Image image : images) {
            assertEquals(image.getProduct().getId(), id);
        }
    }

    @Test
    public void testGetImagesByProductIdReturnsNull(){
        // Expected
        Integer nonExistId = 99;

        // Act
        List<Image> images = imageService.getImagesByProductId(nonExistId);

        //Assert
        assertNotNull(images);
        assertTrue(images.isEmpty());
    }

    @Test
    public void testGetImageByIconAndProductIdReturnsWell(){
        // Expected
        Integer id = 1;

        // Act
        Image image = imageService.getImageByIconAndProductId(id);

        // Assert
        assertNotNull(image);
        assertEquals(image.getId(), id);
    }

//    @Test CHƯA LÀM TRẢ VỀ
//    public void testGetImageByIconAndProductIdReturnsNull(){
//        // Expected
//        Integer nonExistId = 99;
//
//        // Act and Assert
//        assertThrows(ResourceNotFoundException.class, () -> imageService.getImageByIconAndProductId(nonExistId));
//
//    }
}
