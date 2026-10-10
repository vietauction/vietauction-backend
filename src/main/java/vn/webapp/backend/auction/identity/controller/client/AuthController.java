package vn.webapp.backend.auction.identity.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.dto.request.ActivateAccountRequest;
import vn.webapp.backend.auction.identity.dto.request.ChangePasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.ForgotPasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.LoginRequest;
import vn.webapp.backend.auction.identity.dto.request.RegisterRequest;
import vn.webapp.backend.auction.identity.dto.request.ResetPasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.RotateRefreshTokenRequest;
import vn.webapp.backend.auction.identity.dto.response.AuthResponse;
import vn.webapp.backend.auction.identity.service.AuthService;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;

@RestController
@RequestMapping(ApiEndpoint.Auth.BASE)
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping(ApiEndpoint.Auth.LOGIN)
	public ResponseEntity<ApiResponse<AuthResponse>> login(
			@RequestBody @Valid LoginRequest request, HttpServletRequest httpRequest) {
		String ipAddress = httpRequest.getRemoteAddr();
		String deviceInfo = httpRequest.getHeader("User-Agent");
		AuthResponse authResponse = authService.login(request, ipAddress, deviceInfo);
		return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", authResponse));
	}

	@PostMapping(ApiEndpoint.Auth.REGISTER)
	public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Đăng ký thành công", authService.register(request)));
	}

	@PostMapping(ApiEndpoint.Auth.REFRESH_TOKEN)
	public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody RotateRefreshTokenRequest request) {
		return ResponseEntity.ok(ApiResponse.success(authService.refreshToken(request)));
	}

	@PostMapping(ApiEndpoint.Auth.ACTIVATE)
	public ResponseEntity<ApiResponse<Void>> activateAccount(@RequestBody ActivateAccountRequest request) {
		authService.activateAccount(request);
		return ResponseEntity.ok(ApiResponse.success("Kích hoạt tài khoản thành công", null));
	}

	@PostMapping(ApiEndpoint.Auth.FORGOT_PASSWORD)
	public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
		authService.forgotPassword(request);
		return ResponseEntity.ok(ApiResponse.success("Email đặt lại mật khẩu đã được gửi", null));
	}

	@PostMapping(ApiEndpoint.Auth.RESET_PASSWORD)
	public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
		authService.resetPassword(request);
		return ResponseEntity.ok(ApiResponse.success("Đặt lại mật khẩu thành công", null));
	}

	@PostMapping(ApiEndpoint.Auth.CHANGE_PASSWORD)
	public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
		authService.changePassword(request);
		return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công", null));
	}
}
