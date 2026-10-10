package vn.webapp.backend.auction.identity.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
	String firstName;
	String lastName;
	String phone;
	String address;
	String city;
	String district;
	String ward;
	String avatar;
	String cccdFirst;
	String cccdLast;
	String cccdFrom;
	Integer bankId;
	String bankAccountNumber;
	String bankAccountName;
}
