package vn.webapp.backend.auction.transaction.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.transaction.domain.entity.RequestApproval;

public interface RequestApprovalRepository extends JpaRepository<RequestApproval, Integer> {
}
