package vn.webapp.backend.auction.identity.filter;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.domain.entity.Token;
import vn.webapp.backend.auction.identity.domain.repository.TokenRepository;
import vn.webapp.backend.auction.identity.service.JwtService;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().contains(ApiEndpoint.Auth.BASE)) {
			filterChain.doFilter(request, response);
			return;
		}
		final String authHeader = request.getHeader("Authorization");
		try {
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			String jwt = authHeader.substring(7);
			String username = jwtService.extractUsername(jwt);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				Optional<Token> tokenOptional = tokenRepository.findByToken(jwt);
				if (tokenOptional.isPresent()) {
					Token token = tokenOptional.get();
					if (!token.isExpired() && !token.isRevoked() && jwtService.isTokenValid(jwt, userDetails)) {
						UsernamePasswordAuthenticationToken authToken =
								new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					} else {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired or revoked");
						return;
					}
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
					return;
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is expired!");
		}
	}
}
