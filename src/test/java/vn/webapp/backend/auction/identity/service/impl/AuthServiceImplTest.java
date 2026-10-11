package vn.webapp.backend.auction.identity.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import vn.webapp.backend.auction.identity.domain.entity.Token;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.identity.domain.enums.AccountState;
import vn.webapp.backend.auction.identity.domain.enums.Role;
import vn.webapp.backend.auction.identity.domain.repository.BankRepository;
import vn.webapp.backend.auction.identity.domain.repository.TokenRepository;
import vn.webapp.backend.auction.identity.domain.repository.UserRepository;
import vn.webapp.backend.auction.identity.dto.request.LoginRequest;
import vn.webapp.backend.auction.identity.dto.request.RegisterRequest;
import vn.webapp.backend.auction.identity.dto.response.UserResponse;
import vn.webapp.backend.auction.identity.infrastructure.mapper.UserMapper;
import vn.webapp.backend.auction.identity.service.JwtService;
import vn.webapp.backend.auction.shared.exception.AppException;
import vn.webapp.backend.auction.shared.exception.ErrorCode;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenRepository tokenRepository;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserMapper userMapper;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private BankRepository bankRepository;

	@InjectMocks
	private AuthServiceImpl authService;

	@Test
	void registerCreatesActiveMemberAndReturnsSession() {
		RegisterRequest request = validRegisterRequest();
		UserResponse userResponse = UserResponse.builder()
				.id(5)
				.username("new.member")
				.email("member@example.vn")
				.role(Role.MEMBER)
				.state(AccountState.ACTIVE)
				.build();

		when(userRepository.findByUsername("new.member")).thenReturn(Optional.empty());
		when(userRepository.findByEmail("member@example.vn")).thenReturn(Optional.empty());
		when(passwordEncoder.encode("Password1")).thenReturn("encoded-password");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(jwtService.generateToken(any(User.class))).thenReturn("access-token");
		when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refresh-token");
		when(userMapper.toUserResponse(any(User.class))).thenReturn(userResponse);

		var response = authService.register(request);

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userCaptor.capture());
		User savedUser = userCaptor.getValue();
		assertThat(savedUser.getPassword()).isEqualTo("encoded-password");
		assertThat(savedUser.getRole()).isEqualTo(Role.MEMBER);
		assertThat(savedUser.getState()).isEqualTo(AccountState.ACTIVE);
		assertThat(savedUser.getRegisterDate()).isNotNull();
		assertThat(response.getAccessToken()).isEqualTo("access-token");
		assertThat(response.getRefreshToken()).isEqualTo("refresh-token");
		assertThat(response.getUser()).isEqualTo(userResponse);
		verify(tokenRepository).save(any(Token.class));
	}

	@Test
	void registerRejectsDuplicateUsername() {
		RegisterRequest request = validRegisterRequest();
		when(userRepository.findByUsername("new.member")).thenReturn(Optional.of(new User()));

		assertThatThrownBy(() -> authService.register(request))
				.isInstanceOfSatisfying(AppException.class,
						exception -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTED))
				.hasMessage("Tên đăng nhập đã tồn tại");
	}

	@Test
	void loginAcceptsEmailAsIdentifier() {
		LoginRequest request = new LoginRequest();
		request.setUsername("member@example.vn");
		request.setPassword("Password1");
		User user = User.builder().id(5).username("new.member").email("member@example.vn").role(Role.MEMBER).build();
		UserResponse userResponse = UserResponse.builder().id(5).username("new.member").role(Role.MEMBER).build();

		when(userRepository.findByUsername("member@example.vn")).thenReturn(Optional.empty());
		when(userRepository.findByEmail("member@example.vn")).thenReturn(Optional.of(user));
		when(tokenRepository.findAllValidTokenByUser(5)).thenReturn(List.of());
		when(jwtService.generateToken(user)).thenReturn("access-token");
		when(jwtService.generateRefreshToken(user)).thenReturn("refresh-token");
		when(userMapper.toUserResponse(user)).thenReturn(userResponse);

		var response = authService.login(request, "127.0.0.1", "test-agent");

		assertThat(response.getUser()).isEqualTo(userResponse);
		verify(tokenRepository).save(any(Token.class));
	}

	private RegisterRequest validRegisterRequest() {
		RegisterRequest request = new RegisterRequest();
		request.setUsername("new.member");
		request.setPassword("Password1");
		request.setFirstName("Nguyễn");
		request.setLastName("Thành Viên");
		request.setEmail("member@example.vn");
		request.setPhone("0901234567");
		request.setAddress("12 Tràng Tiền");
		request.setCity("Hà Nội");
		request.setDistrict("Hoàn Kiếm");
		request.setWard("Tràng Tiền");
		request.setYob("1995");
		request.setCccd("012345678999");
		return request;
	}
}
