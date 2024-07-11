package com.NttDataChallenge.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDTO {
    private int idAccount;

    private String accountNumber;
    private String accountType;
    private Double openingBalance;
    private Boolean state;
    private int idClient;
}
