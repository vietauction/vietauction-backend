package vn.webapp.backend.auction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.webapp.backend.auction.dto.ImageRequest;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.service.image.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/get-by-product/{id}")
    public ResponseEntity<List<Image>> getByProduct(@PathVariable Integer id)  {
        return ResponseEntity.ok(imageService.getImagesByProductId(id));
    }

    @GetMapping("/get-icon-product/{id}")
    public ResponseEntity<Image> getIconByProduct(@PathVariable Integer id)  {
        return ResponseEntity.ok(imageService.getImageByIconAndProductId(id));
    }

    @PostMapping("/add-image")
    public ResponseEntity<Image> createImage(@RequestBody ImageRequest request){
        return ResponseEntity.ok(imageService.createImage(request));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteImagesByProductId(@PathVariable Integer productId) {
        imageService.deleteByProductId(productId);
        return ResponseEntity.noContent().build();
    }

}
