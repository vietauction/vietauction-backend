package vn.webapp.backend.auction.identity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.identity.domain.entity.Token;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.identity.domain.enums.TokenType;
import vn.webapp.backend.auction.identity.domain.repository.TokenRepository;
import vn.webapp.backend.auction.identity.domain.repository.UserRepository;
import vn.webapp.backend.auction.identity.dto.request.*;
import vn.webapp.backend.auction.identity.dto.response.AuthResponse;
import vn.webapp.backend.auction.identity.infrastructure.mapper.UserMapper;
import vn.webapp.backend.auction.identity.service.AuthService;
import vn.webapp.backend.auction.identity.service.JwtService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(LoginRequest request, String ipAddress, String deviceInfo) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken, refreshToken, ipAddress, deviceInfo);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userMapper.toUserResponse(user))
                .build();
    }

    private void saveUserToken(User user, String jwtToken, String refreshToken, String ipAddress, String deviceInfo) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .ipAddress(ipAddress)
                .deviceInfo(deviceInfo)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
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

