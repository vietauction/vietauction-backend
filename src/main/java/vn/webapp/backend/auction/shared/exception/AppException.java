package vn.webapp.backend.auction.shared.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

	private final ErrorCode errorCode;
	private String customMessage;

	public AppException(ErrorCode errorCode) {
		super(errorCode.getMsg());
		this.errorCode = errorCode;
	}

	public AppException(ErrorCode errorCode, String customMessage) {
		super(customMessage);
		this.errorCode = errorCode;
		this.customMessage = customMessage;
	}
}
