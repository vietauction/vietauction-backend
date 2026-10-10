package vn.webapp.backend.auction.identity.dto.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import vn.webapp.backend.auction.identity.domain.enums.AccountState;
import vn.webapp.backend.auction.identity.domain.enums.Role;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
	Integer id;
	String username;
	String firstName;
	String lastName;
	String fullName;
	String email;
	String phone;
	String address;
	String city;
	String district;
	String ward;
	String yob;
	String avatar;
	String cccd;
	String cccdFirst;
	String cccdLast;
	String cccdFrom;
	String banReason;
	Timestamp registerDate;
	AccountState state;
	Role role;
	String bankAccountNumber;
	String bankAccountName;
	BankResponse bank;
}
