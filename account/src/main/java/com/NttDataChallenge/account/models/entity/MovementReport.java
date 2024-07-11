package com.NttDataChallenge.account.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class MovementReport {

    private Date date;
    private String accountType;
    private String accountNumber;
    private String typeMovement;
    private Double balance;
    private Boolean state;
    private Double value;
}
