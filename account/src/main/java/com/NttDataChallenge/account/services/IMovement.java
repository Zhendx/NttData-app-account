package com.NttDataChallenge.account.services;

import com.NttDataChallenge.account.dto.MovementRequestDTO;
import com.NttDataChallenge.account.dto.MovementResponseDTO;
import com.NttDataChallenge.account.models.entity.MovementReportValue;
import com.NttDataChallenge.account.models.entity.MovementTransaction;

import java.sql.Date;
import java.util.List;

public interface IMovement {
    List<MovementResponseDTO> getAllMovements();
    MovementResponseDTO getById(int id);
    MovementResponseDTO create(MovementRequestDTO movementRequestDTO);
    MovementResponseDTO update(int id, MovementRequestDTO movementRequestDTO);
    void deleteById(int id);
    List<MovementReportValue> getReport(Date dateInitial, Date dateFinal);
    MovementTransaction getTransaction(MovementTransaction movementTrans);
}
