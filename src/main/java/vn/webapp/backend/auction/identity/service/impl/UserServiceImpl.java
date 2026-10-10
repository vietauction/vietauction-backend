package vn.webapp.backend.auction.identity.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.identity.domain.enums.AccountState;
import vn.webapp.backend.auction.identity.domain.enums.Role;
import vn.webapp.backend.auction.identity.domain.repository.UserRepository;
import vn.webapp.backend.auction.identity.dto.request.DisableUserRequest;
import vn.webapp.backend.auction.identity.dto.request.UserUpdateRequest;
import vn.webapp.backend.auction.identity.dto.response.UserResponse;
import vn.webapp.backend.auction.identity.infrastructure.mapper.UserMapper;
import vn.webapp.backend.auction.identity.service.UserService;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public UserResponse getUserById(Integer id) {
		return userMapper.toUserResponse(userRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
	}

	@Override
	public UserResponse getUserByUsername(String username) {
		return userMapper.toUserResponse(userRepository.findByUsername(username)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
	}

	@Override
	public UserResponse getUserByEmail(String email) {
		return userMapper.toUserResponse(userRepository.findByEmail(email)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
	}

	@Override
	public Page<UserResponse> getUsers(String fullName, Role role, AccountState state, Pageable pageable) {
		return userRepository.findByFullNameContainingAndRoleAndNotState(fullName, role, AccountState.DISABLE, pageable)
				.map(userMapper::toUserResponse);
	}

	@Override
	public UserResponse updateUser(Integer id, UserUpdateRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
		if (request.getLastName() != null) user.setLastName(request.getLastName());
		if (request.getPhone() != null) user.setPhone(request.getPhone());
		if (request.getAddress() != null) user.setAddress(request.getAddress());
		if (request.getCity() != null) user.setCity(request.getCity());
		if (request.getDistrict() != null) user.setDistrict(request.getDistrict());
		if (request.getWard() != null) user.setWard(request.getWard());
		if (request.getAvatar() != null) user.setAvatar(request.getAvatar());
		if (request.getCccdFirst() != null) user.setCccdFirst(request.getCccdFirst());
		if (request.getCccdLast() != null) user.setCccdLast(request.getCccdLast());
		if (request.getCccdFrom() != null) user.setCccdFrom(request.getCccdFrom());
		if (request.getBankAccountNumber() != null) user.setBankAccountNumber(request.getBankAccountNumber());
		if (request.getBankAccountName() != null) user.setBankAccountName(request.getBankAccountName());
		return userMapper.toUserResponse(userRepository.save(user));
	}

	@Override
	public void disableUser(DisableUserRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		user.setState(AccountState.DISABLE);
		user.setBanReason(request.getReason());
		userRepository.save(user);
	}

	@Override
	public void setUserState(Integer id, AccountState state) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
		user.setState(state);
		userRepository.save(user);
	}

	@Override
	public User getWinnerByAuctionId(Integer auctionId) {
		return userRepository.findLatestUserInAuctionHistoryByAuctionId(auctionId)
				.orElse(null);
	}
}
