package com.NttDataChallenge.account.models.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MovementReportValue {
    private Date date;
    private String name;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "type_movement")
    private String typeMovement;
    private Double balance;
    private Boolean state;
    private Double value;
    private Double availableBalance;
}
