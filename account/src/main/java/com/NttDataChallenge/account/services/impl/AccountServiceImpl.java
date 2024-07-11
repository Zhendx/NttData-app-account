package com.NttDataChallenge.account.services.impl;

import com.NttDataChallenge.account.dto.AccountRequestDTO;
import com.NttDataChallenge.account.dto.AccountResponseDTO;
import com.NttDataChallenge.account.exception.AppException;
import com.NttDataChallenge.account.exception.ConstantError;
import com.NttDataChallenge.account.mapper.AccountMapper;
import com.NttDataChallenge.account.models.entity.AccountEntity;
import com.NttDataChallenge.account.repository.AccountRepository;
import com.NttDataChallenge.account.services.IAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccount {
    private final AccountRepository accountRep;
    @Override
    public List<AccountResponseDTO> getAllAccounts(){
        return AccountMapper.INSTANCE.listAccountToListAccountResponse(accountRep.findAll());
    };
    @Override
    public AccountResponseDTO getById(int id){
        return AccountMapper.INSTANCE.accountToAccountResponse(accountRep.findById(id).orElseThrow(()-> new AppException(ConstantError.errorApp)));
    };
    @Override
    public AccountResponseDTO create(AccountRequestDTO accountRequestDTO){
        AccountEntity account = accountRep.save(AccountMapper.INSTANCE.accountRequestToAccountCreate(accountRequestDTO));
        return AccountMapper.INSTANCE.accountToAccountResponse(account);
    }
    @Override
    public AccountResponseDTO update(int id, AccountRequestDTO accountRequestDTO){
        AccountEntity account = accountRep.findById(id).orElseThrow(()-> new AppException(ConstantError.errorApp));
        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setAccountType(accountRequestDTO.getAccountType());
        account.setOpeningBalance(accountRequestDTO.getOpeningBalance());
        account.setState(accountRequestDTO.getState());
        return AccountMapper.INSTANCE.accountToAccountResponse(accountRep.save(account));
    }
    @Override
    public void deleteById(int id){
        accountRep.findById(id).orElseThrow(()-> new AppException(ConstantError.errorApp));
        accountRep.deleteById((int) id);
    }
}
