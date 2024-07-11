package com.NttDataChallenge.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MovementRequestDTO {
    private int idMovement;

    private Date date;
    private String typeMovement;
    private Double balance;
    private Double value;
    private int idAccount;
}
