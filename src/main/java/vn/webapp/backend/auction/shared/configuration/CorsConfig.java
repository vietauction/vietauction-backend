package vn.webapp.backend.auction.shared.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	private static final List<String> ALLOWED_ORIGINS = List.of(
		"http://localhost:3000",
		"http://localhost:3001",
		"https://fe-deploy-hazel.vercel.app",
		"https://auction-webapp-production.up.railway.app",
		"https://admin-manager-auction-production.vercel.app",
		"https://website-auction-production.vercel.app"
	);

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/v1/**")
				.allowedOrigins(ALLOWED_ORIGINS.toArray(new String[0]))
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
				.allowedHeaders("Authorization", "Content-Type")
				.allowCredentials(true);
	}
}
