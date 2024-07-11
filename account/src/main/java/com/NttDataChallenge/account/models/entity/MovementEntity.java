package com.NttDataChallenge.account.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="movement")
@Getter
@Setter
public class MovementEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int idMovement;

    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "type_movement")
    private String typeMovement;
    private Double balance;
    private Double value;
    @Column(name = "id_account")
    private int idAccount;
}
