package vn.webapp.backend.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.webapp.backend.auction.model.Image;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImageByProductId(Integer productId);

    @Query("SELECT i FROM Image i WHERE i.icon = true and i.product.id = :productId")
    Image findImageByIconAndProductId(@Param("productId") Integer productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.product.id = :productId")
    void deleteByProductId(@Param("productId") Integer productId);
}
