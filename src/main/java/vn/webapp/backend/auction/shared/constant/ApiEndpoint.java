package vn.webapp.backend.auction.shared.constant;

public final class ApiEndpoint {

	private ApiEndpoint() {}

	public static final String API_V1 = "/api/v1";

	// ========================
	// AUTH
	// ========================
	public static final class Auth {
		public static final String BASE = API_V1 + "/auth";
		public static final String LOGIN = "/login";
		public static final String REGISTER = "/register";
		public static final String LOGOUT = "/logout";
		public static final String REFRESH_TOKEN = "/refresh-token";
		public static final String ACTIVATE = "/activate";
		public static final String FORGOT_PASSWORD = "/forgot-password";
		public static final String RESET_PASSWORD = "/reset-password";
		public static final String CHANGE_PASSWORD = "/change-password";
	}

	// ========================
	// USERS
	// ========================
	public static final class Users {
		public static final String BASE = API_V1 + "/users";
		public static final String ID = "/{id}";
		public static final String BY_EMAIL = "/by-email/{email}";
		public static final String BY_USERNAME = "/by-username/{username}";
		public static final String STATUS = "/{id}/status";
		public static final String BAN = "/{id}/ban";
		public static final String WINNER = "/get-winner-auction/{auctionId}";
		public static final String REGISTRATION = "/{id}/registration";
		public static final String SORTED_PAGED = "/sorted-and-paged";
		public static final String TOP_SPENT = "/top-spent";
	}

	// ========================
	// AUCTIONS
	// ========================
	public static final class Auctions {
		public static final String BASE = API_V1 + "/auctions";
		public static final String ID = "/{id}";
		public static final String STATE = "/{id}/state";
		public static final String END_DATE = "/{id}/end-date";
		public static final String SORTED_PAGED = "/sorted-and-paged";
		public static final String BY_DAY = "/by-day";
		public static final String BY_STATE = "/by-state";
		public static final String BY_STAFF = "/by-staff/{staffId}";
		public static final String REGISTRATIONS = "/registrations";
		public static final String FAILED = "/failed";
		public static final String CURRENT_BY_PRODUCT = "/current-by-product/{productId}";
		public static final String DELETE_RESULT = "/{id}/delete-result";
	}

	// ========================
	// AUCTION HISTORY (BID)
	// ========================
	public static final class AuctionHistory {
		public static final String BASE = API_V1 + "/auction-history";
		public static final String BY_AUCTION = "/by-auction/{auctionId}";
		public static final String BY_USERNAME = "/by-username/{username}";
		public static final String BY_DATE = "/by-date";
		public static final String BY_AUCTION_AND_USER = "/by-auction-and-user";
		public static final String WHEN_FINISHED = "/get-when-auction-finished/{auctionId}";
	}

	// ========================
	// AUCTION REGISTRATIONS
	// ========================
	public static final class AuctionRegistrations {
		public static final String BASE = API_V1 + "/auction-registrations";
		public static final String ID = "/{id}";
		public static final String BY_AUCTION = "/by-auction/{auctionId}";
		public static final String BY_USER = "/by-user/{userId}";
	}

	// ========================
	// PRODUCTS
	// ========================
	public static final class Products {
		public static final String BASE = API_V1 + "/products";
		public static final String ID = "/{id}";
		public static final String STATE = "/{id}/state";
		public static final String HOLDING = "/{id}/holding";
		public static final String SORTED_PAGED = "/sorted-and-paged";
		public static final String MANAGER_LIST = "/manager-list";
		public static final String PRODUCT_REQUEST = "/product-request";
		public static final String BY_USER = "/by-user/{userId}";
	}

	// ========================
	// PRODUCT CATEGORIES
	// ========================
	public static final class ProductCategories {
		public static final String BASE = API_V1 + "/product-categories";
		public static final String ID = "/{id}";
	}

	// ========================
	// IMAGES
	// ========================
	public static final class Images {
		public static final String BASE = API_V1 + "/images";
		public static final String ADD = "/add";
		public static final String BY_PRODUCT = "/by-product/{productId}";
		public static final String ID = "/{id}";
	}

	// ========================
	// TRANSACTIONS
	// ========================
	public static final class Transactions {
		public static final String BASE = API_V1 + "/transactions";
		public static final String ID = "/{id}";
		public static final String STATE = "/{id}/state";
		public static final String METHOD = "/{id}/method";
		public static final String BY_TYPE_STATE = "/by-type-state";
		public static final String WINNER_AUCTION = "/winner-auction/{auctionId}";
		public static final String HANDOVER = "/handover";
		public static final String CREATE_FOR_WINNER = "/create-for-winner/{auctionId}";
		public static final String CREATE_FOR_SELLER = "/create-for-seller/{auctionId}";
	}

	// ========================
	// REQUEST APPROVALS
	// ========================
	public static final class RequestApprovals {
		public static final String BASE = API_V1 + "/request-approvals";
		public static final String ID = "/{id}";
		public static final String STATE = "/{id}/state";
		public static final String CONFIRM = "/{id}/confirm";
		public static final String SEND_FROM_USER = "/send-from-user";
		public static final String SEND_FROM_STAFF = "/send-from-staff";
		public static final String SEND_FROM_MANAGER = "/send-from-manager";
		public static final String CANCEL = "/cancel";
		public static final String SORTED_PAGED = "/sorted-and-paged";
	}

	// ========================
	// BANKS
	// ========================
	public static final class Banks {
		public static final String BASE = API_V1 + "/banks";
		public static final String ID = "/{id}";
	}

	// ========================
	// PAYMENT
	// ========================
	public static final class Payment {
		public static final String BASE = API_V1 + "/payment";
		public static final String VN_PAY = "/vn-pay";
		public static final String VN_PAY_CALLBACK = "/vn-pay-callback";
	}

	// ========================
	// REALTIME / WEBSOCKET
	// ========================
	public static final class Realtime {
		public static final String BASE = API_V1 + "/realtime";
		public static final String BID = "/bid";
		public static final String KICK = "/kick";
	}

	// ========================
	// DASHBOARD
	// ========================
	public static final class Dashboard {
		public static final String BASE = API_V1 + "/dashboard";
	}
}
