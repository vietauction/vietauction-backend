package vn.webapp.backend.auction.identity.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisableUserRequest {
	String username;
	String reason;
}
