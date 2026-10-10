package vn.webapp.backend.auction.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.notification.dto.BidResponse;
import vn.webapp.backend.auction.notification.dto.KickOutResponse;
import vn.webapp.backend.auction.auction.domain.entity.Auction;
import vn.webapp.backend.auction.auction.domain.entity.AuctionRegistration;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.auction.domain.repository.AuctionRegistrationRepository;
import vn.webapp.backend.auction.auction.domain.repository.AuctionRepository;
import vn.webapp.backend.auction.identity.domain.repository.UserRepository;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class RealTimeService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final AuctionRegistrationRepository auctionRegistrationRepository;

    public BidResponse bidRealtime(Integer id, Long bonusTime, String username) {
        Auction auction = auctionRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        Timestamp currentEndDate = auction.getEndDate();
        Timestamp newEndDate = new Timestamp(currentEndDate.getTime() + bonusTime);
        auction.setEndDate(newEndDate);
        auctionRepository.save(auction);

        return new BidResponse(auction.getLastPrice(), auction.getProduct().getBuyNowPrice(), auction.getId(),
                auction.getEndDate(), bonusTime, user.getUsername());
    }

    public KickOutResponse staffKickOutMemberRealtime(Integer id, String username) {
        Auction auction = auctionRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();
        // Placeholder for logic finding invalid registration
        return new KickOutResponse(user.getId(), auction.getLastPrice(), "Kicked", auction.getEndDate());
    }
}
