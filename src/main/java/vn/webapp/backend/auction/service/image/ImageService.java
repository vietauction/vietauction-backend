package vn.webapp.backend.auction.service.image;

import vn.webapp.backend.auction.dto.ImageRequest;
import vn.webapp.backend.auction.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImagesByProductId(Integer id);
    Image getImageByIconAndProductId(Integer id);
    Image createImage(ImageRequest request);
    void deleteByProductId(Integer id);
}
