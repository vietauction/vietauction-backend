package vn.webapp.backend.auction.identity.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.domain.repository.BankRepository;
import vn.webapp.backend.auction.identity.dto.response.BankResponse;
import vn.webapp.backend.auction.identity.infrastructure.mapper.UserMapper;
import vn.webapp.backend.auction.identity.service.BankService;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

	private final BankRepository bankRepository;
	private final UserMapper userMapper;

	@Override
	public List<BankResponse> getAllBanks() {
		return bankRepository.findAll().stream()
				.map(userMapper::toBankResponse)
				.collect(Collectors.toList());
	}

	@Override
	public BankResponse getBankById(Integer id) {
		return userMapper.toBankResponse(bankRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.BANK_NOT_FOUND)));
	}
}
