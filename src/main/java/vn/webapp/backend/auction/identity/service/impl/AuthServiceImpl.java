package vn.webapp.backend.auction.identity.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.domain.entity.Token;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.identity.domain.enums.AccountState;
import vn.webapp.backend.auction.identity.domain.enums.Role;
import vn.webapp.backend.auction.identity.domain.enums.TokenType;
import vn.webapp.backend.auction.identity.domain.repository.BankRepository;
import vn.webapp.backend.auction.identity.domain.repository.TokenRepository;
import vn.webapp.backend.auction.identity.domain.repository.UserRepository;
import vn.webapp.backend.auction.identity.dto.request.ActivateAccountRequest;
import vn.webapp.backend.auction.identity.dto.request.ChangePasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.ForgotPasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.LoginRequest;
import vn.webapp.backend.auction.identity.dto.request.RegisterRequest;
import vn.webapp.backend.auction.identity.dto.request.ResetPasswordRequest;
import vn.webapp.backend.auction.identity.dto.request.RotateRefreshTokenRequest;
import vn.webapp.backend.auction.identity.dto.response.AuthResponse;
import vn.webapp.backend.auction.identity.infrastructure.mapper.UserMapper;
import vn.webapp.backend.auction.identity.service.AuthService;
import vn.webapp.backend.auction.identity.service.JwtService;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BankRepository bankRepository;

    @Override
    public AuthResponse login(LoginRequest request, String ipAddress, String deviceInfo) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseGet(() -> userRepository.findByEmail(request.getUsername())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
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
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (userRepository.findByUsername(username).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED, "Tên đăng nhập đã tồn tại");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED, "Email đã được sử dụng");
        }

        var bank = request.getBankId() == null
                ? null
                : bankRepository.findById(request.getBankId())
                        .orElseThrow(() -> new AppException(ErrorCode.BANK_NOT_FOUND));

        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(email)
                .phone(request.getPhone().trim())
                .address(request.getAddress().trim())
                .city(request.getCity().trim())
                .district(request.getDistrict().trim())
                .ward(request.getWard().trim())
                .yob(request.getYob().trim())
                .cccd(request.getCccd().trim())
                .cccdFirst(request.getCccdFirst())
                .cccdLast(request.getCccdLast())
                .cccdFrom(request.getCccdFrom())
                .bank(bank)
                .bankAccountNumber(valueOrEmpty(request.getBankAccountNumber()))
                .bankAccountName(valueOrEmpty(request.getBankAccountName()))
                .registerDate(Timestamp.from(Instant.now()))
                .state(AccountState.ACTIVE)
                .role(Role.MEMBER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken, refreshToken, null, null);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userMapper.toUserResponse(user))
                .build();
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value.trim();
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
