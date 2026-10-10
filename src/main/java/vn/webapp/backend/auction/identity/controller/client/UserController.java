package vn.webapp.backend.auction.identity.controller.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.domain.enums.AccountState;
import vn.webapp.backend.auction.identity.domain.enums.Role;
import vn.webapp.backend.auction.identity.dto.request.DisableUserRequest;
import vn.webapp.backend.auction.identity.dto.request.UserUpdateRequest;
import vn.webapp.backend.auction.identity.dto.response.UserResponse;
import vn.webapp.backend.auction.identity.service.UserService;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;

@RestController
@RequestMapping(ApiEndpoint.Users.BASE)
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping(ApiEndpoint.Users.ID)
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
		return ResponseEntity.ok(ApiResponse.success(userService.getUserById(id)));
	}

	@GetMapping(ApiEndpoint.Users.BY_USERNAME)
	public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok(ApiResponse.success(userService.getUserByUsername(username)));
	}

	@GetMapping(ApiEndpoint.Users.BY_EMAIL)
	public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(ApiResponse.success(userService.getUserByEmail(email)));
	}

	@GetMapping(ApiEndpoint.Users.SORTED_PAGED)
	public ResponseEntity<ApiResponse<Page<UserResponse>>> getUsers(
			@RequestParam(required = false) String fullName,
			@RequestParam(required = false) Role role,
			@RequestParam(required = false) AccountState state,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, direction, sortBy);
		return ResponseEntity.ok(ApiResponse.success(userService.getUsers(fullName, role, state, pageable)));
	}

	@PutMapping(ApiEndpoint.Users.ID)
	public ResponseEntity<ApiResponse<UserResponse>> updateUser(
			@PathVariable Integer id, @RequestBody UserUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(userService.updateUser(id, request)));
	}

	@PutMapping(ApiEndpoint.Users.STATUS)
	public ResponseEntity<ApiResponse<Void>> setUserState(
			@PathVariable Integer id, @RequestParam AccountState state) {
		userService.setUserState(id, state);
		return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái thành công", null));
	}

	@PutMapping(ApiEndpoint.Users.BAN)
	public ResponseEntity<ApiResponse<Void>> disableUser(@RequestBody DisableUserRequest request) {
		userService.disableUser(request);
		return ResponseEntity.ok(ApiResponse.success("Vô hiệu hóa tài khoản thành công", null));
	}

	@GetMapping(ApiEndpoint.Users.WINNER)
	public ResponseEntity<ApiResponse<UserResponse>> getWinnerByAuctionId(@PathVariable Integer auctionId) {
		return ResponseEntity.ok(ApiResponse.success(
				userService.getUserByUsername(userService.getWinnerByAuctionId(auctionId).getUsername())));
	}
}
