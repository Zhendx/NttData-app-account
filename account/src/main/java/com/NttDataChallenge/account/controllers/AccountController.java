package com.NttDataChallenge.account.controllers;

import com.NttDataChallenge.account.dto.AccountDTO;
import com.NttDataChallenge.account.dto.AccountRequestDTO;
import com.NttDataChallenge.account.dto.AccountResponseDTO;
import com.NttDataChallenge.account.mapper.AccountMapper;
import com.NttDataChallenge.account.services.IAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final IAccount accountService;
    @GetMapping("/list")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @GetMapping("/{id}/view")
    public ResponseEntity<AccountResponseDTO> listAccountId(@PathVariable int id){
        return ResponseEntity.ok(accountService.getById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.create(AccountMapper.INSTANCE.accountDTOToAccountRequest(accountDTO)));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable int id, @RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.update(id, AccountMapper.INSTANCE.accountDTOToAccountRequest(accountDTO)));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id){
        accountService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
