package com.NttDataChallenge.account.controllers;

import com.NttDataChallenge.account.dto.MovementDTO;
import com.NttDataChallenge.account.dto.MovementRequestDTO;
import com.NttDataChallenge.account.dto.MovementResponseDTO;
import com.NttDataChallenge.account.mapper.MovementMapper;
import com.NttDataChallenge.account.models.entity.MovementReportValue;
import com.NttDataChallenge.account.models.entity.MovementTransaction;
import com.NttDataChallenge.account.services.IMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movement")
public class MovementController {
    private final IMovement movementService;
    @GetMapping("/list")
    private ResponseEntity<List<MovementResponseDTO>> listMovements(){
        return ResponseEntity.ok(movementService.getAllMovements());
    }
    @GetMapping("/{id}/view")
    public ResponseEntity<MovementResponseDTO> listMovementId(@PathVariable int id){
        return ResponseEntity.ok(movementService.getById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementDTO movementDTO){
        return ResponseEntity.ok(movementService.create(MovementMapper.INSTANCE.movementDTOToMovementRequest(movementDTO)));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<MovementResponseDTO> updateMovement(@PathVariable int id, @RequestBody MovementDTO movementDTO){
        return ResponseEntity.ok(movementService.update(id, MovementMapper.INSTANCE.movementDTOToMovementRequest(movementDTO)));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteMovement(@PathVariable int id){
        movementService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/report")
    public ResponseEntity<List<MovementReportValue>> listMovementRangeDate(@RequestParam Date dateInitial, @RequestParam Date dateFinal){
        return ResponseEntity.ok(movementService.getReport(dateInitial, dateFinal));
    }
    @PostMapping("/transaction")
    private ResponseEntity<MovementTransaction> transactionMovement(@RequestBody MovementTransaction movementTrans){
        return ResponseEntity.ok(movementService.getTransaction(movementTrans));
    }
}
