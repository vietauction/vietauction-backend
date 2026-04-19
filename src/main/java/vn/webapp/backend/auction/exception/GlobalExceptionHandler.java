package vn.webapp.backend.auction.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.webapp.backend.auction.model.ApiResponse;
import vn.webapp.backend.auction.model.ApiResponseError;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AccountDisabledException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountDisabledException(AccountDisabledException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AccountInactiveException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccountInactiveException(AccountInactiveException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(202).body(body);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(OldPasswordMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleOldPasswordMismatchException(OldPasswordMismatchException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({UnauthorizedException.class, UserNotAllowedAccess.class})
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(RuntimeException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), List.of(ApiResponseError.builder().message(ex.getMessage()).build()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        String message = "Invalid username or password";
        ApiResponse<Void> body = ApiResponse.error(
                message,
                List.of(ApiResponseError.builder().field("username/password").message(message).code("BAD_CREDENTIALS").build())
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ApiResponseError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldError)
                .collect(Collectors.toList());

        ApiResponse<Void> body = ApiResponse.error("Validation failed", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
        List<ApiResponseError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldError)
                .collect(Collectors.toList());

        ApiResponse<Void> body = ApiResponse.error("Validation failed", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String message = "Invalid value for parameter '" + field + "'";
        ApiResponse<Void> body = ApiResponse.error(
                "Validation failed",
                List.of(ApiResponseError.builder().field(field).message(message).code("TYPE_MISMATCH").build())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleUnwantedException(Exception ex) {
        logger.error("Unhandled exception", ex);
        ApiResponse<Void> body = ApiResponse.error(
                ex.getMessage(),
                List.of(ApiResponseError.builder().field("general").message(ex.getMessage()).build())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private ApiResponseError toFieldError(FieldError error) {
        return ApiResponseError.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .code(error.getCode())
                .build();
    }
}
