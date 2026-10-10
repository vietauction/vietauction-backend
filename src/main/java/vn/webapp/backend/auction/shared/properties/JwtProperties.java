package vn.webapp.backend.auction.shared.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtProperties {
	private String secretKey;
	private long expiration;
	private RefreshToken refreshToken = new RefreshToken();

	@Data
	public static class RefreshToken {
		private long expiration;
	}
}
