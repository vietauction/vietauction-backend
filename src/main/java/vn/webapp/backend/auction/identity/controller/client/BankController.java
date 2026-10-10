package vn.webapp.backend.auction.identity.controller.client;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.webapp.backend.auction.identity.dto.response.BankResponse;
import vn.webapp.backend.auction.identity.service.BankService;
import vn.webapp.backend.auction.shared.constant.ApiEndpoint;
import vn.webapp.backend.auction.shared.dto.response.ApiResponse;

@RestController
@RequestMapping(ApiEndpoint.Banks.BASE)
@RequiredArgsConstructor
public class BankController {

	private final BankService bankService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<BankResponse>>> getAllBanks() {
		return ResponseEntity.ok(ApiResponse.success(bankService.getAllBanks()));
	}

	@GetMapping(ApiEndpoint.Banks.ID)
	public ResponseEntity<ApiResponse<BankResponse>> getBankById(@PathVariable Integer id) {
		return ResponseEntity.ok(ApiResponse.success(bankService.getBankById(id)));
	}
}
