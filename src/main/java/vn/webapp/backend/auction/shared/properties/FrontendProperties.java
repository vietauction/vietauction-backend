package vn.webapp.backend.auction.shared.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "frontend")
@Data
public class FrontendProperties {
	private String baseUrl;
}
