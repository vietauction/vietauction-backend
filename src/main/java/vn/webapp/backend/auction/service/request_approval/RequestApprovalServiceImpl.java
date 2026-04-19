package vn.webapp.backend.auction.service.request_approval;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.CancelRequestApproval;
import vn.webapp.backend.auction.dto.ManagerRequestApproval;
import vn.webapp.backend.auction.dto.StaffRequestApproval;
import vn.webapp.backend.auction.dto.UserRequestApproval;
import vn.webapp.backend.auction.enums.ProductState;
import vn.webapp.backend.auction.enums.RequestApprovalState;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.exception.UserNotFoundException;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.Product;
import vn.webapp.backend.auction.model.RequestApproval;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.ProductRepository;
import vn.webapp.backend.auction.repository.RequestApprovalRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;

@Transactional
@Service
@RequiredArgsConstructor
public class RequestApprovalServiceImpl implements RequestApprovalService {
    private final RequestApprovalRepository requestApprovalRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public RequestApproval getRequestById(Integer id) {
        return requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));
    }

    @Override
    public void setRequestState(Integer id, Integer responderId, String state) {
        var existingRequest = requestApprovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));
        var existUser = userRepository.findById(responderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        if (existUser.getRole().equals(Role.STAFF)) {
            existingRequest.setStaff(existUser);
        }
        if (existUser.getRole().equals(Role.MEMBER)) {
            existingRequest.getProduct().setState(ProductState.HIDDEN);
        }
        existingRequest.setResponder(existUser);
        existingRequest.setState(RequestApprovalState.valueOf(state));
        existingRequest.setConfirm(false);
        existingRequest.setResponseTime(Timestamp.from(Instant.now()));
    }

    @Override
    public void confirmRequest(Integer requestId, Integer responderId) {
        var request = requestApprovalRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));

        var responder = userRepository.findById(responderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        if (responder.getRole().equals(Role.STAFF)) {
            request.setStaff(responder);
        } else if (responder.getRole().equals(Role.MEMBER)) {
            request.getProduct().setState(ProductState.ACTIVE);
            request.getProduct().setIsHolding(false);
        }

        request.setConfirm(true);
        request.setResponder(responder);
        request.setResponseTime(Timestamp.from(Instant.now()));
    }

    @Override
    public void cancelRequest(CancelRequestApproval request) {
        var existingRequest = requestApprovalRepository.findById(request.requestId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));
        Product product = existingRequest.getProduct();
        product.setState(ProductState.HIDDEN);
        existingRequest.setProduct(product);
        existingRequest.setNote(request.note());
    }

    @Override
    public Page<RequestApproval> getRequestBySenderRole(Role role, String productName, String category, Pageable pageable) {
        if (category.equals("Tất cả"))
            return requestApprovalRepository.findRequestApprovalBySenderRole(role, productName, null, pageable);
        return requestApprovalRepository.findRequestApprovalBySenderRole(role, productName, category, pageable);
    }

    @Override
    public RequestApproval requestFromUser(UserRequestApproval request) {
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.PRODUCT_NOT_FOUND));

        RequestApproval newRequest = new RequestApproval();
        newRequest.setRequestTime(request.requestTime());
        newRequest.setProduct(product);
        newRequest.setConfirm(false);
        newRequest.setState(RequestApprovalState.ACTIVE);
        newRequest.setSender(sender);
        newRequest.setDesiredPrice(product.getBuyNowPrice());
        requestApprovalRepository.save(newRequest);
        return newRequest;
    }

    @Override
    public RequestApproval requestFromStaff(StaffRequestApproval request) {
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        RequestApproval oldRequest = requestApprovalRepository.findById(request.requestApprovalId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));

        RequestApproval newRequest = new RequestApproval();
        newRequest.setRequestTime(request.requestTime());
        newRequest.setProduct(oldRequest.getProduct());
        newRequest.setConfirm(false);
        newRequest.setState(RequestApprovalState.ACTIVE);
        newRequest.setSender(sender);
        newRequest.setDesiredPrice(oldRequest.getProduct().getBuyNowPrice());
        newRequest.setValuation(request.valuation());
        newRequest.setStaff(sender);
        requestApprovalRepository.save(newRequest);
        return newRequest;
    }

    @Override
    public RequestApproval requestFromManager(ManagerRequestApproval request) {
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        RequestApproval oldRequest = requestApprovalRepository.findById(request.requestApprovalId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.REQUEST_APPROVAL_NOT_FOUND));

        RequestApproval newRequest = new RequestApproval();
        newRequest.setRequestTime(request.requestTime());
        newRequest.setProduct(oldRequest.getProduct());
        newRequest.setConfirm(false);
        newRequest.setState(RequestApprovalState.ACTIVE);
        newRequest.setSender(sender);
        newRequest.setDesiredPrice(oldRequest.getDesiredPrice());
        newRequest.setValuation(oldRequest.getValuation());
        newRequest.setStaff(oldRequest.getSender());
        requestApprovalRepository.save(newRequest);
        return newRequest;
    }

    @Override
    public Page<RequestApproval> getRequestApprovalByUserId(Integer id, String productName, String category, Pageable pageable) {
        if (category.equals("Tất cả"))
            return requestApprovalRepository.findRequestApprovalByUserId(id, productName,null, pageable);
        return requestApprovalRepository.findRequestApprovalByUserId(id, productName,category, pageable);
    }

    @Override
    public Page<RequestApproval> getRequestApprovalPassed(String productName, String category, Pageable pageable) {
        if (category.equals("Tất cả")) {
            return requestApprovalRepository.findRequestApprovalPassed(productName, null, pageable);
        }
        return requestApprovalRepository.findRequestApprovalPassed(productName, category, pageable);
    }

    @Override
    public Page<RequestApproval> getRequestNeedConfirmByMember(Integer memberId,String productName, Pageable pageable) {
        return requestApprovalRepository.findRequestNeedConfirmByMember(memberId,productName, pageable);
    }
}
