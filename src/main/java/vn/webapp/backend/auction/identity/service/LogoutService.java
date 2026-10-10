package vn.webapp.backend.auction.identity.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.domain.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		String jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt).orElse(null);
		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			SecurityContextHolder.clearContext();
		}

		ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", "")
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(0)
				.build();
		response.addHeader("Set-Cookie", refreshTokenCookie.toString());
	}
}
