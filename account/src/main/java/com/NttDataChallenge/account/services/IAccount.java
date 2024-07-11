package com.NttDataChallenge.account.services;

import com.NttDataChallenge.account.dto.AccountRequestDTO;
import com.NttDataChallenge.account.dto.AccountResponseDTO;
import java.util.List;

public interface IAccount {
    List<AccountResponseDTO> getAllAccounts();
    AccountResponseDTO getById(int id);
    AccountResponseDTO create(AccountRequestDTO accountRequestDTO);
    AccountResponseDTO update(int id, AccountRequestDTO accountRequestDTO);
    void deleteById(int id);
}
