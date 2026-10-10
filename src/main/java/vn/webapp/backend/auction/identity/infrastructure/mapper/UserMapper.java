package vn.webapp.backend.auction.identity.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.webapp.backend.auction.identity.domain.entity.Bank;
import vn.webapp.backend.auction.identity.domain.entity.User;
import vn.webapp.backend.auction.identity.dto.response.BankResponse;
import vn.webapp.backend.auction.identity.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "fullName", expression = "java(user.getFullName())")
	UserResponse toUserResponse(User user);

	BankResponse toBankResponse(Bank bank);
}
