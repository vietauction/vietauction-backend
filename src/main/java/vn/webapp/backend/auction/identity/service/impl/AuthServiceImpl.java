package vn.webapp.backend.auction.identity.service.impl;

import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.identity.service.AuthService;
import vn.webapp.backend.auction.identity.dto.request.*;
import vn.webapp.backend.auction.identity.dto.response.AuthResponse;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponse login(LoginRequest request, String ipAddress, String deviceInfo) {
        return null;
    }
    @Override
    public AuthResponse register(RegisterRequest request) {
        return null;
    }
    @Override
    public AuthResponse refreshToken(RotateRefreshTokenRequest request) {
        return null;
    }
    @Override
    public void logout(String token) {}
    @Override
    public void activateAccount(ActivateAccountRequest request) {}
    @Override
    public void forgotPassword(ForgotPasswordRequest request) {}
    @Override
    public void resetPassword(ResetPasswordRequest request) {}
    @Override
    public void changePassword(ChangePasswordRequest request) {}
}
