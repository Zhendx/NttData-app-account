package com.NttDataChallenge.account.mapper;

import com.NttDataChallenge.account.dto.AccountDTO;
import com.NttDataChallenge.account.dto.AccountRequestDTO;
import com.NttDataChallenge.account.dto.AccountResponseDTO;
import com.NttDataChallenge.account.models.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountRequestDTO accountDTOToAccountRequest(AccountDTO accountDTO);
    AccountEntity accountRequestToAccountCreate(AccountRequestDTO accountRequestDTO);
    AccountResponseDTO accountToAccountResponse(AccountEntity account);
    List<AccountResponseDTO> listAccountToListAccountResponse(List<AccountEntity> account);
}
