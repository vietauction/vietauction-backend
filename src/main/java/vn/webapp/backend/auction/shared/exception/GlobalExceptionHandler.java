package vn.webapp.backend.auction.shared.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import vn.webapp.backend.auction.shared.dto.response.ApiError;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(AppException.class)
	public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		String message = ex.getCustomMessage() != null ? ex.getCustomMessage() : errorCode.getMsg();
		ApiResponse<Void> body = ApiResponse.error(
				message,
				List.of(ApiError.builder().code(String.valueOf(errorCode.getCode())).message(message).build()));
		return ResponseEntity.status(errorCode.getStatusCode()).body(body);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
		ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;
		ApiResponse<Void> body = ApiResponse.error(
				errorCode.getMsg(),
				List.of(ApiError.builder()
						.field("username/password")
						.message(errorCode.getMsg())
						.code("BAD_CREDENTIALS")
						.build()));
		return ResponseEntity.status(errorCode.getStatusCode()).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		List<ApiError> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(this::toApiError)
				.collect(Collectors.toList());
		ApiResponse<Void> body = ApiResponse.error("Validation failed", errors);
		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {
		String message = "Invalid value for parameter '" + ex.getName() + "'";
		ApiResponse<Void> body = ApiResponse.error(
				"Validation failed",
				List.of(ApiError.builder()
						.field(ex.getName())
						.message(message)
						.code("TYPE_MISMATCH")
						.build()));
		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiResponse<Void>> handleMaxUploadSizeExceededException(
			MaxUploadSizeExceededException ex) {
		ErrorCode errorCode = ErrorCode.FILE_UPLOAD_FAILED;
		ApiResponse<Void> body = ApiResponse.error(
				errorCode.getMsg(), List.of(ApiError.builder().message(errorCode.getMsg()).build()));
		return ResponseEntity.status(errorCode.getStatusCode()).body(body);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleUnwantedException(Exception ex) {
		logger.error("Unhandled exception", ex);
		ApiResponse<Void> body = ApiResponse.error(
				ErrorCode.UNCATEGORIZED_EXCEPTION.getMsg(),
				List.of(ApiError.builder()
						.field("general")
						.message(ex.getMessage())
						.build()));
		return ResponseEntity.badRequest().body(body);
	}

	private ApiError toApiError(FieldError error) {
		return ApiError.builder()
				.field(error.getField())
				.message(error.getDefaultMessage())
				.code(error.getCode())
				.build();
	}
}
