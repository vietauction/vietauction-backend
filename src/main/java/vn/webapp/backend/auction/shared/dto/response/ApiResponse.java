package vn.webapp.backend.auction.shared.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	@Builder.Default
	boolean success = true;

	String message;

	T data;

	Object meta;

	List<ApiError> errors;

	@Builder.Default
	OffsetDateTime timestamp = OffsetDateTime.now();

	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.<T>builder().success(true).data(data).build();
	}

	public static <T> ApiResponse<T> success(String message, T data) {
		return ApiResponse.<T>builder().success(true).message(message).data(data).build();
	}

	public static <T> ApiResponse<T> error(String message, List<ApiError> errors) {
		return ApiResponse.<T>builder().success(false).message(message).errors(errors).build();
	}
}
