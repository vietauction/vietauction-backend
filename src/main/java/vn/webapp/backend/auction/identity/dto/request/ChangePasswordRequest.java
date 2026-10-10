package vn.webapp.backend.auction.identity.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {

	@NotBlank(message = "Old password is required")
	String oldPassword;

	@NotBlank(message = "New password is required")
	String newPassword;

	String username;
}
