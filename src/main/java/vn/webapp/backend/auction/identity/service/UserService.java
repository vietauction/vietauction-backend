package vn.webapp.backend.auction.identity.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.identity.domain.enums.AccountState;
import vn.webapp.backend.auction.identity.domain.enums.Role;
import vn.webapp.backend.auction.identity.dto.request.DisableUserRequest;
import vn.webapp.backend.auction.identity.dto.request.UserUpdateRequest;
import vn.webapp.backend.auction.identity.dto.response.UserResponse;

public interface UserService {
	UserResponse getUserById(Integer id);
	UserResponse getUserByUsername(String username);
	UserResponse getUserByEmail(String email);
	Page<UserResponse> getUsers(String fullName, Role role, AccountState state, Pageable pageable);
	UserResponse updateUser(Integer id, UserUpdateRequest request);
	void disableUser(DisableUserRequest request);
	void setUserState(Integer id, AccountState state);
	User getWinnerByAuctionId(Integer auctionId);
}
