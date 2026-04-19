package vn.webapp.backend.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.model.RequestApproval;


public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Integer> {
    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = :role " +
            "AND (:productName IS NULL OR ra.product.name LIKE %:productName%) " +
            "AND (:category IS NULL OR ra.product.category.name = :category) " +
            "AND ra.product.state = 'APPROVING' " +
            "AND ra.isConfirm = false AND ra.state = 'ACTIVE'")
    Page<RequestApproval> findRequestApprovalBySenderRole(
            @Param("role") Role role,
            @Param("productName") String productName,
            @Param("category") String category,
            Pageable pageable
    );

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.role = 'MANAGER'" +
            "AND ra.isConfirm = false AND ra.product.user.id = :memberId " +
            "AND ra.state = 'ACTIVE' AND (:productName IS NULL OR ra.product.name LIKE %:productName%) ")
    Page<RequestApproval> findRequestNeedConfirmByMember(
            @Param("memberId") Integer memberId,
            @Param("productName") String productName,
            Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra WHERE ra.sender.id = :id " +
            "AND (:productName IS NULL OR ra.product.name LIKE %:productName%) " +
            "AND (:category IS NULL OR ra.product.category.name = :category)" +
            "ORDER BY ra.isConfirm ASC")
    Page<RequestApproval> findRequestApprovalByUserId(@Param("id") Integer id, @Param("productName") String productName,@Param("category") String category, Pageable pageable);

    @Query("SELECT ra FROM RequestApproval ra " +
            "WHERE " +
            "ra.sender.role = 'MANAGER' AND " +
            "(:productName IS NULL OR ra.product.name LIKE %:productName%) " +
            "AND (:category IS NULL OR ra.product.category.name = :category) " +
            "AND ra.isConfirm = true AND ra.state = 'ACTIVE' " +
            "AND ra.product.state = 'ACTIVE' AND ra.product.isHolding = true")
    Page<RequestApproval> findRequestApprovalPassed(
            @Param("productName") String productName,
            @Param("category") String category,
            Pageable pageable
    );

}
