package com.NttDataChallenge.account.services.impl;

import com.NttDataChallenge.account.dto.MovementRequestDTO;
import com.NttDataChallenge.account.dto.MovementResponseDTO;
import com.NttDataChallenge.account.exception.AppException;
import com.NttDataChallenge.account.exception.ConstantError;
import com.NttDataChallenge.account.mapper.MovementMapper;
import com.NttDataChallenge.account.models.entity.*;
import com.NttDataChallenge.account.repository.AccountRepository;
import com.NttDataChallenge.account.repository.MovementRepository;
import com.NttDataChallenge.account.services.IMovement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class MovementServiceImpl implements MessageListener, IMovement {
    @Autowired
    private MovementRepository movementRep;
    @Autowired
    private AccountRepository accountRep;

    public static ClientReport clientReport = new ClientReport();

    @Override
    public void onMessage(Message message, byte[] bytes){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(message.toString());
            clientReport = objectMapper.treeToValue(root, ClientReport.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<MovementResponseDTO> getAllMovements(){
        return MovementMapper.INSTANCE.listMovementToListMovementResponse(movementRep.findAll());
    };
    @Override
    public MovementResponseDTO getById(int id){
        return MovementMapper.INSTANCE.movementToMovementResponse(movementRep.findById(id).orElseThrow(()-> new AppException(ConstantError.errorApp2)));
    };
    @Override
    public MovementResponseDTO create(MovementRequestDTO movementRequestDTO){
        MovementEntity movement = movementRep.save(MovementMapper.INSTANCE.movementRequestToMovementCreate(movementRequestDTO));
        return MovementMapper.INSTANCE.movementToMovementResponse(movement);
    }
    @Override
    public MovementResponseDTO update(int id, MovementRequestDTO movementRequestDTO){
        MovementEntity movement = movementRep.findById(id).orElseThrow(()-> new AppException(ConstantError.errorApp2));
        movement.setDate(movementRequestDTO.getDate());
        movement.setTypeMovement(movementRequestDTO.getTypeMovement());
        movement.setValue(movementRequestDTO.getValue());
        movement.setBalance(movementRequestDTO.getBalance());
        return MovementMapper.INSTANCE.movementToMovementResponse(movementRep.save(movement));
    }
    @Override
    public void deleteById(int id){
        movementRep.findById(id).orElseThrow(()-> new AppException(ConstantError.errorApp2));
        movementRep.deleteById((int) id);
    }
    @Override
    public List<MovementReportValue> getReport(Date dateInitial, Date dateFinal){
        List<MovementReportValue> movementReportValueList = new ArrayList<>();
        List<MovementReport> movement = movementRep.getMovementReport(dateInitial, dateFinal, clientReport.getIdClient());
        if(Objects.isNull(movement)||movement.isEmpty()){
            throw new AppException(ConstantError.errorApp3);
        }else{
            for(MovementReport movementTemp : movement){
                MovementReportValue movementReportValue = new MovementReportValue();
                double balance = movementTemp.getBalance();
                double value =movementTemp.getValue();
                movementReportValue.setDate(movementTemp.getDate());
                movementReportValue.setName(clientReport.getName());
                movementReportValue.setAccountNumber(movementTemp.getAccountNumber());
                movementReportValue.setAccountType(movementTemp.getAccountType());
                movementReportValue.setState(movementTemp.getState());
                movementReportValue.setTypeMovement(movementTemp.getTypeMovement());
                movementReportValue.setValue(movementTemp.getValue());
                movementReportValue.setAvailableBalance(balance);
                if (movementTemp.getTypeMovement().equals("Retiro")){
                    movementReportValue.setBalance(balance + value);
                }else{
                    movementReportValue.setBalance(balance - value);
                }
                movementReportValueList.add(movementReportValue);
            }
        }
        return movementReportValueList;
    };
    @Override
    public MovementTransaction getTransaction(MovementTransaction movementTrans){
        MovementEntity movement = new MovementEntity();
        MovementTransaction movementTransResponse = new MovementTransaction();

        AccountEntity account = accountRep.findByPublishedAccountNumber(movementTrans.getAccountNumber(), movementTrans.getAccountType());
        MovementEntity movementId = movementRep.findByMovement(account.getIdClient());
        if (Objects.isNull(account)) {
            throw new AppException(ConstantError.errorApp);
        }else {
            String typeMovement = movementTrans.getTypeMovement();
            if (typeMovement.equals("Retiro")) {
                if (account.getOpeningBalance() == 0) {
                    throw new AppException(ConstantError.errorApp1);
                } else {
                    if (Objects.isNull(movementId)){
                        if (account.getOpeningBalance() >= movementTrans.getValue()){
                            double num = account.getOpeningBalance() - movementTrans.getValue();
                            movement.setBalance(num);
                            movementTransResponse.setBalance(num);
                        }else{
                            throw new AppException(ConstantError.errorApp1);
                        }
                    }else{
                        if (movementId.getBalance() >= movementTrans.getValue()){
                            double num = movementId.getBalance() - movementTrans.getValue();
                            movement.setBalance(num);
                            movementTransResponse.setBalance(num);
                        }else{
                            throw new AppException(ConstantError.errorApp1);
                        }
                    }
                }
            }else{
                if (Objects.isNull(movementId)){
                    double num = account.getOpeningBalance() + movementTrans.getValue();
                    movement.setBalance(num);
                    movementTransResponse.setBalance(num);
                }else{
                    double num = movementId.getBalance() + movementTrans.getValue();
                    movement.setBalance(num);
                    movementTransResponse.setBalance(num);
                }
            }

            movement.setTypeMovement(movementTrans.getTypeMovement());
            movement.setDate(movementTrans.getDate());
            movement.setValue(movementTrans.getValue());
            movement.setIdAccount(account.getIdAccount());

            movementTransResponse.setAccountNumber(movementTrans.getAccountNumber());
            movementTransResponse.setAccountType(movementTrans.getAccountType());
            movementTransResponse.setState(account.getState());
            movementTransResponse.setValue(movementTrans.getValue());
            movementTransResponse.setTypeMovement(movementTrans.getTypeMovement() + " de " + movementTrans.getValue());
            movementTransResponse.setDate(movementTrans.getDate());
        }
        movementRep.save(movement);
        return movementTransResponse;
    }

}
