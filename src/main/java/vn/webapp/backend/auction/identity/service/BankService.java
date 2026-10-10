package vn.webapp.backend.auction.identity.service;

import java.util.List;

import vn.webapp.backend.auction.identity.domain.entity.Bank;
import vn.webapp.backend.auction.identity.dto.response.BankResponse;

public interface BankService {
	List<BankResponse> getAllBanks();
	BankResponse getBankById(Integer id);
}
