package vn.webapp.backend.auction.identity.service;

import vn.webapp.backend.auction.identity.dto.request.ActivateAccountRequest;
import vn.webapp.backend.auction.identity.dto.request.ChangePasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.ForgotPasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.LoginRequest;
import vn.webapp.backend.auction.identity.dto.request.RegisterRequest;
import vn.webapp.backend.auction.identity.dto.request.ResetPasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.RotateRefreshTokenRequest;
import vn.webapp.backend.auction.identity.dto.response.AuthResponse;

public interface AuthService {
	AuthResponse login(LoginRequest request, String ipAddress, String deviceInfo);
	AuthResponse register(RegisterRequest request);
	AuthResponse refreshToken(RotateRefreshTokenRequest request);
	void logout(String token);
	void activateAccount(ActivateAccountRequest request);
	void forgotPassword(ForgotPasswordRequest request);
	void resetPassword(ResetPasswordRequest request);
	void changePassword(ChangePasswordRequest request);
}
