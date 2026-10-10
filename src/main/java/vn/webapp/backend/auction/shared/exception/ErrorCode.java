package vn.webapp.backend.auction.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// -- COMMON --
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception Error", HttpStatus.INTERNAL_SERVER_ERROR),
	INVALID_KEY(9998, "Invalid Key Error", HttpStatus.BAD_REQUEST),
	RESOURCE_NOT_FOUND(1009, "Resource not found", HttpStatus.NOT_FOUND),
	FILE_UPLOAD_FAILED(9001, "Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR),

	// -- AUTH --
	UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1007, "Bạn không có quyền thực hiện hành động này", HttpStatus.FORBIDDEN),
	TOKEN_EXPIRED(1008, "JWT token is expired", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN(1010, "Invalid token", HttpStatus.UNAUTHORIZED),
	BAD_CREDENTIALS(1011, "Tên đăng nhập hoặc mật khẩu không đúng", HttpStatus.UNAUTHORIZED),

	// -- USER --
	USER_EXISTED(2001, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
	USER_NOT_FOUND(2002, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
	USERNAME_INVALID(2003, "Username phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(2004, "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
	USER_BLOCKED(2005, "Tài khoản đã bị khóa hoặc chưa kích hoạt", HttpStatus.FORBIDDEN),
	OLD_PASSWORD_MISMATCH(2006, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
	USER_NOT_ALLOWED_ACCESS(2007, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),

	// -- ACCOUNT --
	ACCOUNT_INACTIVE(3001, "Tài khoản chưa được kích hoạt", HttpStatus.ACCEPTED),
	ACCOUNT_DISABLED(3002, "Tài khoản đã bị vô hiệu hóa", HttpStatus.FORBIDDEN),

	// -- AUCTION --
	AUCTION_NOT_FOUND(4001, "Phiên đấu giá không tồn tại", HttpStatus.NOT_FOUND),
	AUCTION_REGISTRATION_NOT_FOUND(4002, "Đăng ký đấu giá không tồn tại", HttpStatus.NOT_FOUND),
	AUCTION_HISTORY_NOT_FOUND(4003, "Lịch sử đấu giá không tồn tại", HttpStatus.NOT_FOUND),
	INVALID_BID_PRICE(4004, "Giá đấu không hợp lệ", HttpStatus.BAD_REQUEST),

	// -- PRODUCT --
	PRODUCT_NOT_FOUND(5001, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
	PRODUCT_CATEGORY_NOT_FOUND(5002, "Danh mục sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
	IMAGE_NOT_FOUND(5003, "Hình ảnh không tồn tại", HttpStatus.NOT_FOUND),

	// -- TRANSACTION --
	TRANSACTION_NOT_FOUND(6001, "Giao dịch không tồn tại", HttpStatus.NOT_FOUND),
	REQUEST_APPROVAL_NOT_FOUND(6002, "Yêu cầu duyệt không tồn tại", HttpStatus.NOT_FOUND),

	// -- BANK --
	BANK_NOT_FOUND(7001, "Ngân hàng không tồn tại", HttpStatus.NOT_FOUND),
	;

	ErrorCode(int code, String msg, HttpStatusCode statusCode) {
		this.code = code;
		this.msg = msg;
		this.statusCode = statusCode;
	}

	private final int code;
	private final String msg;
	private final HttpStatusCode statusCode;
}
