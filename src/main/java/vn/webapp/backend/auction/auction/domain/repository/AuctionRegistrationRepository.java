package vn.webapp.backend.auction.auction.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.webapp.backend.auction.auction.domain.entity.AuctionRegistration;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Integer> {
}
