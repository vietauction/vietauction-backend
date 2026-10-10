package vn.webapp.backend.auction.shared.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageMeta {
	int page;
	int size;
	long totalElements;
	int totalPages;
	boolean last;
}
