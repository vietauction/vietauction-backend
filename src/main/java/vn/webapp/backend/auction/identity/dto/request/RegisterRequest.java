package vn.webapp.backend.auction.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

	@NotBlank(message = "Username is required")
	@Size(min = 4, max = 50, message = "Username must contain between 4 and 50 characters")
	@Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username contains invalid characters")
	String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 72, message = "Password must contain between 8 and 72 characters")
	String password;

	@NotBlank(message = "First name is required")
	String firstName;

	@NotBlank(message = "Last name is required")
	String lastName;

	@Email(message = "Invalid email")
	@NotBlank(message = "Email is required")
	String email;

	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "^[0-9+ ]{9,15}$", message = "Invalid phone number")
	String phone;

	@NotBlank(message = "Address is required")
	String address;

	@NotBlank(message = "City is required")
	String city;

	@NotBlank(message = "District is required")
	String district;

	@NotBlank(message = "Ward is required")
	String ward;

	@NotBlank(message = "Year of birth is required")
	@Pattern(regexp = "^(19|20)\\d{2}$", message = "Invalid year of birth")
	String yob;

	@NotBlank(message = "CCCD is required")
	@Pattern(regexp = "^\\d{9,20}$", message = "Invalid CCCD")
	String cccd;

	String cccdFirst;
	String cccdLast;
	String cccdFrom;
	Integer bankId;
	String bankAccountNumber;
	String bankAccountName;
}
