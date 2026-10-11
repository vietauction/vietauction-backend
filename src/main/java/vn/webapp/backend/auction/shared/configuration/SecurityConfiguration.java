package vn.webapp.backend.auction.shared.configuration;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.filter.JwtAuthenticationFilter;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final LogoutHandler logoutHandler;

	private static final String[] SWAGGER_WHITELIST = {
		"/v2/api-docs",
		"/v3/api-docs",
		"/v3/api-docs/**",
		"/swagger-resources",
		"/swagger-resources/**",
		"/configuration/ui",
		"/configuration/security",
		"/swagger-ui/**",
		"/webjars/**",
		"/swagger-ui.html"
	};

	private static final String[] PUBLIC_GET_ENDPOINTS = {
		ApiEndpoint.Products.BASE + "/**",
		ApiEndpoint.ProductCategories.BASE + "/**",
		ApiEndpoint.Auctions.BASE + "/**",
		ApiEndpoint.AuctionHistory.BASE + "/**",
		ApiEndpoint.AuctionRegistrations.BASE + "/**",
		ApiEndpoint.Images.BASE + "/**",
		ApiEndpoint.Users.BASE + ApiEndpoint.Users.BY_EMAIL,
		ApiEndpoint.Users.BASE + ApiEndpoint.Users.BY_USERNAME,
		ApiEndpoint.Banks.BASE + "/**",
		ApiEndpoint.Payment.BASE + "/**",
		ApiEndpoint.Transactions.BASE + "/**",
		ApiEndpoint.RequestApprovals.BASE + "/**",
		ApiEndpoint.Users.BASE + ApiEndpoint.Users.WINNER,
	};

	private static final String[] PUBLIC_POST_ENDPOINTS = {
		ApiEndpoint.Auth.BASE + "/**",
		ApiEndpoint.AuctionHistory.BASE,
		ApiEndpoint.Products.BASE + ApiEndpoint.Products.PRODUCT_REQUEST,
		ApiEndpoint.Images.BASE + ApiEndpoint.Images.ADD,
		ApiEndpoint.RequestApprovals.BASE + ApiEndpoint.RequestApprovals.SEND_FROM_USER,
		ApiEndpoint.RequestApprovals.BASE + ApiEndpoint.RequestApprovals.SEND_FROM_STAFF,
		ApiEndpoint.RequestApprovals.BASE + ApiEndpoint.RequestApprovals.SEND_FROM_MANAGER,
		ApiEndpoint.Transactions.BASE + "/create-for-winner/**",
		ApiEndpoint.Transactions.BASE + "/create-for-seller/**",
	};

	private static final String[] PUBLIC_PUT_ENDPOINTS = {
		ApiEndpoint.Users.BASE + "/change-password",
		ApiEndpoint.Auctions.BASE + ApiEndpoint.Auctions.END_DATE,
		ApiEndpoint.Auctions.BASE + ApiEndpoint.Auctions.STATE,
		ApiEndpoint.Users.BASE,
		ApiEndpoint.RequestApprovals.BASE + ApiEndpoint.RequestApprovals.STATE,
		ApiEndpoint.RequestApprovals.BASE + ApiEndpoint.RequestApprovals.CONFIRM,
		ApiEndpoint.RequestApprovals.BASE + ApiEndpoint.RequestApprovals.CANCEL,
		ApiEndpoint.Transactions.BASE + "/*/method",
		ApiEndpoint.Products.BASE + "/*/holding",
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(SWAGGER_WHITELIST).permitAll()
				.requestMatchers("/ws/**").permitAll()
				.requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.PUT, PUBLIC_PUT_ENDPOINTS).permitAll()
				.anyRequest().authenticated()
			)
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.httpBasic(Customizer.withDefaults())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.logout(logout -> logout
				.logoutUrl(ApiEndpoint.Auth.BASE + ApiEndpoint.Auth.LOGOUT)
				.addLogoutHandler(logoutHandler)
				.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
			)
			.exceptionHandling(ex -> ex
				.authenticationEntryPoint((request, response, authException) ->
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			);
		return http.build();
	}
}
