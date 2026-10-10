package vn.webapp.backend.auction.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

	@NotBlank(message = "Username is required")
	String username;

	@NotBlank(message = "Password is required")
	String password;

	@NotBlank(message = "First name is required")
	String firstName;

	@NotBlank(message = "Last name is required")
	String lastName;

	@Email(message = "Invalid email")
	@NotBlank(message = "Email is required")
	String email;

	@NotBlank(message = "Phone is required")
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
	String yob;

	@NotBlank(message = "CCCD is required")
	String cccd;

	String cccdFirst;
	String cccdLast;
	String cccdFrom;
	Integer bankId;
	String bankAccountNumber;
	String bankAccountName;
}
