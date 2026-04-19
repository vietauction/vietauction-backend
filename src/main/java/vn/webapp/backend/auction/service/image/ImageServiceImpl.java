package vn.webapp.backend.auction.service.image;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.ImageRequest;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.model.Product;
import vn.webapp.backend.auction.repository.ImageRepository;
import vn.webapp.backend.auction.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService  {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;


    @Override
    public List<Image> getImagesByProductId(Integer id) {
        return imageRepository.findImageByProductId(id);
    }

    @Override
    public Image getImageByIconAndProductId(Integer id) {
        return imageRepository.findImageByIconAndProductId(id);
    }

    @Override
    public Image createImage(ImageRequest request) {
        Optional<Product> existProduct = productRepository.findById(request.productId());
        if (existProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + request.productId() + " not found");
        }
        Product product = existProduct.get();
        Image image = new Image();
        image.setIcon(request.icon());
        image.setData(request.data());
        image.setProduct(product);
        imageRepository.save(image);
        return image;
    }

    @Transactional
    public void deleteByProductId(Integer productId) {
        imageRepository.deleteByProductId(productId);
    }
}
