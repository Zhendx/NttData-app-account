package com.NttDataChallenge.account.models.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MovementTransaction {
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_type")
    private String accountType;
    private Double balance;
    private Double value;
    private Boolean state;
    @Column(name = "type_movement")
    private String typeMovement;
    private Date date;
}
