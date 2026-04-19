package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.ProductState;
import vn.webapp.backend.auction.model.Product;
import vn.webapp.backend.auction.model.RequestApproval;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT j FROM Product j WHERE j.user.username = :username")
    List<Product> findProductByUsername(@Param("username") String username);

    List<Product> findProductByCategoryId(Integer categoryId);

    List<Product> getJewelriesByNameContaining(String name);

    Page<Product> findByState(ProductState productState, Pageable pageable);

    @Query("SELECT j FROM Product j INNER JOIN RequestApproval r ON j.id = r.product.id " +
            "WHERE r.sender.role = 'MEMBER' AND r.isConfirm = false AND r.state = 'ACTIVE'")
    Page<Product> findProductInWaitlist(Pageable pageable);

    @Query("SELECT j FROM Product j INNER JOIN Auction a ON j.id = a.product.id WHERE a.state = 'FINISHED'")
    Page<Product> findProductInHandOver(Pageable pageable);

    @Query("SELECT j FROM Product j WHERE j.state = :state AND j.isHolding = :isHolding "+
            "AND (:productName IS NULL OR j.name LIKE %:productName%) AND (:category IS NULL OR j.category.name = :category)")
    Page<Product> findProductByStateAndIsHolding(
            @Param("state") ProductState state,
            @Param("isHolding") Boolean isHolding,
            @Param("category") String category,
            @Param("productName") String productName,
            Pageable pageable);

    @Query("SELECT j FROM Product j WHERE j.state = :state "+
            "AND (:productName IS NULL OR j.name LIKE %:productName%) "+
            " AND (:category IS NULL OR j.category.name = :category)")
    Page<Product> findJewelriesManager(
            @Param("state") ProductState state,
            @Param("productName") String productName,
            @Param("category") String category,
            Pageable pageable);

    @Query("SELECT j FROM Product j WHERE j.state = 'ACTIVE' AND j.isHolding = true " +
            "AND j.user.state ='DISABLE'" +
            "AND (:productName IS NULL OR j.name LIKE %:productName%) " +
            "AND (:category IS NULL OR j.category.name = :category)")
    Page<Product> findProductReturnedViolator(@Param("category") String category, @Param("productName") String productName, Pageable pageable);

    Page<Product> findByUserUsername(String username, Pageable pageable);

    @Query("SELECT j FROM Product j ORDER BY j.id DESC")
    List<Product> findLatestProduct();

    @Query("SELECT COUNT(j) FROM Product j WHERE j.isHolding IS NULL AND " +
            "j.state = 'APPROVING' AND j.receivedDate IS NULL AND j.deliveryDate IS NULL " +
            "AND MONTH(j.createDate) = :month AND YEAR(j.createDate) = :year")
    Integer countAllJewelriesNeedPricing(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT COUNT(j) FROM Product j WHERE j.isHolding = false AND " +
            "j.state = 'ACTIVE' AND j.receivedDate IS NULL AND j.deliveryDate IS NULL " +
            "AND MONTH(j.createDate) = :month AND YEAR(j.createDate) = :year")
    Integer countAllJewelriesPriced(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT COUNT(j) FROM Product j WHERE j.isHolding = true AND " +
            "j.state = 'ACTIVE' AND j.receivedDate IS NOT NULL AND j.deliveryDate IS NULL " +
            "AND MONTH(j.createDate) = :month AND YEAR(j.createDate) = :year")
    Integer countAllJewelriesNotHasAuction(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT COUNT(j) FROM Product j WHERE j.isHolding = true AND " +
            "j.state = 'AUCTION' AND j.receivedDate IS NOT NULL AND j.deliveryDate IS NULL " +
            "AND MONTH(j.createDate) = :month AND YEAR(j.createDate) = :year")
    Integer countAllJewelriesHasAuction(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT COUNT(j) FROM Product j WHERE j.isHolding = false AND " +
            "j.state = 'HANDED_OVER' AND j.receivedDate IS NOT NULL AND j.deliveryDate IS NOT NULL " +
            "AND MONTH(j.createDate) = :month AND YEAR(j.createDate) = :year")
    Integer countAllJewelriesHandOver(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT j FROM Product j " +
            "WHERE " +
            "(:productName IS NULL OR j.name LIKE %:productName%) " +
            "AND (:category IS NULL OR j.category.name = :category) " +
            "AND j.state = 'ACTIVE' AND j.isHolding = true AND j.user.state != 'DISABLE'")
    Page<Product> getPassedProduct(
            @Param("productName") String productName,
            @Param("category") String category,
            Pageable pageable
    );

    @Query("SELECT j FROM Product j WHERE j.user.id = :userId AND (:productName IS NULL OR j.name LIKE %:productName%) AND (j.state = 'ACTIVE' OR j.state = 'AUCTION')")
    Page<Product> findProductActiveByUserId(@Param("userId") Integer userId,@Param("productName") String productName, Pageable pageable);

}
